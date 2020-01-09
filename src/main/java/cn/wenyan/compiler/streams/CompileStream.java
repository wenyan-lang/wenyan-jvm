package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.script.libs.Language;

public abstract class CompileStream {

    protected WenYanCompilerImpl compiler;

    protected Language language;

    CompileStream(WenYanCompilerImpl compiler){
        this.compiler = compiler;
        this.language = compiler.getLanguageType();
        this.compiler.getStreamMap().put(this.getClass(),this);
    }
    public abstract CompileResult compile(String[] wenyan);

    public Language getLanguage() {
        return language;
    }
}
