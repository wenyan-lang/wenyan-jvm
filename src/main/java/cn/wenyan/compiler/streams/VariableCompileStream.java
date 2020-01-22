package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.*;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.utils.GroovyUtils;
import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.utils.NumberTree;
import cn.wenyan.compiler.utils.ScalaUtils;
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



    public CompileResult compile(List<String> wenyans) {
        if(Utils.matches(wenyans,WenYanLib.VAR_VALUE())){
            List<String> nowWenYans = new ArrayList<>();
            wenyans.set(0,"曰"+Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyans.get(0)));
            StringBuilder builder = new StringBuilder();
            int last = nowNames.size() - 1;
            int defineNumber = 0;
            while (Utils.matches(wenyans,WenYanLib.VAR_GET_NAME())){
                defineNumber ++;
                nowWenYans.add(compiler.removeWenyan());
            }
            Stack<String> strings = new Stack<>();
            for(int i = 0;i<defineNumber;i++){
                strings.push(nowNames.get(last--));
            }
            int size = strings.size();
            for(int i = 0;i<size;i++){
                builder.append(LanguageUtils.defineVar(language,getName(nowWenYans.get(i).substring(nowWenYans.get(i).indexOf("曰")+1),true),strings.pop())).append("\n");
            }
            return new CompileResult(builder.toString());
        }
        if(Utils.matches(wenyans,WenYanLib.WRITE())){
            compiler.removeWenyan();
            return new CompileResult(LanguageUtils.println(language,nowName));
        }
        //清除堆栈
        if(Utils.matches(wenyans,WenYanLib.CLEAR_STACK())){
            compiler.removeWenyan();
            clearStack();
            return new CompileResult("");
        }
        //TODO
        if(Utils.matches(wenyans, WenYanLib.DEFINE_VAR())){
            compiler.removeWenyan();
            if(Utils.matches(wenyans,WenYanLib.DEFINE_ARG())){
                String value = compiler.removeWenyan();//吾有一言
                String wuYiYan = Utils.getString(WenYanLib.NUMBER(),value);
                int number = Integer.parseInt(getNumber(wuYiYan).toString())+1;
                List<String> values = getValues(wenyans);//曰...
                List<String> names = getNames(wenyans);//名之曰...
                //number 为数的数目
                //吾有一数，曰三，名之曰「和」
                return new CompileResult(appendVar(number,
                        names,
                        values,
                        Utils.getString(WenYanLib.TYPE(),value).charAt(0)
                )
                );
            }

        }
        if(Utils.matches(wenyans,WenYanLib.OTHER())){
            String value = compiler.removeWenyan();
            return new CompileResult(LanguageUtils.defineVar(language,getAnsName(),language.getSyntax(Syntax.NOT)+Utils.getValue(Utils.getString(WenYanLib.VAR_NAME_FOR(),value),this)));
        }
        //TODO
        if(Utils.matches(wenyans,WenYanLib.SIMPLE_VAR())){
            String value = compiler.removeWenyan();
            List<String> values = new ArrayList<>();
            values.add(value.substring(2));
            return new CompileResult(appendVar(1,
                    getNames(wenyans),
                    values,
                    Utils.getString(WenYanLib.TYPE(),value).charAt(0)
            ));
        }
        if(Utils.matches(wenyans,WenYanLib.CHANGE())){
            String value = compiler.removeWenyan();
            String beforeName = Utils.getStringFrom(WenYanLib.BEFORE_NAME(),value,WenYanLib.NAME_START(),WenYanLib.NAME_END());
            return change(beforeName,compiler.removeWenyan(),wenyans);
        }
        if(Utils.matches(wenyans,WenYanLib.REPLACE_ARRAY())){
            String value = compiler.removeWenyan();
            String val = value.substring(value.indexOf("昔之")+2,value.lastIndexOf("者"));
            if (Utils.matches(wenyans, WenYanLib.DELETE())) {
                compiler.removeWenyan();
                int middle = Utils.indexOf(val,'之');
                String name = getLeft(val,middle,this);
                String index = getRight(val,middle,this);
                return new CompileResult(LanguageUtils.removeArray(language,name,index));
            }else{
                String get = getArray(val,this);
                return change(get,compiler.removeWenyan(),wenyans);
            }

        }
        return new CompileResult(false,wenyans);
    }

    public void clearStack(){
        nowNames.clear();
        nowName = null;
    }


    public CompileResult change(String beforeName,String changeCmd,List<String> wenyans){
        String end = "";
        if(wenyans.get(0).equals("是也")||wenyans.get(0).equals("也")){
            compiler.removeWenyan();
            if(!wenyans.get(0).equals("若非")||!wenyans.get(0).equals("或")){
                compiler.removeWenyan();
                end = language.getSyntax(Syntax.IF_END);
            }
        }else if(wenyans.get(0).equals("是矣")||wenyans.get(0).equals("矣")){
            compiler.removeWenyan();
        }
        if(Utils.matches(changeCmd,WenYanLib.AFTER_NAME())){
            int i = changeCmd.lastIndexOf("是")==-1?changeCmd.lastIndexOf("也")==-1?changeCmd.lastIndexOf("矣"):changeCmd.lastIndexOf("也"):changeCmd.lastIndexOf("是");
            String afterName = Utils.getValue(i <= 0?changeCmd.substring(changeCmd.indexOf("今")+1):changeCmd.substring(changeCmd.indexOf("今")+1,i),this);
            return new CompileResult(LanguageUtils.change(language,beforeName,afterName)+"\n"+end);
        }else if(changeCmd.equals(WenYanLib.IT_CHANGE())){
            return new CompileResult(LanguageUtils.change(language,beforeName,nowName)+"\n"+end);
        }
        return new CompileResult(false,wenyans);
    }

    //变量者乎
    //TODO
    //endIndex就是结尾，为value定义后
    public String appendVar(int number,List<String> name, List<String> values, char type){
        if(name.size() == 0&&number > 1){
            StringBuilder builder = new StringBuilder("''");
            for(String value : values){
                builder.append(language.getSyntax(Syntax.STRING_APPEND)).append(Utils.getValue(value,this)).append(language.getSyntax(Syntax.STRING_APPEND)).append("''");
            }
            return LanguageUtils.defineVar(language,getAnsName(),builder.toString());
        }
        return parseType(type, name, values);
    }

    public String getAnsName(){
        varIndex++;
        return getName(WenYanLib.NAME_START()+"ans_"+varIndex+WenYanLib.NAME_END(),true);
    }

    public String parseType(char type,List<String> name,List<String> values){
        StringBuilder head = new StringBuilder();
        Syntax syntax = WenYanLib.types().get(type+"").get();
        switch (type){
            case '數':
                return getVarString(syntax,type,head,name,values,this::getNumberString);
            case '言':
                return getVarString(syntax,type,head,name,values,
                       this::getString);
            case '爻':
                return getVarString(syntax,type,head,name,values,val->language.getSyntax(WenYanLib.bool().get(val).get()));
            case '列':
                return getVarString(syntax,type,head,name,values,val->WenYanLib.define().get(type));
            case '物':
                return getVarString(syntax,type,head,name,values,val->language.getSyntax(Syntax.DEFINE_OBJECT));
            case '空':
                return getVarString(syntax,type,head,name,values,val->language.getSyntax(Syntax.NULL));
            case '元':
                return parseType(getType(values.size() == 0?"nil":values.get(0)),name,values);
            default:
                throw new SyntaxException("此'"+type+"'为何物邪?");
        }
    }

    private char getType(String value){
        if(value.matches(WenYanLib.numbersGet())){
            return '數';
        }
        if(value.startsWith(WenYanLib.STRING_START())&&value.endsWith(WenYanLib.STRING_END())){
            return '言';
        }
        if(value.equals(WenYanLib.TRUE())&&value.equals(WenYanLib.FALSE())){
            return '爻';
        }
        if(value.equals("nil")){
            return '空';
        }
        return '列';
    }

    public String getString(String val){
        return language.getSyntax(Syntax.STRING)+val.substring(val.indexOf(WenYanLib.STRING_START())+2,val.lastIndexOf(WenYanLib.STRING_END()))+language.getSyntax(Syntax.STRING);
    }

    private List<String> getNames(List<String> wenyans){
        List<String> names = new ArrayList<>();
        while (Utils.matches(wenyans, WenYanLib.VAR_VALUE()) || Utils.matches(wenyans, WenYanLib.VAR_GET_NAME())) {
            String val = compiler.removeWenyan();
            String ns = Utils.getString(WenYanLib.VAR_GET_NAME(), val);
            names.add(getName(ns.substring(ns.indexOf(ns.charAt(0)) + 1), true));
        }
        return names;
    }
    public String getName(String name,boolean define){
        return getName(name,define,false,false);
    }

    public String defineArgName(String name,boolean define){
        return getName(name,define,false,true);
    }



    public String getName(String name,boolean define,boolean runFunc,boolean defineArg){
        String chinese = name.substring(name.indexOf(WenYanLib.NAME_START()) + 1, name.lastIndexOf(WenYanLib.NAME_END()));
        FunctionCompileStream stream = compiler.getStream(FunctionCompileStream.class);
        if(varMap.containsValue(chinese)){
            return stream.getName(varMap.get(chinese),defineArg);
        }
        name = chinese;
        if(varMap.containsKey(name)){
            varIndex++;
            name = name+varIndex;
            varMap.put(name,chinese);
        }else{
            varMap.put(name,chinese);
        }
        if(!runFunc) {
            nowName = name;
            nowNames.add(name);
        }
        return stream.getName(name,defineArg);
    }

    private List<String> getValues(List<String> wenyans){
        List<String> values = new ArrayList<>();
        for(;;){
            if(wenyans.size() == 1)break;
            if(Utils.matches(wenyans,WenYanLib.VAR_NAME())){
                String val = compiler.removeWenyan();
                values.add(Utils.getStringFrom(WenYanLib.ALL(),val,"曰"));
            }else{
                break;
            }
        }
        return values;
    }




    private String getVarString(Syntax typeInfo,char type,StringBuilder head,List<String> name, List<String> values, Function<String,Object> setValue){
        for(int i = 0;i<Math.max(name.size(),values.size());i++){
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
            String getName =  i>name.size()?getAnsName():name.get(i);
            if (i >= values.size()){
                def = LanguageUtils.defineVar(language,getName,language.getSyntax(WenYanLib.define().get(type).get()));
            }else if(
                    values.get(i).startsWith("「")&&values.get(i).endsWith("」")
            ){
                def = LanguageUtils.defineVar(language,getName,Utils.getValue(values.get(i),this));
            }else{
                def = LanguageUtils.defineVar(language,getName,setValue.apply(values.get(i)).toString());
            }


            head.append(def).append("\n");
        }
        return head.toString();
    }

    public String getArray(String get,VariableCompileStream stream){
        int ind = Utils.indexOf(get,'之');
        return LanguageUtils.getArray(language,getLeft(get,ind,stream),getRight(get,ind,stream));
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
        if(!ScalaUtils.containsCommonNumber(wenyanNumber)){
            return GroovyUtils.getNumber(wenyanNumber);
        }
        NumberTree tree = new NumberTree();
        return tree.inputNumber(wenyanNumber).convertToNumber();
    }

    public Map<String, Integer> getArrIndex() {
        return arrIndex;
    }

    public String getNowName() {
        return nowName;
    }

    public List<String> getNowNames() {
        return nowNames;
    }

    private String getLeft(String get,int ind,VariableCompileStream stream){
        String nameString = get.substring(0,ind);
        return Utils.getValue(nameString,stream);
    }
    private String getRight(String get,int ind,VariableCompileStream stream){
        String valueString = get.substring(ind+1);

        return Utils.getValue(valueString,stream);
    }
}
