package cn.wenyan.compiler;

import cn.wenyan.compiler.script.libs.Language;

public class WenYanTools {

    private static WenYanCompiler compiler;

    public static WenYanCompiler makeCompiler(Language language){
        if(compiler == null)compiler = new WenYanCompilerImpl(false,language);
        return compiler;
    }
}
