package lang.wenyan.test

import cn.wenyan.compiler.WenYanTools
import cn.wenyan.compiler.script.libs.Language
import org.junit.Test

class Test_Compiler_Running_Successed {
    @Test
    void run(){
        def compiler = WenYanTools.makeCompiler(Language.GROOVY)
        compiler.runDirectly(true,"")
    }
}
