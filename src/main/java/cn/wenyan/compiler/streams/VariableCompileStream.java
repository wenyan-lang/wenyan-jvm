package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.*;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.utils.GroovyUtils;
import cn.wenyan.compiler.utils.PinYinUtils;
import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.utils.Utils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;


public class VariableCompileStream extends CompileStream{


    private Map<String,Integer> arrIndex = new HashMap<>();


    private String nowName;

    private List<String> nowNames = new ArrayList<>();

    private Map<String,String> varMap = new HashMap<>();

    private long varIndex = 0;

    public VariableCompileStream(WenYanCompilerImpl compiler){
        super(compiler);
    }
    // 具之一句，而翻万里者也。
    public CompileResult compile(String[] wenyans) {
        if(Utils.matches(wenyans[0],WenYanLib.VAR_VALUE())){
            Utils.inputWenyan(compiler,0);
            wenyans[0] = "曰"+Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyans[0]);
            int index = 0;
            StringBuilder builder = new StringBuilder();
            int last = nowNames.size() - 1;
            int defineNumber = 0;
            while (Utils.matches(wenyans[index],WenYanLib.VAR_GET_NAME())){
                defineNumber ++;
                Utils.inputWenyan(compiler,index);
                index++;
            }
            index = 0;
            Stack<String> strings = new Stack<>();
            for(int i = 0;i<defineNumber;i++){
                strings.push(nowNames.get(last--));
            }
            int size = strings.size();
            for(int i = 0;i<size;i++){
                builder.append(LanguageUtils.defineVar(language,getName(wenyans[index++].substring(wenyans[index].indexOf("曰")+1),true),strings.pop())).append("\n");
            }
            return new CompileResult(builder.toString());
        }
        if(wenyans[0].equals("書之")){
            Utils.inputWenyan(compiler,0);
            return new CompileResult(LanguageUtils.println(language,nowName));
        }
        if(Utils.matches(wenyans[0],WenYanLib.YI())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult("");
        }
        //TODO
        if(Utils.matches(wenyans[0], WenYanLib.DEFINE_VAR())){
            Utils.inputWenyan(compiler,0);
            if(Utils.matches(wenyans[1],WenYanLib.DEFINE_ARG())){
                Utils.inputWenyan(compiler,1);
                String wuYiYan = Utils.getString(WenYanLib.NUMBER(),wenyans[1]);
                int number = Integer.parseInt(getNumber(wuYiYan).toString())+1;
                if(Utils.matches(wenyans[2],WenYanLib.VAR_VALUE())){
                    number = 1;
                }
                return new CompileResult(appendVar(
                        number+1,wenyans[number+1],
                        getNames(number,wenyans),
                        getValues(number,wenyans),
                        Utils.getString(WenYanLib.TYPE(),wenyans[1]).charAt(0)
                )
                );
            }

        }
        if(Utils.matches(wenyans[0],WenYanLib.OTHER())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult(LanguageUtils.defineVar(language,getAnsName(),"!"+Utils.getValue(Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyans[0]),this)));
        }
        //TODO
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
        String end = "";
        if(wenyans[2].equals("是也")||wenyans[2].equals("也")){
            Utils.inputWenyan(compiler,2);
            if(!wenyans[3].equals("若非")){
                end = language.getSyntax(Syntax.IF_END);
            }
        }
        if(wenyans[2].equals("是矣")||wenyans[2].equals("矣")){
            Utils.inputWenyan(compiler,2);
        }
        if(Utils.matches(changeCmd,WenYanLib.AFTER_NAME())){
            Utils.inputWenyan(compiler,1);
            int i = changeCmd.lastIndexOf("是")==-1?changeCmd.lastIndexOf("也")==-1?changeCmd.lastIndexOf("矣"):changeCmd.lastIndexOf("也"):changeCmd.lastIndexOf("是");
            String afterName = Utils.getValue(i <= 0?changeCmd.substring(changeCmd.indexOf("今")+1):changeCmd.substring(changeCmd.indexOf("今")+1,i),this);
            return new CompileResult(LanguageUtils.change(language,beforeName,afterName)+"\n"+end);
        }else if(changeCmd.equals(WenYanLib.IT_CHANGE())){
            Utils.inputWenyan(compiler,1);
            return new CompileResult(LanguageUtils.change(language,beforeName,nowName)+"\n"+end);
        }
        return new CompileResult(false,wenyans);
    }

    //变量者乎
    //TODO
    //endIndex就是结尾，为value定义后
    public String appendVar(int endIndex,String end,List<String> name, List<String> values, char type){
        if(name.size()==0&&Utils.matches(end,WenYanLib.WRITE())){
            String append = language.getSyntax(Syntax.STRING_APPEND);
            Utils.inputWenyan(compiler,endIndex);
            StringBuilder builder = new StringBuilder();
            for(String value : values){
                builder.append(Utils.getValue(value,this)).append(append);

            }
            return LanguageUtils.println(language,builder.toString().substring(0,builder.lastIndexOf(append)));

        }else return parseType(type, name, values);
    }

    public String getAnsName(){
        varIndex++;
        return getName(WenYanLib.NAME_START()+"ans_"+varIndex+WenYanLib.NAME_END(),true);
    }

    private String parseType(char type,List<String> name,List<String> values){
        StringBuilder head = new StringBuilder();
        Syntax syntax = WenYanLib.types().get(type+"").get();
        switch (type){
            case '數':
                return getVarString(syntax,type,head,name,values,this::getNumberString);
            case '言':
                return getVarString(syntax,type,head,name,values,
                       this::getString);
            case '爻':
                return getVarString(syntax,type,head,name,values,val->WenYanLib.bool().get(val).get());
            case '列':
                return getVarString(syntax,type,head,name,values,val->WenYanLib.define().get(type));
            case '物':
                return "";
            default:
                throw new SyntaxException("此'"+type+"'为何物邪?");
        }
    }

    public String getString(String val){
        return language.getSyntax(Syntax.STRING)+val.substring(val.indexOf(WenYanLib.STRING_START())+2,val.lastIndexOf(WenYanLib.STRING_END()))+language.getSyntax(Syntax.STRING);
    }

    private List<String> getNames(double number,String[] wenyans){
        int index = (int)number+1;//4
        List<String> names = new ArrayList<>();
        while (Utils.matches(wenyans[index],WenYanLib.VAR_VALUE())||Utils.matches(wenyans[index],WenYanLib.VAR_GET_NAME())){
            String ns = Utils.getString(WenYanLib.VAR_GET_NAME(),wenyans[index]);
            Utils.inputWenyan(compiler,index);
            names.add(getName(ns.substring(ns.indexOf(ns.charAt(0))+1),true));
            index++;
            if(index >= wenyans.length)break;
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
        nowNames.add(name);
        return name;
    }

    private List<String> getValues(double number,String[] wenyans){
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




    private String getVarString(Syntax typeInfo,char type,StringBuilder head,List<String> name, List<String> values, Function<String,Object> setValue){
        for(int i = 0;i<name.size();i++){
            String def;
            if(values.size()!=0) {
                if (values.get(i).contains(".")) {
                    if (typeInfo.equals(Syntax.INT_TYPE)) {

                        typeInfo = Syntax.DOUBLE_TYPE;
                    }
                } else {
                    if (typeInfo.equals(Syntax.DOUBLE_TYPE)) {

                        typeInfo = Syntax.INT_TYPE;
                    }
                }
            }
            if (i >= values.size()){
                def = LanguageUtils.defineVar(language,name.get(i),language.getSyntax(WenYanLib.define().get(type).get()),typeInfo);
            }else if(
                    values.get(i).startsWith("「")&&values.get(i).endsWith("」")
            ){
                def = LanguageUtils.defineVar(language,name.get(i),Utils.getValue(values.get(i),this),typeInfo);
            }else{
                def = LanguageUtils.defineVar(language,name.get(i),setValue.apply(values.get(i)).toString(),typeInfo);
            }


            head.append(def).append("\n");
        }
        return head.toString();
    }

    public String getArray(String get,VariableCompileStream stream){
        String[] gets = get.split("之");
        String name = Utils.getValue(gets[0],stream);
        String index = Utils.getValue(gets[1],stream);
        return LanguageUtils.getArray(language,name,index);
    }

    public String getNumberString(String wenyanNumber){
        String number = getNumber(wenyanNumber)+"";
        String intNumber = number.substring(number.lastIndexOf(".")+1);
        for(int i = 0;i<intNumber.length();i++){
            if(intNumber.charAt(i) != '0'){
                return number;
            }
        }
        return number.split("\\.")[0];
    }

    public BigDecimal getNumber(String wenyanNumber){
        return GroovyUtils.getNumber(wenyanNumber);
    }

    public Map<String, Integer> getArrIndex() {
        return arrIndex;
    }

    public String getNowName() {
        return nowName;
    }

}
