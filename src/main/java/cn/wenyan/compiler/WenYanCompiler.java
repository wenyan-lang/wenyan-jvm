package cn.wenyan.compiler;

import java.io.File;
import java.io.IOException;

public interface WenYanCompiler extends Compile,RunCode{

    Class<?> compileToClass(String className,String... wenyanString);

    void runFile(String file);

    void runFile(File file) throws IOException;

    String compile(String wenyan);
}
