package cn.wenyan.compiler;


public interface WenYanCompiler extends Compile{

    String ERROR = "error";

    Class<?> compileToClass(String className,String... wenyanString);

    Class<?> compileToClass(String... wenyans);

    String dispatch(String wenyan);
}
