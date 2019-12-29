package cn.wenyan.compiler;


import java.io.InputStream;
import java.io.PrintStream;

/**
 * 若施文言之术，必先用其器。古人云: 君子生非异，善假于物。
 * 此器以爪哇之法，行虚拟机之道，亦可广传文言于天下者。
 * 君用[run]之洋文可走之。吾欲将其译为java者也。
 */
public class WenYanCompiler implements Compile{


    @Override
    public int compile(PrintStream out, InputStream in, String... args) {
        return 0;
    }

    private VariableCompileStream variableCompilerStream;

    // 据之以得上下文，而书之。
    private String now;

    //此为天地之造物者，乃于此乎。
    public WenYanCompiler(){
        variableCompilerStream = new VariableCompileStream(this);
    }

    public String compile(String wenyan){
        return StreamBuilder.compile(wenyan,
                variableCompilerStream
        );
    }

    public void setNow(String now) {
        this.now = now;
    }
    
}
