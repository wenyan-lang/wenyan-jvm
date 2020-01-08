package cn.wenyan.compiler;

public class WenYanTools {

    public static WenYanCompiler makeCompiler(){
        return new WenYanCompilerImpl(false);
    }
}
