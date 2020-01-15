package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.script.libs.Language;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.utils.Utils;

import java.util.List;

public class TryCompileStream extends CompileStream{

    private String nowException;

    private int index = 0;

    public TryCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(List<String> wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan, WenYanLib.TRY())){
            compiler.removeWenyan();
            return new CompileResult(language.getSyntax(Syntax.TRY));
        }
        if(Utils.matches(wenyan,WenYanLib.EXCEPTION_DEFINE())){
            compiler.removeWenyan();
            if(Utils.matches(wenyan,WenYanLib.EXCEPTION_THROW())){
                String value = compiler.removeWenyan();
                String name = Utils.getValue(Utils.getString(WenYanLib.STRING(),value),stream);
                return new CompileResult(LanguageUtils.throwEx(language,stream.getAnsName(),name));
            }
        }
        if(Utils.matches(wenyan,WenYanLib.CATCH())){
            compiler.removeWenyan();
            String name = stream.getAnsName();
            nowException = name;
            return new CompileResult(LanguageUtils.catchEx(language,name));
        }
        if(Utils.matches(wenyan,WenYanLib.EXCEPTION_IF())){
            String name = compiler.removeWenyan();
            name = Utils.getValue(Utils.getString(WenYanLib.STRING(),name),stream);
            if(index == 0){
                index++;
                return new CompileResult(LanguageUtils.defineIf(language, LanguageUtils.ifEquals(language,nowException,name)));
            }else{
                index++;
                return new CompileResult(language.getSyntax(Syntax.ELSE_IF)+LanguageUtils.defineIf(language, LanguageUtils.ifEquals(language,nowException,name)));
            }
        }
        if(Utils.matches(wenyan,WenYanLib.EXCEPTION_ELSE())){
            //不知何禍歟。
            compiler.removeWenyan();
            return new CompileResult(language.getSyntax(Syntax.ELSE)+"\n"+LanguageUtils.defineVar(language,stream.getAnsName(),nowException));
        }
        if(Utils.matches(wenyan,WenYanLib.CATCH_END())){
            compiler.removeWenyan();
            index = 0;
            return new CompileResult(language.getSyntax(Syntax.CATCH_END));
        }

        return new CompileResult(false,wenyan);
    }
}
