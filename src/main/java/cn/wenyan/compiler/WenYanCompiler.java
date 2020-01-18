package cn.wenyan.compiler;


public interface WenYanCompiler extends Compile{

    String ERROR = "error";

    String WYG = "藏書樓";

    String WYG_LIB = "序.wy";

    Class<?> compileToClass(String className,String... wenyanString);

    Class<?> compileToClass(String... wenyans);

    String dispatch(String wenyan);

    WenYanRuntime getRuntime();
}
