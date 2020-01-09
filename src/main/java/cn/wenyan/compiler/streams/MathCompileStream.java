package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.script.libs.Syntax;
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
            String symbol = language.getSyntax(WenYanLib.math().get(wenyan[0].charAt(0)).get());
            String num = wenyan[0].substring(wenyan[0].indexOf(wenyan[0].charAt(0))+1);
            String number1 = Utils.getValue(num,stream);
            Utils.inputWenyan(compiler,0);
            if(Utils.matches(wenyan[1],WenYanLib.VAL())) {
                int index = 1;
                Utils.inputWenyan(compiler, index);
                String number2 = Utils.getValue(wenyan[1].substring(wenyan[1].indexOf(wenyan[1].charAt(0))+1), stream);
                if (index + 1 < wenyan.length) {
                    if (symbol.equals(language.getSyntax(Syntax.MATH_EXCEPT))) {
                        if (Utils.matches(wenyan[index + 1], WenYanLib.MOD())) {
                            index++;
                            Utils.inputWenyan(compiler, index);
                            symbol = language.getSyntax(Syntax.MATH_REMAIN);
                        }
                    }
                }
                String name = stream.getAnsName();

                String result;
                if (wenyan[1].charAt(0) == '以') {
                    if(symbol.equals(language.getSyntax(Syntax.MATH_REMAIN))){
                        number1 = LanguageUtils.numberSugar(language,number1);
                    }
                    result = LanguageUtils.defineVar(language,name,number1 + symbol + number2);
                } else {
                    result = LanguageUtils.defineVar(language,name,number2 + symbol + number1);
                }


                return new CompileResult(result);
            }
        }
        if(Utils.matches(wenyan[0],WenYanLib.AND_OR())){
            Utils.inputWenyan(compiler,0);
            String method = Utils.getString(WenYanLib.AND_OR(),wenyan[0]);
            List<String> strings = Utils.getStrings(WenYanLib.VAR_NAME_FOR(),wenyan[0]);
            if("有陽".equals(method)){
                return new CompileResult(LanguageUtils.defineVar(language,stream.getAnsName(),stream.getName(strings.get(0),false)+language.getSyntax(Syntax.OR)+stream.getName(strings.get(1),false)));
            }else if("無陰".equals(method)){
                return new CompileResult(LanguageUtils.defineVar(language,stream.getAnsName(),stream.getName(strings.get(0),false)+language.getSyntax(Syntax.AND)+stream.getName(strings.get(1),false)));
            }
        }
        return new CompileResult(false,wenyan);
    }
}
