package cn.wenyan.compiler;

public class Main {

    public static void main(String[] args) {
        WenYanCompiler compiler = new WenYanCompiler();
        compiler.runFile(args[0]);
    }
}
