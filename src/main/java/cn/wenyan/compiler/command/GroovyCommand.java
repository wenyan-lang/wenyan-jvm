package cn.wenyan.compiler.command;

@Deprecated
public class GroovyCommand extends Command {

    public GroovyCommand() {
        super("-g",0);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setGroovy(false);
        return null;
    }

    @Override
    public String help() {
        return "该指令废弃，不要使用";
    }
}
