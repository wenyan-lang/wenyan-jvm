package cn.wenyan.compiler.command;

public class WenYuanGeDownloadCommand extends Command {

    public WenYuanGeDownloadCommand() {
        super("-wd",1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setWygDownload(args[0]);
        return null;
    }

    @Override
    public String help() {
        return "参数: name,相当于wyg i name,确保有node.js";
    }
}
