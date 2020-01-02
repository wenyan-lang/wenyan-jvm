package cn.wenyan.compiler;


public class Main {

    public static void main(String[] args) {
        WenYanCompiler compiler = new WenYanCompilerImpl(false);
        compiler.runDirectly(false,
                "" +
                        "吾有一數。曰七。名之曰「甲」。" +
                        "為是「甲」遍。" +
                        "   為是「甲」遍。"+
                        "       吾有一言。曰「「問天地好在。」」。書之。" +
                        "   云云。" +
                        "云云。");
    }
}
