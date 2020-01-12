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
    public CompileResult compile(List<String> wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan, WenYanLib.MATH_START())){
            String value01 = compiler.removeWenyan();//0
            String symbol = language.getSyntax(WenYanLib.math().get(value01.charAt(0)).get());
            String num = value01.substring(value01.indexOf(value01.charAt(0))+1);
            String number1 = Utils.getValue(num,stream);
            if(Utils.matches(wenyan,WenYanLib.VAL())) {

                String value02 = compiler.removeWenyan();//1
                String number2 = Utils.getValue(value02.substring(value02.indexOf(value02.charAt(0))+1), stream);
                if (symbol.equals(language.getSyntax(Syntax.MATH_EXCEPT))) {
                    if (Utils.matches(wenyan, WenYanLib.MOD())) {
                        compiler.removeWenyan();
                        symbol = language.getSyntax(Syntax.MATH_REMAIN);
                    }
                }
                String name = stream.getAnsName();

                String result;
                if (value02.charAt(0) == '以') {
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
        if(Utils.matches(wenyan,WenYanLib.AND_OR())){
            String value = compiler.removeWenyan();
            String method = Utils.getString(WenYanLib.AND_OR(),value);
            List<String> strings = Utils.getStrings(WenYanLib.VAR_NAME_FOR(),value);
            if("有陽".equals(method)){
                return new CompileResult(LanguageUtils.defineVar(language,stream.getAnsName(),stream.getName(strings.get(0),false)+language.getSyntax(Syntax.OR)+stream.getName(strings.get(1),false)));
            }else if("無陰".equals(method)){
                return new CompileResult(LanguageUtils.defineVar(language,stream.getAnsName(),stream.getName(strings.get(0),false)+language.getSyntax(Syntax.AND)+stream.getName(strings.get(1),false)));
            }
        }
        return new CompileResult(false,wenyan);
    }
}
