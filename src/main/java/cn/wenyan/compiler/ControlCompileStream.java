package cn.wenyan.compiler;

import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.utils.Utils;

public class ControlCompileStream extends CompileStream {


    long index = 0;


    public ControlCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(String[] wenyan) {
        if(Utils.matches(wenyan[0],WenYanLib.FOR())){
            Utils.inputWenyan(compiler,0);
            String str = Utils.getString(WenYanLib.FOR(),wenyan[0]);
            if(str != null) {
                str = compiler.getStream(VariableCompileStream.class).getNumber(str)+"";
            }else{
                String var = Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[0]);
                str = compiler.getStream(VariableCompileStream.class).getName(var,false);

            }
            index++;
            return new CompileResult("for(_ans" + index + " in 1.." +str + "){");
        }
        if(Utils.matches(wenyan[0],WenYanLib.FOR_END())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult("}");
        }
        return new CompileResult(false,wenyan);
    }

    private String getBooleanSyntax(String wenYan){
        if(wenYan.contains(WenYanLib.NOT_BIG_THAN())){
            return getBool(WenYanLib.NOT_BIG_THAN(),wenYan);
        }else if(wenYan.contains(WenYanLib.BIG_THAN())){
            return getBool(WenYanLib.BIG_THAN(),wenYan);
        }else if(wenYan.contains(WenYanLib.NOT_SMALL_THAN())){
            return getBool(WenYanLib.NOT_SMALL_THAN(),wenYan);
        }else if(wenYan.contains(WenYanLib.SMALL_THAN())){
            return getBool(WenYanLib.SMALL_THAN(),wenYan);
        }
        throw new SyntaxException("无此对比也: "+wenYan);
    }

    public String getBool(String type,String wenYan){
        String[] numbers = wenYan.split(type);
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        return stream.getNumber(numbers[0])+WenYanLib.bool().get(type).get()+stream.getNumber(numbers[1]);
    }
}
