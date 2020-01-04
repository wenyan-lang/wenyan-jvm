package cn.wenyan.compiler;



public class Main {

    public static void main(String[] args) throws Exception{
        WenYanCompiler compiler = new WenYanCompilerImpl(false);
//        compiler.runDirectly(true,
//                "" +
//                        "有數七，名之曰「甲」。" +
//                        "有數五，名之曰「乙」。" +
//                        "有數零，名之曰「艾」。" +
//                        "恆為是，若「艾」大於「甲」者乃止也。" +
//                        "   若「艾」等於「乙」者。" +
//                        "       有言『ssr』，書之。" +
//                        "   若非。" +
//                        "       加一以「甲」，乘其以三，書之。" +
//                        "   也。" +
//                        "   加一以「艾」，昔之「艾」者，今其是矣。" +
//                        "云云。");
        compiler.runDirectly(true,"" +
                "有數零，名之曰「艾」。" +
                "恆為是，若「艾」大於一二三四五六一二三者乃止也。" +
                "   吾有一言，曰「「YR退坑是矣」」，書之。"+
                "   加一以「艾」，昔之「艾」者，今其是矣。" +
                "云云。");

    }
}
