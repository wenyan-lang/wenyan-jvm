package cn.wenyan.compiler.command;

public class SourceCommand extends Command{


    public SourceCommand() {
        super("-sc",1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setSourcePath(args[0]);
        return null;
    }

    @Override
    public String help() {
        return "参数: sourcePath,指定源文件的根目录，根目录下面的文件夹则作为package名称";
    }
}
