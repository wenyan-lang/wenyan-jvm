package cn.wenyan.compiler.command;

public class PinYinCommand extends Command {

    public PinYinCommand() {
        super("-p",1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setSupportPinYin(Boolean.parseBoolean(args[0]));
        return null;
    }

    @Override
    public String help() {
        return "该指令已经废弃，使用无效";
    }
}
