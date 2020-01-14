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
        WenYanCompiler compiler = new WenYanCompilerImpl(false, Language.GROOVY);

        compiler.compile(args);
        System.out.println(compiler.dispatch("吾有一術。名之曰「曼德博」。欲行是術。必先得二數。曰「寬」。曰「高」。乃行是術曰。\n" +
                "\t批曰。「「曼德博集。亦稱曼德布洛特复数集合。复平面上组成分形之点之集合也。」」\n" +
                "\n" +
                "\t吾有一言。曰「「丁龘蠹臺龜畫龍淼蔑高五三二」」。名之曰「皴法」。\n" +
                "\t批曰。「「皴法者。圖畫之法也」」。\n" +
                "\n" +
                "\t減零以一。名之曰「上」。加零以一。名之曰「下」。\n" +
                "\t減零以二。名之曰「左」。加零以二。名之曰「右」。\n" +
                "\n" +
                "\t減「右」以「左」。除其以「寬」。名之曰「橫步」。\n" +
                "\t減「下」以「上」。除其以「高」。名之曰「縱步」。\n" +
                "\n" +
                "\t有數零。名之曰「戊」。恆為是。若「戊」等於「高」者乃止也。\n" +
                "\n" +
                "\t\t乘「縱步」以「戊」。加其以「上」。以名之曰「虛」。\n" +
                "\n" +
                "\t\t吾有一言。名之曰「行」。\n" +
                "\n" +
                "\t\t有數零。名之曰「戌」。恆為是。若「戌」等於「寬」者乃止也。\n" +
                "\n" +
                "\t\t\t乘「橫步」以「戌」。加其以「左」。名之曰「實」。\n" +
                "\n" +
                "\t\t\t有數「虛」。名之曰「虛虛」。\n" +
                "\t\t\t有數「實」。名之曰「實實」。\n" +
                "\n" +
                "\t\t\t批曰。「「凡每一像素。皆算令其收斂之最大疊代數」」。\n" +
                "\n" +
                "\t\t\t有數零。名之曰「己」。恆為是。若「己」等於十二者乃止也。\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t乘「實實」以「實實」。乘「虛虛」以「虛虛」。名之曰「甲」。曰「乙」。\n" +
                "\t\t\t\t加「甲」以「乙」。名之曰「丙」。\n" +
                "\t\t\t\t若「丙」大於四者乃止也。\n" +
                "\n" +
                "\t\t\t\t乘「虛虛」以「實實」。乘其以二。加其以「虛」。昔之「虛虛」者。今其是矣。\n" +
                "\t\t\t\t減「甲」以「乙」。加其以「實」。昔之「實實」者。今其是矣。\n" +
                "\n" +
                "\t\t\t加一以「己」。昔之「己」者。今其是矣云云。\n" +
                "\n" +
                "\t\t\t批曰。「「既得疊代數。乃以皴法圖之」」。\n" +
                "\n" +
                "\t\t\t減十三以「己」。名之曰「巳」。\n" +
                "\t\t\t夫「皴法」之「巳」。名之曰「墨」。\n" +
                "\t\t\t加「行」以「墨」。昔之「行」者。今其是矣。\n" +
                "\n" +
                "\t\t加一以「戌」。昔之「戌」者。今其是矣云云。\n" +
                "\t\t\n" +
                "\t\t吾有一言。曰「行」。書之。\n" +
                "\t加一以「戊」。昔之「戊」者。今其是矣云云。\n" +
                "是謂「曼德博」之術也。\n" +
                "\n" +
                "批曰。「「畫曼德博集合之法。至是盡矣。乃一試之」」。\n" +
                "\n" +
                "施「曼德博」於五十九。於二十四。"));
    }
}
