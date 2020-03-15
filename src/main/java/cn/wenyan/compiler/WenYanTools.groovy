package cn.wenyan.compiler

import cn.wenyan.compiler.script.libs.Language
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

@CompileStatic
class WenYanTools {

    static {
        setDefault()
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private static void setDefault(){
        WenYanTools.metaClass.static.default = {
            getDefault()
        }
    }

    static WenYanCompiler makeCompiler(Language language){
        return new WenYanCompilerImpl(false,language)
    }

    static WenYanCompiler getDefault(){
        return makeCompiler(Language.GROOVY)
    }
}
