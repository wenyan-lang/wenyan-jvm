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

    @Override
    public String help() {
        return "参数: 主类,编译时，设置编译主类,比如cn.main.HelloWorld,对应着@sourceFile/cn/main/HelloWorld.wy";
    }
}
