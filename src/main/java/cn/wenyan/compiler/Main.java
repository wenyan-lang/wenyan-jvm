package cn.wenyan.compiler;


import cn.wenyan.compiler.command.CommandHandler;
import cn.wenyan.compiler.script.libs.Language;
import cn.wenyan.compiler.utils.ScalaUtils;
import groovy.lang.GroovyClassLoader;

import java.io.File;

/**
 * 万事开头必有难，指令重器蔑则空。
 * 以此以动编译者，指令皆可为君用。
 * 亦可帮助亦可行，亦可脚本亦可成。
 * 程式终创一场空，来也来去皆也净。
 *
 * 诚心默念爪哇好，不忘恩赐来世重。
 *
 */

public class Main {

    public static void main(String[] args){
        if(args.length == 1 && args[0].equals("shell")){
            WenYanShell.run();
            return;
        }
        if(args.length == 2 && args[0].equals("run")) {
            try {
                Class<?> runner = new GroovyClassLoader(Main.class.getClassLoader()).parseClass(new File(args[1]));
                runner.getDeclaredMethod("main",String[].class).invoke(null,(Object)args);
            }catch (Exception e){
                e.printStackTrace();
            }
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
