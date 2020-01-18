package cn.wenyan.compiler.command;

public class RunCommand extends Command {

    public RunCommand() {
        super("-r",-1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setRun(true);
        compilerConfig.setRunArgs(args);
        return null;
    }

    @Override
    public String help() {
        return "参数: 程序参数,运行主文件，-r后面跟着args，即程序参数，这个选项必须放在最后面，比如 -o xxx -c xxx -r 1 2 3";
    }
}
