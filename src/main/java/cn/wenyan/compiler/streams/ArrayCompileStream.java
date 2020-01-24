package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.utils.Utils;
import org.codehaus.groovy.runtime.typehandling.BigDecimalMath;

import java.util.List;

public class ArrayCompileStream extends CompileStream {

    public ArrayCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(List<String> wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan,WenYanLib.LENGTH())){
            String value = compiler.removeWenyan();
            return new CompileResult(
                    LanguageUtils.defineVar(language,stream.getAnsName(),Utils.getValue(value.substring(value.indexOf("夫")+1),stream))
            );
        }
        if(Utils.matches(wenyan, WenYanLib.ADD())){
            String value01 = compiler.removeWenyan();
            String name = Utils.getValue(Utils.getString(WenYanLib.VAR_NAME_FOR(),value01),stream);
            StringBuilder result = new StringBuilder();
            while (Utils.matches(wenyan,WenYanLib.VAL())){
                String value02 = compiler.removeWenyan();

                result.append(LanguageUtils.addArray(
                        language,
                        name,
                        Utils.getValue(value02.substring(value02.indexOf(value02.charAt(0)) + 1), stream)
                        )
                ).append("\n");
            }
            return new CompileResult(result.toString());
        }
        if(Utils.matches(wenyan,WenYanLib.GET())){
            String value = compiler.removeWenyan();
            String get = value.substring(value.indexOf("夫")+1);
            String val = stream.getArray(get,stream);

            return new CompileResult(
                    LanguageUtils.defineVar(language,stream.getAnsName(),val)
            );
        }
        if(Utils.matches(wenyan,WenYanLib.CONCAT())){
            String name = compiler.removeWenyan();
            name = Utils.getValue(name.substring(name.indexOf("銜")+1),stream);
            StringBuilder builder = new StringBuilder();
            while (Utils.matches(wenyan,WenYanLib.VAL())){
                String value = compiler.removeWenyan();
                String valueGet = Utils.getValue(value.substring(value.indexOf("以")+1),stream);
                builder.append(LanguageUtils.putAll(language,name,valueGet)).append("\n");
            }
            return new CompileResult(builder.toString());
        }
        return new CompileResult(false,wenyan);
    }


}
