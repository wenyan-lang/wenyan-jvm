package cn.wenyan.compiler.test;

import cn.wenyan.compiler.WenYanCompiler;
import cn.wenyan.compiler.WenYanTools;
import cn.wenyan.compiler.script.libs.Language;

public class TestMain {

    public static void main(String[] args) {
        String project = "/Users/luchangcun/Projects/michel/wenyan-lang_jvm/project_example";
        String makeFile = project+"/MakeFile.txt";
        String out = project+"/target";
        WenYanCompiler compiler = WenYanTools.makeCompiler(Language.GROOVY);
        compiler.compile(
                "-c","@"+makeFile,out,"-sc",project+"/src/"
        );
    }
}
