package cn.wenyan.compiler.command;

public class ClassFileCommand extends Command {

    public ClassFileCommand() {
        super("-n",1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setClassFile(args[0]);
        return null;
    }
}
