package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.utils.PinYinUtils;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.utils.ScalaUtils;
import cn.wenyan.compiler.utils.Utils;

import java.util.*;
import java.util.function.Function;


public class VariableCompileStream extends CompileStream{




    private String nowName;

    private Map<String,String> varMap = new HashMap<>();

    private long varIndex = 0;

    public VariableCompileStream(WenYanCompilerImpl compiler){
        super(compiler);
    }
    // 具之一句，而翻万里者也。
    public CompileResult compile(String[] wenyans) {

        if(Utils.matches(wenyans[0],WenYanLib.VAR_VALUE())){
            Utils.inputWenyan(compiler,0);
            String name = Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyans[0]);
            String now = nowName;
            return new CompileResult("def "+getName(name,false)+" = "+now);
        }
        if(wenyans[0].equals("書之")){
            Utils.inputWenyan(compiler,0);
            return new CompileResult("println("+nowName+")");
        }
        if(Utils.matches(wenyans[0],WenYanLib.YI())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult("");
        }
        if(Utils.matches(wenyans[0], WenYanLib.DEFINE_VAR())){
            Utils.inputWenyan(compiler,0);
            if(Utils.matches(wenyans[1],WenYanLib.DEFINE_ARG())){
                Utils.inputWenyan(compiler,1);
                String wuYiYan = Utils.getString(WenYanLib.NUMBER(),wenyans[1]);
                long number = getNumber(wuYiYan)+1;
                if(Utils.matches(wenyans[2],WenYanLib.VAR_VALUE())){
                    number = 1;
                }
                return new CompileResult(appendVar(
                        (int)number+1,wenyans[(int)number+1],
                        getNames(number,wenyans),
                        getValues(number,wenyans),
                        Utils.getString(WenYanLib.TYPE(),wenyans[1]).charAt(0)
                )
                );
            }

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
            return change(beforeName,wenyans[1],wenyans);
        }
        if(Utils.matches(wenyans[0],WenYanLib.REPLACE_ARRAY())){
            Utils.inputWenyan(compiler,0);
            String get = getArray(wenyans[0].substring(wenyans[0].indexOf("昔之")+2,wenyans[0].lastIndexOf("者")),this);
            return change(get,wenyans[1],wenyans);
        }
        return new CompileResult(false,wenyans);
    }


    public CompileResult change(String beforeName,String changeCmd,String[] wenyans){
        if(Utils.matches(changeCmd,WenYanLib.AFTER_NAME())){
            Utils.inputWenyan(compiler,1);
            String afterName = Utils.getValue(changeCmd.substring(changeCmd.indexOf("今")+1,changeCmd.lastIndexOf("是")),this);
            return new CompileResult(beforeName+" = "+afterName);
        }else if(changeCmd.equals(WenYanLib.IT_CHANGE())){
            Utils.inputWenyan(compiler,1);
            return new CompileResult(beforeName+" = "+nowName);
        }
        return new CompileResult(false,wenyans);
    }

    //变量者乎
    //TODO
    //endIndex就是结尾，为value定义后
    public String appendVar(int endIndex,String end,List<String> name, List<String> values, char type){
        if(name.size()==0&&Utils.matches(end,WenYanLib.WRITE())){
            Utils.inputWenyan(compiler,endIndex);
            StringBuilder builder = new StringBuilder();
            for(String value : values){
                builder.append(Utils.getValue(value,this)).append("+");

            }
            return "println("+builder.toString().substring(0,builder.lastIndexOf("+"))+")";

        }else return parseType(type, name, values);
    }

    public String getAnsName(){
        varIndex++;
        return getName(WenYanLib.NAME_START()+"ans_"+varIndex+WenYanLib.NAME_END(),true);
    }

    private String parseType(char type,List<String> name,List<String> values){
        StringBuilder head = new StringBuilder("def ");
        switch (type){
            case '數':
                return getVarString(type,true,head,name,values,this::getNumber);
            case '言':
                return getVarString(type,true,head,name,values,
                       this::getString);
            case '爻':
                return getVarString(type,true,head,name,values,val->WenYanLib.bool().get(val).get());
            case '列':
                return getVarString(type,false,head,name,values,val->"[]");
            case '物':
                return "";
            default:
                throw new SyntaxException("此'"+type+"'为何物邪?");
        }
    }

    public String getString(String val){
        return "'"+val.substring(val.indexOf(WenYanLib.STRING_START())+2,val.lastIndexOf(WenYanLib.STRING_END()))+"'";
    }

    private List<String> getNames(long number,String[] wenyans){
        int index = (int)number+1;//4
        List<String> names = new ArrayList<>();
        while (Utils.matches(wenyans[index],WenYanLib.VAR_VALUE())||Utils.matches(wenyans[index],WenYanLib.VAR_GET_NAME())){
            String ns = Utils.getString(WenYanLib.VAR_GET_NAME(),wenyans[index]);
            Utils.inputWenyan(compiler,index);
            names.add(getName(ns.substring(ns.indexOf(ns.charAt(0))+1),true));
            index++;
        }
        return names;
    }

    public String getName(String name,boolean define){
        String chinese = name.substring(name.indexOf(WenYanLib.NAME_START()) + 1, name.lastIndexOf(WenYanLib.NAME_END()));
        //if(!define&&varMap.get(chinese) == null) throw new SyntaxException("此變量非定義也:"+name+" 於 「「 "+compiler.getNow()+" 」」");
        if(varMap.containsValue(chinese)){
            if(define)
                compiler.getServerLogger().warning(" 物之名且唯一也: "+name);
            return varMap.get(chinese);
        }
        try {
            Class.forName("net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination");
            name = PinYinUtils.getPingYin(chinese, compiler.isSupportPinyin());
        }catch (ClassNotFoundException e){
            name = chinese;
        }
        if(varMap.containsKey(name)){
            varIndex++;
            name = name+varIndex;
            varMap.put(name,chinese);
        }else{
            varMap.put(name,chinese);
        }
        nowName = name;
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




    private String getVarString(char type,boolean mustNameValue,StringBuilder head,List<String> name, List<String> values, Function<String,Object> setValue){
        for(int i = 0;i<name.size();i++){
            String def;
            if (i >= values.size()){
                def = name.get(i) + "=" +WenYanLib.define().get(type).get();
            }else if(
                    values.get(i).startsWith("「")&&values.get(i).endsWith("」")
            ){
                def = name.get(i)+ " = " +Utils.getValue(values.get(i),this);
            }else{
                def = name.get(i) + "=" + setValue.apply(values.get(i));
            }

            if(name.size() == 1||i == name.size()-1) {
                head.append(def);
            }else {
                head.append(def).append(",");
            }
        }
        return head.toString();
    }

    public String getArray(String get,VariableCompileStream stream){
        String[] gets = get.split("之");
        String name = Utils.getValue(gets[0],stream);
        String index = Utils.getValue(gets[1],stream);
        String indexNumber;
        if(index.matches("[0-9]+")){
            indexNumber = index +"-1";
        }else{
            indexNumber = "("+index+".class == java.lang.Integer.class?"+index+"-1:"+index+")";
        }
        return name+"["+indexNumber+"]";
    }

    public long getNumber(String wenyanNumber){
        int maxNumber = 0;
        long result = 0;
        if(!ScalaUtils.containsCommonNumber(wenyanNumber)){
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

    public String getNowName() {
        return nowName;
    }

}
