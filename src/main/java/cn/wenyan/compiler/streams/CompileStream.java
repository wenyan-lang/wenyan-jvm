package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;

public abstract class CompileStream {

    protected WenYanCompilerImpl compiler;

    CompileStream(WenYanCompilerImpl compiler){
        this.compiler = compiler;
        this.compiler.getStreamMap().put(this.getClass(),this);
    }
    public abstract CompileResult compile(String[] wenyan);
}
