package cn.wenyan.compiler;

public abstract class CompileStream {

    WenYanCompiler compiler;

    CompileStream(WenYanCompiler compiler){
        this.compiler = compiler;
    }
    public abstract CompileResult compile(String wenyan);
}
