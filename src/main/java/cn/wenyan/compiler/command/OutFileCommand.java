package cn.wenyan.compiler.command;

public class OutFileCommand extends Command {

    public OutFileCommand() {
        super("-o",1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setOutFile(args[0]);
        return null;
    }

    @Override
    public String help() {
        return "参数: OutFile,单独设置编译文件输出的文件夹";
    }
}
