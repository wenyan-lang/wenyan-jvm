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

    @Override
    public String help() {
        return "参数: 主类,运行时，设置运行主类,比如cn.main.HelloWorld,对应着@sourceFile/cn/main/HelloWorld.wy";
    }
}
