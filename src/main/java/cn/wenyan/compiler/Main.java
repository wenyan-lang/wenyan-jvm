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
        WenYanCompiler compiler = new WenYanCompilerImpl(false, Language.GROOVY);

        compiler.compile(args);

        System.out.println(compiler.dispatch("夫「甲」。夫「乙」。夫「丙」。取二以施「丁」。取二以施「戊」。取一以施「己」。夫「庚」。夫「辛」。取三以施「壬」。名之曰「癸」。"));
        long end = System.currentTimeMillis();

        System.out.println("Use: "+(end-start)+"ms");

    }
}
