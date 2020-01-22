package cn.wenyan.compiler.command;


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
        return "選擇生成groovy文件";
    }
}
