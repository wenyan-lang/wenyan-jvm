package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.utils.Utils;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

import java.util.List;


public class FunctionCompileStream extends CompileStream {

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
                        return new CompileResult("def "+name+"(){");
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
                                            args.append(stream.getName(Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[j]),false)).append(",");
                                        }else{
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if(Utils.matches(wenyan[count],WenYanLib.DEFINE_END())){
                            Utils.inputWenyan(compiler,count);
                            String args_str = args.toString().substring(0,args.lastIndexOf(","));
                            return new CompileResult("def "+name+"("+args_str+"){");
                        }

                    }
                }
            }
        }

        if(Utils.matches(wenyan[0],WenYanLib.RETURN())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult("return "+Utils.getValue(wenyan[0].substring(wenyan[0].indexOf("得")+1),stream));
        }
        if(Utils.matches(wenyan[0],WenYanLib.FUNCTION_END())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult("}");
        }
        if(Utils.matches(wenyan[0],WenYanLib.RUN_FUNCTION())){
            Utils.inputWenyan(compiler,0);
            String name = Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[0]);
            StringBuilder builder = new StringBuilder();
            builder.append(Utils.getValue(name,stream)).append("(");
            for(int i = 1;i<wenyan.length;i++){

                if(Utils.matches(wenyan[i],WenYanLib.ARGS_RUN())){
                    Utils.inputWenyan(compiler,i);
                    builder.append(Utils.getValue( Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[i]),stream)).append(",");
                }
            }
            String result;
            if(builder.lastIndexOf(",")!=-1){
                result = builder.substring(0,builder.lastIndexOf(","));
            }else{
                result = builder.toString();
            }
            return new CompileResult(result+")");
        }

        if(Utils.matches(wenyan[0],WenYanLib.IMPORT())){
            Utils.inputWenyan(compiler,0);
            String clz = Utils.getString(WenYanLib.STRING(),wenyan[0]).replace("之",".");
            clz = clz.substring(clz.indexOf(WenYanLib.STRING_START())+2,clz.lastIndexOf(WenYanLib.STRING_END()));
            if(Utils.matches(wenyan[1],WenYanLib.IMPORT_STATIC())){
                Utils.inputWenyan(compiler,1);
                List<String> strs = Utils.getStrings(WenYanLib.VAR_NAME_FOR(),wenyan[1]);
                StringBuilder builder = new StringBuilder();
                for(String s:strs){
                    String name = Utils.getValue(s,stream);
                    builder.append("import static ").append(clz).append(".").append(name).append("\n");
                }
                return new CompileResult(builder.toString());
            }else{
                return new CompileResult("import "+clz);
            }
        }

        return new CompileResult(false,wenyan);
    }
}
