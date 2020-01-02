package cn.wenyan.compiler;

import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.utils.ScalaUtils;
import cn.wenyan.compiler.utils.Utils;

import java.util.*;
import java.util.function.Function;


public class VariableCompileStream extends CompileStream{

    private Map<String,String> varMap = new HashMap<>();

    private long varIndex = 0;

    VariableCompileStream(WenYanCompilerImpl compiler){
        super(compiler);
    }
    // 具之一句，而翻万里者也。
    public CompileResult compile(String[] wenyans) {
        if(Utils.matches(wenyans[0],WenYanLib.DEFINE_VAR())){
            String wuYiYan = Utils.getString(WenYanLib.NUMBER(),wenyans[0]);
            Utils.inputWenyan(compiler,0);
            long number = getNumber(wuYiYan);
            return new CompileResult(appendVar(
                    (int)number+1,wenyans[(int)number+1],
                    getNames(number,wenyans),
                    getValues(number,wenyans),
                    Utils.getString(WenYanLib.TYPE(),wenyans[0]).charAt(0)
                )
            );
        }
        if(Utils.matches(wenyans[0],WenYanLib.SIMPLE_VAR())){
            Utils.inputWenyan(compiler,0);
            List<String> values = new ArrayList<>();
            values.add(wenyans[0].substring(2));
            int number = 0;
            return new CompileResult(appendVar(
                    number+1,wenyans[number+1],
                    getNames(number,wenyans),
                    values,
                    Utils.getString(WenYanLib.TYPE(),wenyans[0]).charAt(0)
            ));
        }

        if(Utils.matches(wenyans[0],WenYanLib.CHANGE())){
            Utils.inputWenyan(compiler,0);
            String beforeName = Utils.getStringFrom(WenYanLib.BEFORE_NAME(),wenyans[0],WenYanLib.NAME_START(),WenYanLib.NAME_END());
            if(Utils.matches(wenyans[1],WenYanLib.AFTER_NAME())){
                Utils.inputWenyan(compiler,1);
                String afterName = Utils.getStringFrom(WenYanLib.AFTER_NAME(),wenyans[1],WenYanLib.NAME_START(),WenYanLib.NAME_END());
                return new CompileResult(beforeName+" = "+afterName);
            }
        }
        return new CompileResult(false,wenyans);
    }


    //变量者乎
    //TODO
    private String appendVar(int endIndex,String end,List<String> name, List<String> values, char type){
        if(name.size()==0&&Utils.matches(end,WenYanLib.WRITE())){
            Utils.inputWenyan(compiler,endIndex);
            String value = values.get(0);
            if(
                    value.startsWith(WenYanLib.NAME_START())&&value.endsWith(WenYanLib.NAME_END())
                    &&!value.startsWith(WenYanLib.STRING_START())&&!value.endsWith(WenYanLib.STRING_END())
            ){
                String varName = getName(value,false);
                return "println("+varName+")";
            }else{
                varIndex++;
                String systemName = getName(WenYanLib.NAME_START()+"ans_"+varIndex+WenYanLib.NAME_END(),true);
                List<String> systemNames = new ArrayList<>();
                systemNames.add(systemName);
                return parseType(type, systemNames, values)+"\n" +
                        "println("+systemName+")";
            }
        }else return parseType(type, name, values);
    }

    private String parseType(char type,List<String> name,List<String> values){
        StringBuilder head = new StringBuilder("def ");
        switch (type){
            case '數':
                return getVarString(true,head,name,values,this::getNumber);
            case '言':
                return getVarString(true,head,name,values,
                        val->{
                            if(!val.contains(WenYanLib.STRING_START())||!val.contains(WenYanLib.STRING_END())){
                                throw new SyntaxException("此非言也: "+val);
                            }
                            return "'"+val.substring(val.indexOf(WenYanLib.STRING_START())+2,val.lastIndexOf(WenYanLib.STRING_END()))+"'";
                        } );
            case '爻':
                return getVarString(true,head,name,values,val->WenYanLib.bool().get(val).get());
            case '列':
                return getVarString(false,head,name,values,val->"[]");
            case '物':
                return "";
            default:
                throw new SyntaxException("此'"+type+"'为何物邪?");
        }
    }

