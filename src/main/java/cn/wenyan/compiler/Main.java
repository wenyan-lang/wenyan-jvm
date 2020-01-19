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
        compiler.getRuntime().getShell().run(true,"吾有一術。名之曰「賈憲三角」。欲行是術。必先得一數。曰「層數」。乃行是術曰。\n" +
                "    吾有一列。名之曰「前層之得」。\n" +
                "    充「前層之得」以一。夫「前層之得」。書之。\n" +
                "    若「層數」等於一者乃歸空無也。\n" +
                "\n" +
                "    充「前層之得」以一。夫「前層之得」。書之。\n" +
                "    若「層數」等於二者乃歸空無也。\n" +
                "\n" +
                "    有數三。名之曰「計甲」。\n" +
                "    恆為是。若「計甲」大於「層數」者乃止也。\n" +
                "        吾有一列。名之曰「此層之得」。\n" +
                "        充「此層之得」以一。\n" +
                "        有數一。名之曰「計乙」。\n" +
                "        夫「前層之得」之長。名之曰「層長」。\n" +
                "        恆為是。若「計乙」不小於「層長」者乃止也\n" +
                "            加一以「計乙」。名之曰「計乙又一」\n" +
                "            夫「前層之得」之「計乙」。名之曰「數甲」。\n" +
                "            夫「前層之得」之「計乙又一」。名之曰「數乙」。\n" +
                "            加「數甲」以「數乙」。名之曰「新數」。\n" +
                "            充「此層之得」以「新數」。\n" +
                "            加「計乙」以一。昔之「計乙」者。今其是矣。\n" +
                "        云云。\n" +
                "        充「此層之得」以一。夫「此層之得」。書之。\n" +
                "        昔之「前層之得」者。今「此層之得」是矣。\n" +
                "        加「計甲」以一。昔之「計甲」者。今其是矣。\n" +
                "    云云\n" +
                "是謂「賈憲三角」之術也。\n" +
                "\n" +
                "施「賈憲三角」於九。\n");
        long time = ScalaUtils.countTime(()->{
            compiler.compile(args);
            return null;
        });
        compiler.getServerLogger().debug("Use: "+time+"ms");

    }
}
