package cn.wenyan.compiler.test;

import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanTools;
import cn.wenyan.compiler.script.libs.Language;

import java.io.File;

public class PackStdLib {
    //施「JSON.stringify」於「太白餛飩」。書之。
    //施「包渾沌」於「太白餛飩」。書之。
    public static void main(String[] args) {
        String project = "/Users/luchangcun/Projects/new/wenyan-jvm/";
        WenYanCompilerImpl wenYanCompiler = (WenYanCompilerImpl) WenYanTools.makeCompiler(Language.GROOVY);
        String source = project+"src/main/java/";
        wenYanCompiler.getLibrary().getLibs().forEach((x,y)-> {
            wenYanCompiler
                    .compile(
                            "-c", source + y.replace(".", File.separator) + ".wy",

                            project + "/target/classes",

                            "-sc", source,"-g"
                    );
        });
    }
}
