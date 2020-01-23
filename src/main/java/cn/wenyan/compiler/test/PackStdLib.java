package cn.wenyan.compiler.test;

import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanTools;
import cn.wenyan.compiler.script.libs.Language;

import java.io.File;

public class PackStdLib {

    public static void main(String[] args) {
        String project = "/Users/luchangcun/Projects/new/jvm/";
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
