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
        WenYanCompiler wenYanCompiler = WenYanTools.makeCompiler(Language.GROOVY);
        wenYanCompiler.runDirectly(true,"" +
                "吾嘗觀「「算經」」之書。方悟「正弦」「餘弦」之義。\t" +
                "");
    }
}
