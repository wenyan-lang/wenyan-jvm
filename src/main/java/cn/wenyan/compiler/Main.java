package cn.wenyan.compiler;


public class Main {

    public static void main(String[] args) throws Exception{
        WenYanCompiler compiler = new WenYanCompilerImpl(false);
        compiler.runDirectly(true,
               compiler.getTraditionalChinese("吾有一数。曰一。书之"));

    }
}
