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
        Class<?> wenyanClass = compiler.compileToClass("HelloWorld","" +
                "有數七名之曰「甲」" +
                "有數五名之曰「乙」" +
                "有數零名之曰「艾」" +
                "恆為是若「艾」大於「甲」者乃止也" +
                "   若「艾」等於「乙」者" +
                "       有言『ssr』書之" +
                "   若非" +
                "       加一以「甲」乘其以三書之" +
                "       減七百五十六以四百三十三名之曰「亥」書之" +
                "   也" +
                "   加一以「艾」昔之「艾」者今其是矣" +
                "云云");
        wenyanClass.getDeclaredMethod("run").invoke(wenyanClass.newInstance());

    }
}
