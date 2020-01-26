package cn.wenyan.compiler.test;

import cn.wenyan.compiler.WenYanCompiler;
import cn.wenyan.compiler.WenYanTools;
import cn.wenyan.compiler.script.libs.Language;

public class TestMain {
//
    public static void main(String[] args) {
        String project = "/Users/luchangcun/Projects/new/jvm/project_example";
        String makeFile = project+"/MakeFile.txt";
        String sc = project+"/src/main/java";
        String out = project+"/target";
        WenYanCompiler impl = WenYanTools.makeCompiler(Language.GROOVY);
        impl.compile("-c","@"+makeFile,out,"-sc",sc,"-m","main.主文件","-g");
        impl.compile("-o","/Users/luchangcun/Projects/new/jvm/project_example/target/","-n","main.主文件","-r");
    }
}
