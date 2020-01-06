package cn.wenyan.compiler;



public class Main {

    public static void main(String[] args) throws Exception{
        WenYanCompiler compiler = new WenYanCompilerImpl(false);
        compiler.compile(args);
        compiler.runDirectly(false,("" +
                "吾有一數。曰四十二。名之曰「運數」。今有一術。名之曰「運」。欲行是術。必先得一數。曰「甲」。乃行是術曰。\n" +
                "\t注曰「「運者。隨機種子也」」。\n" +
                "\t昔之「運數」者。今「甲」是矣。\n" +
                "是謂「運」之術也。\n" +
                "\n" +
                "今有一術。名之曰「占」。是術曰。\n" +
                "\t注曰「「線性同餘方法所得隨機數也」」。\n" +
                "\t有數四十二億九千四百九十六萬七千二百九十六。名之曰「模」。\n" +
                "\t注曰「「有數二千二百六十九萬五千四百七十七。名之曰「倍」。」」。\n" +
                "\t有數二千二百六十七萬五千四百五十六。名之曰「上倍」。有數二萬零二十一。名之曰「下倍」。\n" +
                "\t有數一。名之曰「增」。\n" +
                "\t乘「上倍」於「運數」。除其以「模」。所餘幾何。名之曰「上餘」。\n" +
                "\t乘「下倍」於「運數」。加其於「上餘」。加其以「增」。除其以「模」。所餘幾何。昔之「運數」者。今其是矣。\n" +
                "\t除「運數」以「模」。名之曰「卦」。\n" +
                "\t乃得「卦」。\n" +
                "是謂「占」之術也。").replace("\t","").replace("\n",""));
    }
}
