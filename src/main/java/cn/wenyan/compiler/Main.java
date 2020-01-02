package cn.wenyan.compiler;


public class Main {

    public static void main(String[] args) {
        WenYanCompiler compiler = new WenYanCompilerImpl(false);
        compiler.compile(args);
        compiler.runDirectly(false,
                "吾有一言，曰「「一百一。『1。111』」」，書之。");
    }
}
