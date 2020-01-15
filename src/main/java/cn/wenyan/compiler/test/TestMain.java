package cn.wenyan.compiler.test;

import cn.wenyan.compiler.Main;
public class TestMain {

    public static void main(String[] args) {
        String project = "/Users/luchangcun/Projects/michel/wenyan-lang_jvm/project_example";
        String makeFile = project+"/MakeFile.txt";
        String out = project+"/target";
        Main.main(new String[]{"-c","@"+makeFile,out,"-sc",project+"/src/main/java","-m","main.主文件"});
    }
}
