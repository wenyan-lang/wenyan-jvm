package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.utils.Utils;

public class ArrayCompileStream extends CompileStream {

    public ArrayCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(String[] wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);

        if(Utils.matches(wenyan[0],WenYanLib.LENGTH())){
            Utils.inputWenyan(compiler,0);

            return new CompileResult(
                    LanguageUtils.defineVar(language,stream.getAnsName(),Utils.getValue(wenyan[0].substring(wenyan[0].indexOf("夫")+1),stream))
            );
        }
        if(Utils.matches(wenyan[0], WenYanLib.ADD())){

            String name = Utils.getValue(Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[0]),stream);
            StringBuilder result = new StringBuilder();
            Utils.inputWenyan(compiler,0);
            Utils.inputWenyan(compiler,1);
            int i = 1;
            while (Utils.matches(wenyan[i],WenYanLib.VAL())){
                Utils.inputWenyan(compiler,i);

                result.append(LanguageUtils.addArray(
                        language,
                        name,
                        Utils.getValue(wenyan[i].substring(wenyan[i].indexOf(wenyan[i].charAt(0)) + 1), stream)
                        )
                ).append("\n");
                i++;
            }
            return new CompileResult(result.toString());
        }
        if(Utils.matches(wenyan[0],WenYanLib.GET())){

            Utils.inputWenyan(compiler,0);
            String get = wenyan[0].substring(wenyan[0].indexOf("夫")+1);

            return new CompileResult(
                    LanguageUtils.defineVar(language,stream.getAnsName(),stream.getArray(get,stream))
            );
        }
        return new CompileResult(false,wenyan);
    }


}
