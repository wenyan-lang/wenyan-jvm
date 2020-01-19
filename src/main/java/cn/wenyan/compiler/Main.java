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
        compiler.getRuntime().getShell().run(true,"吾嘗觀「「算經」」之書。方悟「絕對」「平方根」之義。\n" +
                "\n" +
                "吾有一術名之曰「畫心」。\n" +
                "欲行是術。必先得一言。曰「心語」。\n" +
                "乃行是術曰。\n" +
                "    夫「心語」之長。名之曰「長度」。\n" +
                "    吾有一言。曰「「一」」。名之曰「填充符」。\n" +
                "    吾有一言。曰「「\\n」」。名之曰「换行符」。\n" +
                "    除十三以十。名之曰「乙」。\n" +
                "    除負十一以十。名之曰「乙止」。\n" +
                "    除四十以一千。名之曰「甲步長」。\n" +
                "    除六以一百。名之曰「乙步長」。\n" +
                "    吾有一數。曰一。名之曰「輸出位置」。\n" +
                "    吾有一言。曰「「」」。名之曰「果」。\n" +
                "    恆為是。若「乙」小於「乙止」者乃止也。\n" +
                "        除負十一以十。名之曰「甲」。\n" +
                "        除十一以十。名之曰「甲止」。\n" +
                "        吾有一言。曰「「」」。名之曰「本行」。\n" +
                "\t    恆為是。若「甲」大於「甲止」者乃止也。\n" +
                "            施「絕對」於「甲」。名之曰「甲絕對」。\n" +
                "            施「平方根」於「甲絕對」。名之曰「減數」。\n" +
                "            乘五於「乙」。除其以四。名之曰「被減數」。\n" +
                "            減「被減數」以「減數」。名之曰「差」。\n" +
                "            乘「差」以「差」。名之曰「加數」。\n" +
                "            乘「甲」以「甲」。加其以「加數」。減其以一。名之曰「函數值」。\n" +
                "            若「函數值」不大於零者。\n" +
                "                夫「心語」之「輸出位置」。名之曰「字」。\n" +
                "                加「本行」以「字」。昔之「本行」者今其是矣。\n" +
                "                除「輸出位置」以「長度」。所餘幾何。加其以一。昔之「輸出位置」者今其是矣。\n" +
                "            若非。\n" +
                "                加「本行」以「填充符」。昔之「本行」者今其是矣。\n" +
                "            终也。\n" +
                "            加「甲」以「甲步長」。昔之「甲」者今其是矣。\n" +
                "\t    云云。\n" +
                "        減「乙」以「乙步長」。昔之「乙」者今其是矣。\n" +
                "        加「本行」以「换行符」。昔之「本行」者今其是矣。\n" +
                "        加「果」以「本行」。昔之「果」者今其是矣。\n" +
                "    云云。\n" +
                "    吾有一言。曰「果」。書之。\n" +
                "是謂「畫心」之術也。\n" +
                "\n" +
                "施「畫心」於「「琉璃梳子撫青絲。畫心牽腸癡不癡。」」。");
        long time = ScalaUtils.countTime(()->{
            compiler.compile(args);
            return null;
        });
        compiler.getServerLogger().debug("Use: "+time+"ms");

    }
}
