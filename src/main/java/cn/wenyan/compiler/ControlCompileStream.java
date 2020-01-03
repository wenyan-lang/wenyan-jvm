package cn.wenyan.compiler;

import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.utils.Utils;

import static cn.wenyan.compiler.utils.Utils.getValue;

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
        if(Utils.matches(wenyan[0],WenYanLib.IF_END())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult("}");
        }
        if(Utils.matches(wenyan[0],WenYanLib.IF_START())){
            Utils.inputWenyan(compiler,0);
            String bool = getBooleanSyntax(wenyan[0].substring(wenyan[0].indexOf("若")+1,wenyan[0].indexOf("者")));
            return new CompileResult("if("+bool+"){");
        }
        if(Utils.matches(wenyan[0],WenYanLib.IF_BREAK())){
            Utils.inputWenyan(compiler,0);
            String bool = getBooleanSyntax(wenyan[0].substring(wenyan[0].indexOf("若")+1,wenyan[0].indexOf("者")));
            return new CompileResult("if("+bool+")break");
        }
        if(Utils.matches(wenyan[0],WenYanLib.WHILE())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult("while(true){");
        }
        if(Utils.matches(wenyan[0],WenYanLib.ELSE())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult("}else{");
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
        }else if(wenYan.contains(WenYanLib.EQUALS())){
            return getBool(WenYanLib.EQUALS(),wenYan);
        }
        throw new SyntaxException("无此对比也: "+wenYan);
    }

    //TODO 未完成
    public String getBool(String type,String wenYan){
        String[] numbers = wenYan.split(type);
        if(numbers.length != 2)throw new SyntaxException("此表达式之过也: "+wenYan);
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        StringBuilder builder = new StringBuilder();
        builder.append(getValue(numbers[0],stream));
        builder.append(WenYanLib.bool().get(type).get());
        builder.append(getValue(numbers[1],stream));
        return builder.toString();
    }



}
