package cn.wenyan.compiler;


import cn.wenyan.compiler.log.ServerLogger;
import groovy.lang.GroovyShell;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * 若施文言之术，必先用其器。古人云: 君子生非异，善假于物。
 * 此器以爪哇之法，行虚拟机之道，亦可广传文言于天下者。
 * 君用[run]之洋文可走之。吾欲将其译为java者也。
 */
public class WenYanCompiler implements Compile{

    private int index = 0;

    private List<Integer> nowCompiling = new ArrayList<>();

    private VariableCompileStream variableCompilerStream;

    private GroovyCompiler groovyCompiler;

    private ServerLogger serverLogger;

    private GroovyShell shell;
    // 据之以得上下文，而书之。
    private String now;

    private String[] wenyans;

    //此为天地之造物者，乃于此乎。
    public WenYanCompiler(){
        variableCompilerStream = new VariableCompileStream(this);
        groovyCompiler = new GroovyCompiler();
        serverLogger = new ServerLogger(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile());
        shell = new GroovyShell();
    }

    //单句编译
    public String compile(String wenyan){
        index ++;
        StringBuilder builder = new StringBuilder();
        serverLogger.info("吾译之于 "+index+" 行也,其为'"+wenyan+"'者乎");
        setNow(wenyan);
        wenyans = wenyan.split("。");
        List<String> newWenyans = new ArrayList<>(Arrays.asList(wenyans));
        appendSplit(newWenyans,wenyans);
        wenyans = newWenyans.toArray(new String[0]);
        while (wenyans.length != 0) {
            builder.append("\n").append(StreamBuilder.compile(wenyan,
                    variableCompilerStream
            ));
            this.clearCompiled();
            wenyan = getWenyanFromArray(wenyans);
        }
        return builder.toString();
    }

    private String getWenyanFromArray(String[] wenyans){
        StringBuilder builder = new StringBuilder();
        for(String wenyan: wenyans){
            builder.append(wenyan).append("。");
        }
        return builder.toString();
    }

    //多句编译
    public Class<?> compileToClass(String className,String... wenyanString){
        Class<?> clz = groovyCompiler.compile(getGroovyCode(false,wenyanString),className);

        this.serverLogger.info("得类为:"+clz.getName());
        return clz;
    }

    public Object runDirectly(boolean out,String... wenyanString){
        serverLogger.info("---------------运行之--------------------");
        return shell.evaluate(getGroovyCode(out,wenyanString));
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


    public List<Integer> getNowCompiling() {
        return nowCompiling;
    }

    public void clearCompiled(){
        List<String> newWenyans = new ArrayList<>(Arrays.asList(wenyans));
        for(int index:nowCompiling){
            newWenyans.set(index,null);
        }
        Iterator<String> str = newWenyans.iterator();
        while (str.hasNext()){
            if(str.next() == null)str.remove();
        }
        wenyans = newWenyans.toArray(new String[0]);
        nowCompiling.clear();
    }

    void appendSplit(List<String> newWenyans,String[] wenyans){
        //此处要解决歧义问题，如果'。'在字符串出现如何解决
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<newWenyans.size();i++){
            int index = newWenyans.get(i).indexOf("「「");
            if(index != -1) {
                int endIndex = newWenyans.get(i).indexOf("」」", index);
                if (endIndex == -1) {
                    builder.append(newWenyans.get(i));
                    int removed = 0;
                    for (int j = i+1; j < wenyans.length; j++) {
                        builder.append("。").append(newWenyans.get(j));
                        removed++;
                        if (builder.indexOf("」」", index) != -1) {
                            for(int z = 0;z<removed;z++){
                                newWenyans.remove(i+1);
                            }
                            newWenyans.set(i,builder.toString());
                            break;
                        }
                    }
                }
                builder = new StringBuilder();
            }
        }
    }
}
