package cn.wenyan.compiler;


import cn.wenyan.compiler.log.ServerLogger;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;


/**
 * 若施文言之术，必先用其器。古人云: 君子生非异，善假于物。
 * 此器以爪哇之法，行虚拟机之道，亦可广传文言于天下者。
 * 君用[run]之洋文可走之。吾欲将其译为java者也。
 */
public class WenYanCompiler implements Compile{

    private int index = 0;


    private VariableCompileStream variableCompilerStream;

    private GroovyCompiler groovyCompiler;

    private ServerLogger serverLogger;
    // 据之以得上下文，而书之。
    private String now;

    //此为天地之造物者，乃于此乎。
    public WenYanCompiler(){
        variableCompilerStream = new VariableCompileStream(this);
        groovyCompiler = new GroovyCompiler();
        serverLogger = new ServerLogger(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile());
    }

    //单句编译
    public String compile(String wenyan){
        index ++;
        serverLogger.info("吾译之于 "+index+" 行也,其为'"+wenyan+"'者乎");
        return StreamBuilder.compile(wenyan,
                variableCompilerStream
        );
    }

    //多句编译
    public Class<?> compileToClass(String className,String... wenyanString){
        Class<?> clz = groovyCompiler.compile(getGroovyCode(false,wenyanString),className);

        this.serverLogger.info("得类为:"+clz.getName());
        return clz;
    }

    public int compileToGroovy(File file,boolean outInConsole,String... wenyanString){
        try {
            String code = getGroovyCode(outInConsole,wenyanString);
            FileUtils.write(file,code,System.getProperty("file.coding"));
            serverLogger.info("得文件为: "+file);
            return 0;
        }catch (Exception e){
            serverLogger.error("Syntax Error",e);
            return 1;
        }
    }

    public String getGroovyCode(boolean outInConsole,String... wenyanString){
        StringBuilder groovyCode = new StringBuilder();
        for(String code:wenyanString){
            String compile = compile(code);
            if(outInConsole){
                serverLogger.info(code+" => "+ compile);
            }
            groovyCode.append(compile).append("\n");
        }
        this.serverLogger.info("此事成也，得之");
        return groovyCode.toString();
    }

    public void setNow(String now) {
        this.now = now;
    }

    @Override
    public int compile(PrintStream out, InputStream in, String... args) {
        return 0;
    }


}
