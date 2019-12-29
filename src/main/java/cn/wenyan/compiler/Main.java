package cn.wenyan.compiler;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        WenYanCompiler compiler = new WenYanCompiler();
        compiler.runDirectly(false,
                "吾有一言。曰「「好。好。」」。名之曰「甲」。",
                "吾有一爻。曰陰。名之曰「丙」。",
                "吾有一爻。曰陰。書之。");
    }
}
