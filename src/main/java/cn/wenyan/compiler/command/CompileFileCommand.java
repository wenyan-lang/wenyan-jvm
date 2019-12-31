package cn.wenyan.compiler.command;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CompileFileCommand extends Command {

    public CompileFileCommand() {
        super("-c",2);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        String name = args[0];
        if(name.startsWith("@")){
            name = name.substring(name.indexOf("@")+1);
            try {
                compilerConfig.setCompileFiles(FileUtils.readLines(new File(name), System.getProperty("file.codings")).toArray(new String[0]));
            }catch (IOException e){
                System.err.println("-----> No Such File");
            }
        }else{
            compilerConfig.setCompileFiles(name);
        }
        compilerConfig.setOutFile(args[1]);
        return null;
    }
}
