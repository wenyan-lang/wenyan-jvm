package cn.wenyan.compiler.command;

public class CompileLibCommand extends Command {

    public CompileLibCommand() {
        super("-l",1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        String[] libs = args[0].split(";");
        compilerConfig.setCompileLib(libs);
        return null;
    }
}
