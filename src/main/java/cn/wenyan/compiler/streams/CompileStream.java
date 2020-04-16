package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.script.libs.Language;
import cn.wenyan.compiler.script.libs.Library;

import java.util.List;

public abstract class CompileStream {

    protected WenYanCompilerImpl compiler;

    protected Language language;

    protected Library library;

    CompileStream(WenYanCompilerImpl compiler){
        this.compiler = compiler;
        this.language = compiler.getLanguageType();
        this.compiler.getStreamMap().put(this.getClass(),this);
        this.library = compiler.getLibrary();
    }
    public abstract CompileResult compile(List<String> wenyan);

    public Language getLanguage() {
        return language;
    }
}
