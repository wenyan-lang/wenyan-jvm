package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.utils.Utils;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

import java.util.Arrays;
import java.util.List;


public class FunctionCompileStream extends CompileStream {


    private int funcIndex = 0;

    public FunctionCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(String[] wenyan) {
        int count = 0;
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan[count++],WenYanLib.DEFINE_VAR())){
            Utils.inputWenyan(compiler,count-1);
            if(Utils.matches(wenyan[count++], WenYanLib.FUNCTION())){
                Utils.inputWenyan(compiler,count-1);
                if(Utils.matches(wenyan[count++],WenYanLib.VAR_VALUE())){
                    Utils.inputWenyan(compiler,count-1);
                    String name = stream.getName(Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[2]),false);
                    if(Utils.matches(wenyan[count],WenYanLib.NO_ARGS())){
                        Utils.inputWenyan(compiler,count);
                        return new CompileResult(defineFunc(name));
                    }else if(Utils.matches(wenyan[count],WenYanLib.ARGS())){
                        count++;
                        StringBuilder args = new StringBuilder();
                        Utils.inputWenyan(compiler,count-1);
                        if (Utils.matches(wenyan[count++],WenYanLib.MUST())){
                            Utils.inputWenyan(compiler,count-1);
                            for(int i = count;i<wenyan.length;i++){
                                if(Utils.matches(wenyan[i],WenYanLib.DEFINE_ARG())){
                                    count++;
                                    Utils.inputWenyan(compiler,i);
                                    String wuYiYan = Utils.getString(WenYanLib.NUMBER(),wenyan[i]);
                                    long len = stream.getNumber(wuYiYan);
                                    int now = i;
                                    for(int j = i+1;j<len+now+1;j++,i++){
                                        if(Utils.matches(wenyan[j],WenYanLib.VAR_GET_NAME())){
                                            count++;
                                            Utils.inputWenyan(compiler,j);
                                            args.append(stream.getName(Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[j]),false)).append(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT));
                                        }else{
                                            break;
                                        }
                                    }
                                }
                                if(Utils.matches(wenyan[i],WenYanLib.DEFINE_END()))break;
                            }
                        }
                        if(Utils.matches(wenyan[count],WenYanLib.DEFINE_END())){
                            Utils.inputWenyan(compiler,count);
                            String args_str = args.toString().substring(0,args.lastIndexOf(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT)));
                            return new CompileResult(defineFunc(name,args_str));
                        }

                    }
                }
            }
        }

        if(Utils.matches(wenyan[0],WenYanLib.RETURN())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult(LanguageUtils.returnSomething(language,Utils.getValue(wenyan[0].substring(wenyan[0].indexOf("得")+1),stream)));
        }
        if(Utils.matches(wenyan[0],WenYanLib.FUNCTION_END())){
            funcIndex--;
            Utils.inputWenyan(compiler,0);
            return new CompileResult(language.getSyntax(Syntax.FUNCTION_END));
        }
        if(Utils.matches(wenyan[0],WenYanLib.RUN_FUNCTION())){
            Utils.inputWenyan(compiler,0);
            String find = Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[0]);
            String name;
            if(find != null) {
                name = find.replace("之", language.getSyntax(Syntax.OBJECT_INNER));
            }else{
                name = wenyan[0].substring(wenyan[0].indexOf("施")+1).replace("之", language.getSyntax(Syntax.OBJECT_INNER));
            }
            StringBuilder builder = new StringBuilder();
            int end = 0;
            for(int i = 1;i<wenyan.length;i++){
                if(Utils.matches(wenyan[i],WenYanLib.ARGS_RUN())){
                    end++;
                    Utils.inputWenyan(compiler,i);

                    builder.append(Utils.getValue(wenyan[i].substring(wenyan[i].indexOf("於")+1),stream)).append(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT));
                }else{
                    break;
                }
            }
            String result;
            if(builder.lastIndexOf(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT))!=-1){
                result = builder.substring(0,builder.lastIndexOf(language.getSyntax(Syntax.FUNCTION_ARGS_SPLIT)));
            }else{
                result = builder.toString();
            }
            String returnName;
            if(end+1>=wenyan.length){
                returnName = stream.getAnsName();
            }else if(Utils.matches(wenyan[end+1],WenYanLib.VAR_VALUE())){
                Utils.inputWenyan(compiler,end+1);
                returnName = Utils.getValue(Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[end+1]),stream);
            }else{
                returnName = stream.getAnsName();
            }
            return new CompileResult(LanguageUtils.runFunction(language,returnName,Utils.getValue(name,stream),result));
        }

        if(Utils.matches(wenyan[0],WenYanLib.IMPORT())){
            Utils.inputWenyan(compiler,0);
            String clz = Utils.getString(WenYanLib.STRING(),wenyan[0]).replace("之",language.getSyntax(Syntax.OBJECT_INNER));
            clz = clz.substring(clz.indexOf(WenYanLib.STRING_START())+2,clz.lastIndexOf(WenYanLib.STRING_END()));
            if(Utils.matches(wenyan[1],WenYanLib.IMPORT_STATIC())){
                Utils.inputWenyan(compiler,1);
                List<String> strs = Utils.getStrings(WenYanLib.VAR_NAME_FOR(),wenyan[1]);
                StringBuilder builder = new StringBuilder();
                if(Boolean.parseBoolean(language.getSyntax(Syntax.IMPORT_SPLIT))){
                    for(String s:strs){
                        String name = Utils.getValue(s,stream);
                        builder.append(LanguageUtils.importStatic(language,clz,name)).append("\n");
                    }
                }else{
                    StringBuilder method = new StringBuilder();
                    for(String s: strs){
                        String name = Utils.getValue(s,stream);
                        method.append(name).append(language.getSyntax(Syntax.IMPORT_STATIC_SEPARATE));
                    }
                    String methods = method.substring(0,method.lastIndexOf(language.getSyntax(Syntax.IMPORT_STATIC_SEPARATE)));
                    builder.append(LanguageUtils.importStatic(language,clz,methods)).append("\n");
                }

                return new CompileResult(builder.toString());
            }else{
                return new CompileResult(LanguageUtils.importClass(language,clz));
            }
        }

        return new CompileResult(false,wenyan);
    }

    //def a(a,b){
    //def a = {a,b ->
    private String defineFunc(String name,String args_str){
        funcIndex++;
        if(funcIndex == 1)return LanguageUtils.defineFunction(language,name,args_str);
        return LanguageUtils.defineInnerFunction(language,name,args_str);
    }

    private String defineFunc(String name){
        funcIndex++;
        if(funcIndex == 1)return LanguageUtils.defineFunction(language,name,"");
        return LanguageUtils.defineInnerFunction(language,name);
    }
}
