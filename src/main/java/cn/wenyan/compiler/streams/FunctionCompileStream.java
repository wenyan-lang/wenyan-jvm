package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FunctionCompileStream extends CompileStream {


    private int funcIndex = 0;

    private int stackNumber = 0;


    public FunctionCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(List<String> wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan,WenYanLib.DEFINE_VAR())){ //0
            compiler.removeWenyan();
            if(Utils.matches(wenyan, WenYanLib.FUNCTION())){ //1
                compiler.removeWenyan();
                if(Utils.matches(wenyan,WenYanLib.VAR_VALUE())){ //2
                    String value = compiler.removeWenyan();
                    String name = stream.getName(Utils.getString(WenYanLib.VAR_NAME_FOR(),value),false);
                    if(Utils.matches(wenyan,WenYanLib.NO_ARGS())){
                        compiler.removeWenyan();
                        return new CompileResult(defineFunc(name));
                    }else if(Utils.matches(wenyan,WenYanLib.ARGS())){
                        StringBuilder args = new StringBuilder();
                        compiler.removeWenyan();
                        if (Utils.matches(wenyan,WenYanLib.MUST())){
                            compiler.removeWenyan();
                            while (true){
                                if(Utils.matches(wenyan,WenYanLib.DEFINE_ARG())||Utils.matches(wenyan,WenYanLib.FUNCTION())){
                                    String value01 = compiler.removeWenyan();
                                    String wuYiYan = Utils.getString(WenYanLib.NUMBER(),value01);
                                    int len = Integer.parseInt(stream.getNumber(wuYiYan).toString());
                                    for(int i = 0;i<len;i++){
                                        if(Utils.matches(wenyan,WenYanLib.VAR_GET_NAME())){
                                            String get = compiler.removeWenyan();
                                            String defined = LanguageUtils.defineArg(language,stream.getName(Utils.getString(WenYanLib.VAR_NAME_FOR(),get),true));
                                            args.append(defined).append(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT));
                                        }else{
                                            break;
                                        }
                                    }
                                }
                                if(Utils.matches(wenyan,WenYanLib.DEFINE_END()))break;
                            }
                        }
                        if(Utils.matches(wenyan,WenYanLib.DEFINE_END())){
                            compiler.removeWenyan();
                            String args_str = args.toString().substring(0,args.lastIndexOf(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT)));
                            return new CompileResult(defineFunc(name,args_str));
                        }

                    }
                }
            }
        }

        if(Utils.matches(wenyan,WenYanLib.RETURN())){
            String value = compiler.removeWenyan();
            return new CompileResult(LanguageUtils.returnSomething(language,Utils.getValue(value.substring(value.indexOf("得")+1),stream)));
        }
        if(Utils.matches(wenyan,WenYanLib.FUNCTION_END())){
            funcIndex--;
            compiler.removeWenyan();
            return new CompileResult(language.getSyntax(Syntax.FUNCTION_END));
        }

        if(Utils.matches(wenyan,WenYanLib.STATEMENT())){
            String value = compiler.removeWenyan();
            String name = Utils.getValue(value.substring(value.indexOf("夫")+1),stream);
            String ans = stream.getAnsName();
            return new CompileResult(LanguageUtils.defineVar(language,ans,name));
        }
        if(Utils.matches(wenyan,WenYanLib.RUN_FUNCTION())){
            String value = compiler.removeWenyan();
            String find = Utils.getString(WenYanLib.VAR_NAME_FOR(),value);
            String name;
            if(find != null) {
                name = find;
            }else{
                name = value.substring(value.indexOf("施")+1);
            }
            StringBuilder builder = new StringBuilder();
            int i = 0;
            for(;;){
                if(Utils.matches(wenyan,WenYanLib.ARGS_RUN())){
                    String value01 = compiler.removeWenyan();

                    builder.append(Utils.getValue(value01.substring(value01.indexOf("於")+1),stream)).append(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT));
                    i++;
                }else{
                    break;
                }
            }
            String result;
            String funcName = stream.getAnsName();
            if(i == 0){
                List<String> nowArgs = getNowNames(stackNumber,stream);
                stackNumber = 0;
                StringBuilder build = new StringBuilder();
                for(int z = 0;z<nowArgs.size();z++){
                    build.append(nowArgs.get(z)).append(",");
                }
                result = build.substring(0,build.lastIndexOf(","));

            }else{
                if(builder.lastIndexOf(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT))!=-1){
                    result = builder.substring(0,builder.lastIndexOf(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT)));
                }else{
                    result = builder.toString();
                }
            }
            return new CompileResult(LanguageUtils.runFunction(language,funcName,stream.getName(name,true,true),result));
        }


        if(Utils.matches(wenyan,WenYanLib.FUNC_ARG())){
            String number = compiler.removeWenyan();
            stackNumber = Integer.parseInt(Utils.getValue(Utils.getString(WenYanLib.FOR(),number),stream));
            return new CompileResult("");
        }

        if(Utils.matches(wenyan,WenYanLib.IMPORT())){
            String value01= compiler.removeWenyan();

            String clz = getClassName(value01);
            String pack = library.get(clz);
            if(pack != null) clz = pack;
            StringBuilder builder = new StringBuilder(LanguageUtils.importClass(language,clz));
            builder.append("\n");
            if(Utils.matches(wenyan,WenYanLib.IMPORT_STATIC())){
                String value02 = compiler.removeWenyan();
                List<String> strs = Utils.getStrings(WenYanLib.VAR_NAME_FOR(),value02);

                if(Boolean.parseBoolean(language.getSyntax(Syntax.IMPORT_STATIC_SEPARATE))){
                    for(String s:strs){
                        String name = Utils.getValue(s,stream);
                        builder.append(LanguageUtils.importStatic(language,clz,name)).append("\n");
                    }
                }else{
                    StringBuilder method = new StringBuilder();
                    for(String s: strs){
                        String name = Utils.getValue(s,stream);
                        method.append(name).append(language.getSyntax(Syntax.IMPORT_SPLIT));
                    }
                    String methods = method.substring(0,method.lastIndexOf(language.getSyntax(Syntax.IMPORT_STATIC_SEPARATE)));
                    builder.append(LanguageUtils.importStatic(language,clz,methods)).append("\n");
                }



                return new CompileResult(builder.toString());
            }else{
                return new CompileResult(builder.toString());
            }
        }
        if(Utils.matches(wenyan,WenYanLib.RETURN_())){
            compiler.removeWenyan();
            return new CompileResult("return");
        }
        if(Utils.matches(wenyan,WenYanLib.MACRO_AFTER())){
            compiler.removeWenyan();
            return new CompileResult("");
        }
        return new CompileResult(false,wenyan);
    }

    //def a(a,b){
    //def a = {a,b ->
    private String defineFunc(String name,String args_str){

        funcIndex++;
        //if(funcIndex == 1)return LanguageUtils.defineFunction(language,name,args_str);
        return LanguageUtils.defineInnerFunction(language,name,args_str);
    }

    private String defineFunc(String name){
        funcIndex++;
        //if(funcIndex == 1)return LanguageUtils.defineFunction(language,name,"");
        return LanguageUtils.defineInnerFunction(language,name);
    }

    public String getClassName(String value01){
        List<String> clzs = Utils.getStrings(WenYanLib.STRING(),value01);
        StringBuilder builder01 = new StringBuilder();
        for(String s : clzs){
            String get = s.substring(s.indexOf(WenYanLib.STRING_START())+2,s.lastIndexOf(WenYanLib.STRING_END()));
            builder01.append(get).append(".");
        }
        return builder01.substring(0,builder01.lastIndexOf("."));
    }

    //取得最后的两个变量，并且按照先后顺序排列
    public List<String> getNowNames(int number,VariableCompileStream stream){
        int last = stream.getNowNames().size();
        int start = last - number;
        List<String> stack = new ArrayList<>(stream.getNowNames().subList(start,last));
        stream.getNowNames().removeAll(stack);
        return stack;
    }

}
