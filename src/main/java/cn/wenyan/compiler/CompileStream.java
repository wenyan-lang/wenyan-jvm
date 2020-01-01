package cn.wenyan.compiler;

public abstract class CompileStream {

    protected WenYanCompilerImpl compiler;

    CompileStream(WenYanCompilerImpl compiler){
        this.compiler = compiler;
        this.compiler.getStreamMap().put(this.getClass(),this);
    }
    public abstract CompileResult compile(String[] wenyan);
}
