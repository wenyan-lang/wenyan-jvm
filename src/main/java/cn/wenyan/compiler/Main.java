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
                "吾有一術。名之曰「六脈神劍」。欲行是術。必先得六數。曰「甲」。曰「乙」。曰「丙」。曰「丁」。曰「戊」。曰「己」乃行是術曰。是謂「六脈神劍」之術也。" +
                "");
    }
}