    private List<String> getNames(long number,String[] wenyans){
        List<String> names = new ArrayList<>();
        if(Utils.matches(wenyans[(int)number+1],WenYanLib.VAR_VALUE())){
            String[] ns = Utils.getString(WenYanLib.VAR_GET_NAME(),wenyans[(int)number+1]).split("曰");
            Utils.inputWenyan(compiler,(int)number+1);
            for(int i = 1;i<ns.length;i++) {
                names.add(getName(ns[i],true));
            }
        }
        return names;
    }

    private String getName(String name,boolean define){
        String chinese = name.substring(name.indexOf(WenYanLib.NAME_START()) + 1, name.lastIndexOf(WenYanLib.NAME_END()));
        if(varMap.containsValue(chinese)){
            if(define)
                throw new SyntaxException("物之名且唯一也: "+chinese);
            return varMap.get(chinese);
        }
        name = PinYinUtils.getPingYin(chinese,compiler.isSupportPinyin());
        if(varMap.containsKey(name)){
            varIndex++;
            name = name+varIndex;
            varMap.put(name,chinese);
        }else{
            varMap.put(name,chinese);
        }
        return name;
    }

    private List<String> getValues(long number,String[] wenyans){
        List<String> values = new ArrayList<>();
        for(int i = 1;i<=number;i++){
            if(wenyans.length == 1)break;
            if(Utils.matches(wenyans[i],WenYanLib.VAR_NAME())){
                Utils.inputWenyan(compiler,i);
                values.add(Utils.getStringFrom(WenYanLib.ALL(),wenyans[i],"曰"));
            }
        }
        return values;
    }




    private String getVarString(boolean mustNameValue,StringBuilder head,List<String> name, List<String> values, Function<String,Object> setValue){
        if(mustNameValue&&name.size() == 0)throw new SyntaxException("此地无造物者");
        if(mustNameValue&&name.size()!=values.size())throw new SyntaxException("君有"+name.size()+"之变量,而吾得"+values.size()+"也，嗟乎");
        for(int i = 0;i<name.size();i++){

            String def = name.get(i) + "=" + (i >= values.size()?"":setValue.apply(values.get(i)));
            if(name.size() == 1||i == name.size()-1) {
                head.append(def);
            }else {
                head.append(def).append(",");
            }
        }
        return head.toString();
    }

    public long getNumber(String wenyanNumber){
        int maxNumber = 0;
        long result = 0;
        if(ScalaUtils.containsCommonNumber(wenyanNumber)){
            int len = wenyanNumber.length()-1;
            char[] numberChar = wenyanNumber.toCharArray();
            for(char str : numberChar){
                result += (Integer)WenYanLib.numbers().get(str).get() * Math.pow(10,len);
                len -- ;
            }
            return result;
        }

        if(wenyanNumber.startsWith("十")){
            wenyanNumber = "一"+wenyanNumber;
        }
        char[] chars = wenyanNumber.toCharArray();
        for(int i = 0;i<chars.length;i++){
            if(i+1<=chars.length-1){
                if(WenYanLib.prefixs().contains(chars[i+1])){
                    int max = (Integer)WenYanLib.prefixs().get(chars[i+1]).get();
                    if(maxNumber == 0||max < maxNumber) {
                        result += max *
                                (Integer) WenYanLib.numbers().get(chars[i]).get();
                        if(maxNumber == 0)maxNumber = max;
                    }else{
                        result += (Integer) WenYanLib.numbers().get(chars[i]).get();
                        result = result * max;
                        maxNumber = max;
                    }
                    i++;
                }else{
                    result += (Integer)WenYanLib.numbers().get(chars[i]).get();
                }
            }else{
                result += (Integer)WenYanLib.numbers().get(chars[i]).get();
            }
        }
        return result;
    }

}
