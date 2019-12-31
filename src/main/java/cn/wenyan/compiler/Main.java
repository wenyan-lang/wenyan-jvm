package cn.wenyan.compiler;

public class Main {

    public static void main(String[] args) {
        WenYanCompiler compiler = new WenYanCompiler(false);
        compiler.runDirectly(true,
                "吾有一言。曰「「好。好。」」。書之。" +
                        "吾有一言。曰「「好。好。」」。書之。" +
                        "吾有一爻。曰陰。名之曰「丙」。" +
                        "吾有一爻。曰「丙」。書之。");
    }
}
