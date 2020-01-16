package cn.wenyan.compiler;


import cn.wenyan.compiler.command.CommandHandler;
import cn.wenyan.compiler.script.libs.Language;


public class Main {

    public static void main(String[] args){
        if(args.length == 1 && args[0].equals("shell")){
            WenYanShell.run();
            return;
        }
        if (args.length == 0){
            CommandHandler.compileCommand.entrySet().stream().forEach(x->System.out.println(x.getValue().getOption()+": "+x.getValue().getClass().getSimpleName()));
        }
        long start = System.currentTimeMillis();
        WenYanCompilerImpl compiler = new WenYanCompilerImpl(false, Language.GROOVY);

        compiler.compile(args);
        System.out.println(compiler.dispatch("吾有一物。名之曰「甲」。其物如是。物之「「乙」」者。數曰三。物之「「丙」」者。言曰「「丁」」。是謂「甲」之物也。"));
        long end = System.currentTimeMillis();

        compiler.getServerLogger().debug("Use: "+(end-start)+"ms");

    }
}
