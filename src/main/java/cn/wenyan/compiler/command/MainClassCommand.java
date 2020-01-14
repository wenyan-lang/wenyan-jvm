package cn.wenyan.compiler.command;

public class MainClassCommand extends Command {

    public MainClassCommand() {
        super("-m", 1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setMainClass(args[0]);
        return null;
    }
}
