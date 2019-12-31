package cn.wenyan.compiler;


public class Main {

    public static void main(String[] args) {
        WenYanCompiler compiler = new WenYanCompiler(false);
        compiler.compile(args);
        compiler.runDirectly(true,
                "吾有一數。曰三。名之曰「甲」。" +
                        "有數五十。名之曰「大衍」。"+
                        "昔之「甲」者。今「大衍」是也。" +
                        "吾有一數。曰「甲」。書之。" +
                        "注曰。「「文言備矣」」。");
    }
}
