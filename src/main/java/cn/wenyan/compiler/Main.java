package cn.wenyan.compiler;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        WenYanCompiler compiler = new WenYanCompiler();
        compiler.compileToGroovy(new File("/Users/luchangcun/Desktop/未命名文件夹/a.groovy"),true,"吾有一數。曰三。名之曰「甲」。");
    }
}
