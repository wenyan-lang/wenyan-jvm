package cn.wenyan.compiler;


import cn.wenyan.compiler.command.CommandHandler;
import cn.wenyan.compiler.script.libs.Language;
import cn.wenyan.compiler.utils.ScalaUtils;


public class Main {

    public static void main(String[] args){
        if(args.length == 1 && args[0].equals("shell")){
            WenYanShell.run();
            return;
        }

        if (args.length == 0||args[0].equals("help")){
            CommandHandler.compileCommand.entrySet().stream().forEach(x->System.out.println(x.getValue().getOption()+": "+x.getValue().help()));
        }
        WenYanCompilerImpl compiler = new WenYanCompilerImpl(false, Language.GROOVY);
        long time = ScalaUtils.countTime(()->{
            compiler.compile(args);
            return null;
        });
        compiler.getServerLogger().debug("Use: "+time+"ms");

    }
}
