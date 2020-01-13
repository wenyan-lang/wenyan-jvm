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
}
