package cn.wenyan.compiler;

import java.io.File;
import java.io.IOException;

public class WenYanRuntime {

    private WenYanCompilerImpl compiler;

    private WenYanShell shell;

    public WenYanRuntime(WenYanCompilerImpl compiler){
        this(compiler,new WenYanShell(compiler));
    }

    public WenYanRuntime(WenYanCompilerImpl compiler,WenYanShell shell){
        this.compiler = compiler;
        this.shell = shell;
    }

    public void runFile(String file){
        try {
            runFile(new File(file));
        }catch (IOException e){
            compiler.getServerLogger().info("",e);
        }
    }


    public void runFile(File file) throws IOException {
        shell.run(false,compiler.getGroovyCodeByFile(file));
    }
}
