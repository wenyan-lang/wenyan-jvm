package cn.wenyan.compiler;

import java.io.InputStream;
import java.io.PrintStream;

public interface Compile {

    int compile(PrintStream out, InputStream in,String... args);
}
