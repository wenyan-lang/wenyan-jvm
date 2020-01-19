package cn.wenyan.compiler;

import cn.wenyan.compiler.script.libs.Language;

public class WenYanTools {


    public static WenYanCompiler makeCompiler(Language language){
        return new WenYanCompilerImpl(false,language);
    }
}
