package cn.wenyan.compiler;



public class Main {

    public static void main(String[] args) throws Exception{
        WenYanCompiler compiler = new WenYanCompilerImpl(false);
        compiler.runDirectly(true,
                "吾嘗觀「「群經之算經」」之書。方悟「正弦」「餘弦」之義。" +
                        "有數五十。名之曰「大衍」。" +
                        "吾有一術。名之曰「翻倍」。欲行是術。必先得一數。曰「甲」。乃行是術曰。" +
                        "   乘「甲」以二。名之曰「乙」。乃得「乙」。" +
                        "是謂「翻倍」之術也。" +
                        "施「餘弦」於「大衍」。名之曰「矣」。書之");

    }
}
