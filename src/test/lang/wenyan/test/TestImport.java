package lang.wenyan.test;

import cn.wenyan.compiler.WenYanCompiler;
import cn.wenyan.compiler.WenYanTools;
import cn.wenyan.compiler.script.libs.Language;
import org.junit.Test;

public class TestImport {

    @Test
    public void test(){
        WenYanCompiler wenYanCompiler = WenYanTools.makeCompiler(Language.GROOVY);
    }
}
