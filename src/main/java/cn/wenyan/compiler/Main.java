package cn.wenyan.compiler;



public class Main {

    public static void main(String[] args) throws Exception{
        WenYanCompiler compiler = new WenYanCompilerImpl(false);
        compiler.compile(args);
    }
}
