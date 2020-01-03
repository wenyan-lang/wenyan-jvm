package cn.wenyan.compiler;

import cn.wenyan.compiler.utils.Utils;

public class MathCompileStream extends CompileStream {


    public MathCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(String[] wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan[0],WenYanLib.MATH_START())){
            int index = 0;
            Utils.inputWenyan(compiler,index);
            String symbol = WenYanLib.math().get(wenyan[index].charAt(0)).get();
            String splitMath = Utils.getString(WenYanLib.SPLIT_MATH(),wenyan[index]);
            String[] numbers = wenyan[index].substring(1).split(splitMath);
            String number1 = Utils.getValue(numbers[index],stream);
            String number2 = Utils.getValue(numbers[1],stream);
            if(symbol.equals("/")) {
                if (Utils.matches(wenyan[index+1], WenYanLib.MOD())) {
                    index++;
                    Utils.inputWenyan(compiler, index);
                    symbol = "%";
                }
            }
            String name = stream.getAnsName();
            if(Utils.matches(wenyan[index+1],WenYanLib.VAR_VALUE())){
                index++;
                Utils.inputWenyan(compiler,index);
                name = stream.getName(Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[index]),false);
            }
            String result;
            if(splitMath.equals("以")){
                result = name+"="+number1+symbol+number2;
            }else{
                result = name+"="+number2+symbol+number1;
            }

            return new CompileResult(result);
        }
        return new CompileResult(false,wenyan);
    }
}
