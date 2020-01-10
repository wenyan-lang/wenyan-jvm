package lang.wenyan.test;

import cn.wenyan.compiler.WenYanCompiler;
import cn.wenyan.compiler.WenYanTools;
import cn.wenyan.compiler.script.libs.Language;
import org.junit.Test;

public class TestImport {

    @Test
    public void test(){
        WenYanCompiler wenYanCompiler = WenYanTools.makeCompiler(Language.GROOVY);
        wenYanCompiler.runDirectly(true,"" +
                "吾當觀「「算經」」之書，方悟「正弦」之義" +
                "");
    }
}
