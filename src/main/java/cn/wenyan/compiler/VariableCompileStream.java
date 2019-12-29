package cn.wenyan.compiler;

import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.utils.Utils;

import java.util.*;
import java.util.function.Function;


public class VariableCompileStream extends CompileStream{

    private Map<String,String> varMap = new HashMap<>();

    private int varIndex = 0;

    private List<String> nextVars;

    VariableCompileStream(WenYanCompiler compiler){
        super(compiler);
    }
    // 具之一句，而翻万里者也。
    public CompileResult compile(String wenyan) {
        compiler.setNow(wenyan);
        String[] wenyans = wenyan.split("。");
        List<String> newWenyans = new ArrayList<>(Arrays.asList(wenyans));
        appendSplit(newWenyans,wenyans);
        wenyans = newWenyans.toArray(new String[0]);
        if(Utils.matches(wenyans[0],WenYanLib.DEFINE_VAR())){
            long number = getNumber(Utils.getString(WenYanLib.NUMBER(),wenyans[0]));
            return new CompileResult(true,appendVar(
                    wenyans[(int)number+1],
                    getNames(number,wenyans),
                    getValues(number,wenyans),
                    Utils.getString(WenYanLib.TYPE(),wenyans[0]).charAt(0)
            )
            );
        }
        return new CompileResult(false,wenyan);
    }

    private void appendSplit(List<String> newWenyans,String[] wenyans){
        //此处要解决歧义问题，如果'。'在字符串出现如何解决
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<newWenyans.size();i++){
            int index = newWenyans.get(i).indexOf("「「");
            if(index != -1) {
                int endIndex = newWenyans.get(i).indexOf("」」", index);
                if (endIndex == -1) {
                    builder.append(newWenyans.get(i));
                    int removed = 0;
                    for (int j = i+1; j < wenyans.length; j++) {
                        builder.append("。").append(newWenyans.get(j));
                        removed++;
                        if (builder.indexOf("」」", index) != -1) {
                            for(int z = 0;z<removed;z++){
                                newWenyans.remove(i+1);
                            }
                            newWenyans.set(i,builder.toString());
                            break;
                        }
                    }
                }
                builder = new StringBuilder();
            }
        }
    }
    //变量者乎
    //TODO
    private String appendVar(String end,List<String> name, List<String> values, char type){
        if(name.size()==0&&Utils.matches(end,WenYanLib.WRITE())){
            //TODO  这里就是输出的编译地方
            String value = values.get(0);
            if(
                    value.startsWith("「")&&value.endsWith("」")
                    &&!value.startsWith("「「")&&!value.endsWith("」」")
            ){
                String varName = getName(value);
                return "println("+varName+")";
            }else{
                String systemName = getName("「ans」");
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
                return getVarString(head,name,values,this::getNumber);
            case '言':
                return getVarString(head,name,values,
                        val->{
                            if(!val.contains("「「")||!val.contains("」」")){
                                throw new SyntaxException("此非言也: "+val);
                            }
                            return "'"+val.substring(val.indexOf("「「")+2,val.lastIndexOf("」」"))+"'";
                        } );
            case '爻':
                return getVarString(head,name,values,val->WenYanLib.bool().get(val).get());
            case '列':
                return "";
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
            for(int i = 1;i<ns.length;i++) {
                names.add(getName(ns[i]));
            }
        }
        return names;
    }

    private String getName(String name){
        String chinese = name.substring(name.indexOf("「") + 1, name.lastIndexOf("」"));
        if(varMap.containsValue(chinese)){
            throw new SyntaxException("物之名且唯一也: "+chinese);
        }
        name = PinYinUtils.getPingYin(chinese);
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
                values.add(Utils.getStringFrom(WenYanLib.ALL(),wenyans[i],"曰"));
            }
        }
        return values;
    }




    private String getVarString(StringBuilder head,List<String> name, List<String> values, Function<String,Object> setValue){
        if(name.size() == 0)throw new SyntaxException("此地无造物者");
        if(name.size()!=values.size())throw new SyntaxException("君有"+name.size()+"之变量,而吾得"+values.size()+"也，嗟乎");
        for(int i = 0;i<name.size();i++){
            String def = name.get(i) + "=" + setValue.apply(values.get(i));
            if(name.size() == 1||i == name.size()-1) {
                head.append(def);
            }else {
                head.append(def).append(",");
            }
        }
        return head.toString();
    }

    private long getNumber(String wenyanNumber){
        int maxNumber = 0;
        long result = 0;
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
