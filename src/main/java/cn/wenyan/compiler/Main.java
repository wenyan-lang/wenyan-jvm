package cn.wenyan.compiler;


import cn.wenyan.compiler.command.CommandHandler;
import cn.wenyan.compiler.script.libs.Language;


public class Main {

    public static void main(String[] args){
        if (args.length == 0){
            CommandHandler.compileCommand.entrySet().stream().forEach(x->System.out.println(x.getValue().getOption()+": "+x.getValue().getClass().getSimpleName()));
        }
        WenYanCompiler compiler = new WenYanCompilerImpl(false, Language.GROOVY);
        compiler.compile(args);
        compiler.runDirectly(false,"" +
                compiler.getTraditionalChinese("吾有一言，曰「「问天地好在」」，书之"));
    }
}
