package cn.wenyan.compiler.test;

import cn.wenyan.compiler.Main;
public class TestMain {

    public static void main(String[] args) {
        String project = "/Users/luchangcun/Projects/new/jvm/project_example";
        String makeFile = project+"/MakeFile.txt";
        String sc = project+"/src/main/java";
        String out = project+"/target";
        Main.main(new String[]{"-c","@"+makeFile,out,"-sc",sc,"-m","main.主文件","-wyg",sc});
        Main.main(new String[]{"-o","/Users/luchangcun/Projects/new/jvm/project_example/target/","-n","main.主文件","-r"});
    }
}
