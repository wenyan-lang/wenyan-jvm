package cn.wenyan.compiler.command;

public class RunCommand extends Command {

    public RunCommand() {
        super("-r",-1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setRun(true);
        compilerConfig.setRunArgs(args);
        return null;
    }
}
