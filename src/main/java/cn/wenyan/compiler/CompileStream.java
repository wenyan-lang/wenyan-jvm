package cn.wenyan.compiler;

public abstract class CompileStream {

    protected WenYanCompilerImpl compiler;

    CompileStream(WenYanCompilerImpl compiler){
        this.compiler = compiler;
    }
    public abstract CompileResult compile(String[] wenyan);
}
