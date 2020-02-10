package cn.wenyan.compiler.command;

public class StrongTypeCommand extends Command {

    public StrongTypeCommand() {
        super("-st",0);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setStrongType(true);
        return null;
    }

    @Override
    public String help() {
        return "开启强类型编译模式";
    }
}
