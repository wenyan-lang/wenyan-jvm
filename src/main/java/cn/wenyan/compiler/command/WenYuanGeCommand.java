package cn.wenyan.compiler.command;

import cn.wenyan.compiler.WenYanCompiler;

public class WenYuanGeCommand extends Command{

    public WenYuanGeCommand() {
        super("-wyg",1);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setWenyuangeFile(args[0]);
        return null;
    }

    @Override
    public String help() {
        return "参数: "+WenYanCompiler.WYG_LIB+"'s Path,将文渊阁编译进去，后面跟着"+ WenYanCompiler.WYG+"的上一级目录即可，需要有-sc和-o的内容";
    }
}