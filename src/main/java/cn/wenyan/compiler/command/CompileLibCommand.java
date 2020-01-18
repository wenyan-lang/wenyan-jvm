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

    @Override
    public String help() {
        return "参数: jarFiles,加入依赖的jar包，如hello.jar;eat.jar";
    }
}
