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
}
