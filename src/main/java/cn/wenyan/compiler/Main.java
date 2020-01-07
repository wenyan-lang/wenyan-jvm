package cn.wenyan.compiler;


import cn.wenyan.compiler.command.CommandHandler;



public class Main {

    public static void main(String[] args) throws Exception{
        if (args.length == 0){
            CommandHandler.compileCommand.entrySet().stream().forEach(x->System.out.println(x.getValue().getOption()+": "+x.getValue().getClass().getSimpleName()));
        }
        WenYanCompiler compiler = new WenYanCompilerImpl(false);
        compiler.compile(args);
        String code = ("" +
                "吾有一術。名之曰「圖靈機」。欲行是術。必先得一列。曰「諸律」。二言。曰「始態」。曰「終態」。\n" +
                "乃行是術曰。\n" +
                "\t吾有一列。名之曰「帶」。充「帶」以「「白」」。\n" +
                "\t有數一。名之曰「針」。\n" +
                "\n" +
                "\t吾有二數。曰一。曰一。名之曰「左疆」。曰「右疆」。\n" +
                "\t吾有一言。曰「始態」。名之曰「態」。\n" +
                "\n" +
                "\t吾有一術。名之曰「圖靈機畫法」。是術曰。\n" +
                "\t\t吾有一言。名之曰「畫帶」。\n" +
                "\t\t有數「左疆」。名之曰「筆」。恆為是。若「筆」大於「右疆」者乃止也。\n" +
                "\t\t\t夫「帶」之「筆」。名之曰「符」。\n" +
                "\t\t\t若「針」等於「筆」者。\n" +
                "\t\t\t\t加「符」於「「〔」」。加其以「「〕」」昔之「符」者。今其是矣\n" +
                "\t\t\t若非。\n" +
                "\t\t\t\t加「符」於「「、」」。加其以「「、」」昔之「符」者。今其是矣\n" +
                "\t\t\t云云。\n" +
                "\t\t\t加「畫帶」以「符」。昔之「畫帶」者。今其是矣。\n" +
                "\n" +
                "\t\t加一以「筆」。昔之「筆」者。今其是矣云云。\n" +
                "\t\t吾有四言。曰「態」。曰「「《」」。曰「畫帶」。曰「「》」」書之。\n" +
                "\t是謂「圖靈機畫法」之術也。\n" +
                "\n" +
                "\t施「圖靈機畫法」。噫。\n" +
                "\n" +
                "\t恆為是。\n" +
                "\t\t凡「諸律」中之「律」\n" +
                "\t\t\t夫「律」之一。名之曰「甲態」。\n" +
                "\t\t\t夫「律」之二。名之曰「讀符」。\n" +
                "\t\t\t夫「律」之三。名之曰「乙態」。\n" +
                "\t\t\t夫「律」之四。名之曰「畫符」。\n" +
                "\t\t\t夫「律」之五。名之曰「移」。\n" +
                "\n" +
                "\t\t\t若「態」等於「甲態」者。若「帶」之「針」等於「讀符」者。\n" +
                "\t\t\t\t昔之「帶」之「針」者。今「畫符」是矣。\n" +
                "\t\t\t\t昔之「態」者。今「乙態」是矣。\n" +
                "\t\t\t\t若「移」等於「「左」」者。減一於「針」。昔之「針」者。今其是矣。云云。\n" +
                "\t\t\t\t若「移」等於「「右」」者。加一於「針」。昔之「針」者。今其是矣。云云。\n" +
                "\t\t\t\t乃止。\n" +
                "\t\t\t云云云云\n" +
                "\t\t云云。\n" +
                "\n" +
                "\t\t若「針」小於「左疆」者。\n" +
                "\t\t\t昔之「帶」之「針」者。今「「白」」是矣。\n" +
                "\t\t\t昔之「左疆」者。今「針」是矣。\n" +
                "\t\t云云\n" +
                "\t\t若「針」大於「右疆」者。\n" +
                "\t\t\t昔之「帶」之「針」者。今「「白」」是矣。\n" +
                "\t\t\t昔之「右疆」者。今「針」是矣。\n" +
                "\t\t云云\n" +
                "\n" +
                "\t\t施「圖靈機畫法」。噫。\n" +
                "\t\t若「態」等於「終態」者乃止也。\n" +
                "\t云云。\n" +
                "是謂「圖靈機」之術也。\n" +
                "\n" +
                "\n" +
                "吾有一術。名之曰「製律」。欲行是術。必先得一列。曰「諸律」。\n" +
                "\t五言。曰「甲態」。曰「讀符」。曰「乙態」。曰「畫符」。曰「移」。乃行是術曰。\n" +
                "\t吾有一列。名之曰「律」。\n" +
                "\t充「律」以「甲態」。以「讀符」。以「乙態」。以「畫符」。以「移」。\n" +
                "\t充「諸律」以「律」。\n" +
                "是謂「製律」之術也。\n" +
                "\n" +
                "吾有一言。曰「「營營河狸。止于樊。」」。書之。\n" +
                "\n" +
                "吾有一列。名之曰「諸律」。\n" +
                "吾有一言。曰「「墨」」。名之曰「陽符」。\n" +
                "吾有一言。曰「「白」」。名之曰「陰符」。\n" +
                "施「製律」於「諸律」於「「甲」」於「陰符」於「「乙」」於「陽符」於「「右」」\n" +
                "施「製律」於「諸律」於「「甲」」於「陽符」於「「丙」」於「陽符」於「「左」」\n" +
                "施「製律」於「諸律」於「「乙」」於「陰符」於「「甲」」於「陽符」於「「左」」\n" +
                "施「製律」於「諸律」於「「乙」」於「陽符」於「「乙」」於「陽符」於「「右」」\n" +
                "施「製律」於「諸律」於「「丙」」於「陰符」於「「乙」」於「陽符」於「「左」」\n" +
                "施「製律」於「諸律」於「「丙」」於「陽符」於「「樊」」於「陽符」於「「中」」\n" +
                "\n" +
                "施「圖靈機」於「諸律」於「「甲」」於「「樊」」。").replace("\t","").replace("\n","");
        String code1 = ("吾有一術。名之曰「角谷猜想」。\n" +
                "欲行是術。必先得一數。\n" +
                "曰「甲」。\n" +
                "乃行是術曰。\n" +
                "    吾有一術。名之曰「助手」。\n" +
                "    欲行是術。必先得一數。\n" +
                "    曰「乙」。\n" +
                "    乃行是術曰。\n" +
                "        吾有一數。名之曰「埃」。\n" +
                "        除「乙」以二。所餘幾何。名之曰「積」。\n" +
                "        若「積」不等於零者。乘三以「乙」。加其於一。昔之「埃」者。今其是矣。\n" +
                "        若非。 除二於「乙」。昔之「埃」者。今其是矣。云云。\n" +
                "        乃得「埃」。\n" +
                "    是謂「助手」之術也。\n" +
                "\n" +
                "    吾有一列。名之曰「回」。充「回」以「甲」。\n" +
                "    恆為是。\n" +
                "        若「甲」等於一者。乃止。也。\n" +
                "        施「助手」於「甲」。昔之「甲」者。今其是矣。\n" +
                "        充「回」以「甲」。\n" +
                "    云云。\n" +
                "    充「回」以一。\n" +
                "    乃得「回」。\n" +
                "是謂「角谷猜想」之術也。\n" +
                "\n" +
                "施「角谷猜想」於十二。書之。\n" +
                "施「角谷猜想」於十九。書之。\n" +
                "施「角谷猜想」於二十七。書之。").replace("\t","").replace("\n","");;
        compiler.runDirectly(true,code1);
//        compiler.runDirectly(true,"" +
//                "吾有一言。名之曰「畫帶」。" +
//                "吾有一言。曰「畫帶」。名之曰「其」。" +
//                "吾有一言。曰「「畫帶」」。名之曰「其1」。" +
//                "吾有一列。名之曰「帶」。充「帶」以「「白」」以「「白」」。" +
//                "加「「畫帶」」以「畫帶」。");
    }
}
