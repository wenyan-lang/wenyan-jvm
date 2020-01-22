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
        compiler.getRuntime().getShell().run(true,"吾有一術。名之曰「埃氏篩」。欲行是術。必先得一數。曰「甲」。乃行是術曰。\n" +
                "\t吾有一列。名之曰「掩」。為是「甲」遍。充「掩」以陽也。\n" +
                "\t除「甲」以二。名之曰「甲半」。\n" +
                "\n" +
                "\t有數二。名之曰「戊」。恆為是。若「戊」等於「甲半」者乃止也。\n" +
                "\t\t有數二。名之曰「戌」。恆為是。若「戌」等於「甲半」者乃止也。\n" +
                "\n" +
                "\t\t\t乘「戊」以「戌」。名之曰「合」\n" +
                "\t\t\t若「合」不大於「甲」者。\n" +
                "\t\t\t\t昔之「掩」之「合」者。今陰是矣。\n" +
                "\t\t\t若非乃止也。\n" +
                "\t\t加一以「戌」。昔之「戌」者。今其是矣云云。\n" +
                "\t加一以「戊」。昔之「戊」者。今其是矣云云。\n" +
                "\n" +
                "\t吾有一列。名之曰「諸素」。\n" +
                "\t昔之「戊」者。今二是矣。恆為是。若「戊」等於「掩」之長者乃止也。\n" +
                "\t\t夫「掩」之「戊」。名之曰「素耶」。\n" +
                "\t\t若「素耶」者充「諸素」以「戊」也。\n" +
                "\t加一以「戊」。昔之「戊」者。今其是矣云云。\n" +
                "\t乃得「諸素」。\n" +
                "是謂「埃氏篩」之術也。\n" +
                "\n" +
                "施「埃氏篩」於一百。書之。");
        long time = ScalaUtils.countTime(()->{
            compiler.compile(args);
            return null;
        });
        compiler.getServerLogger().debug("Use: "+time+"ms");

    }
}
