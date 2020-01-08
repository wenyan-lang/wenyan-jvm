package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.utils.Utils;

import java.util.List;

public class MathCompileStream extends CompileStream {


    public MathCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(String[] wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan[0], WenYanLib.MATH_START())){
            String symbol = WenYanLib.math().get(wenyan[0].charAt(0)).get();
            String num = wenyan[0].substring(wenyan[0].indexOf(wenyan[0].charAt(0))+1);
            String number1 = Utils.getValue(num,stream);
            Utils.inputWenyan(compiler,0);
            if(Utils.matches(wenyan[1],WenYanLib.VAL())) {
                int index = 1;
                Utils.inputWenyan(compiler, index);
                String number2 = Utils.getValue(wenyan[1].substring(wenyan[1].indexOf(wenyan[1].charAt(0))+1), stream);
                String name = "";
                if (index + 1 < wenyan.length) {
                    if (symbol.equals("/")) {
                        if (Utils.matches(wenyan[index + 1], WenYanLib.MOD())) {
                            index++;
                            Utils.inputWenyan(compiler, index);
                            symbol = "%";
                        }
                    }
//                    if (Utils.matches(wenyan[index + 1], WenYanLib.VAR_VALUE())) {
//                        index++;
//                        Utils.inputWenyan(compiler, index);
//                        name = stream.getName(Utils.getString(WenYanLib.VAR_NAME_FOR(), wenyan[index]), false);
//                    }
                }
                if (name.equals("")) name = stream.getAnsName();

                String result;
                if (wenyan[1].charAt(0) == '以') {
                    if(symbol.equals("%")){
                        number1 = "((Integer)"+number1+")";
                    }
                    result = name + "=" + number1 + symbol + number2;
                } else {
                    result = name + "=" + number2 + symbol + number1;
                }


                return new CompileResult("def "+result);
            }
        }
        if(Utils.matches(wenyan[0],WenYanLib.AND_OR())){
            Utils.inputWenyan(compiler,0);
            String method = Utils.getString(WenYanLib.AND_OR(),wenyan[0]);
            List<String> strings = Utils.getStrings(WenYanLib.VAR_NAME_FOR(),wenyan[0]);
            if("有陽".equals(method)){
                return new CompileResult("def "+stream.getAnsName()+"="+stream.getName(strings.get(0),false)+"||"+stream.getName(strings.get(1),false));
            }else if("無陰".equals(method)){
                return new CompileResult("def "+stream.getAnsName()+"="+stream.getName(strings.get(0),false)+"&&"+stream.getName(strings.get(1),false));
            }
        }
        return new CompileResult(false,wenyan);
    }
}
