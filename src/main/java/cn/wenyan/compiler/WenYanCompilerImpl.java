package cn.wenyan.compiler;


import cn.wenyan.compiler.command.CommandHandler;
import cn.wenyan.compiler.command.CompilerConfig;
import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.factory.CompileFactory;
import cn.wenyan.compiler.factory.StreamBuilder;
import cn.wenyan.compiler.log.LogFormat;
import cn.wenyan.compiler.log.ServerLogger;
import cn.wenyan.compiler.streams.*;
import cn.wenyan.compiler.utils.JuDouUtils;
import cn.wenyan.compiler.utils.SyntaxColor;
import cn.wenyan.compiler.utils.Utils;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import groovy.lang.GroovyShell;
import org.apache.commons.io.FileUtils;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import static cn.wenyan.compiler.log.LogFormat.fg;


/**
 * 若施文言之术，必先用其器。古人云: 君子生非异，善假于物。
 * 此器以爪哇之法，行虚拟机之道，亦可广传文言于天下者。
 * 君用[run]之洋文可走之。吾欲将其译为java者也。
 */
public class WenYanCompilerImpl implements WenYanCompiler {

    private boolean supportPinyin;

    private int index = 0;

    private String now;

    private List<Integer> nowCompiling = new ArrayList<>();

    private GroovyCompiler groovyCompiler;

    private ServerLogger serverLogger;

    private GroovyShell shell;

    private CompileFactory factory;

    private String[] wenyans;

    private CommandHandler handler;

    private Map<Class<? extends CompileStream>,CompileStream> streamMap;

    //***************************************************//
    //*********************构造器*************************//
    //**************此为天地之造物者，乃于此乎。**************//
    //**************************************************//


    public WenYanCompilerImpl(boolean supportPinyin){
        this.streamMap = new HashMap<>();
        this.groovyCompiler = new GroovyCompiler();
        this.serverLogger = new ServerLogger(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile());
        this.shell = new GroovyShell();
        this.handler = new CommandHandler(this);
        this.supportPinyin = supportPinyin;
        if(File.separator.equals("\\")) AnsiConsole.systemInstall();
        this.serverLogger.info(LogFormat.textFormat(LogFormat.Control.BOLD.getAnsi()+"WenYan Lang JVM Compiler"+ fg(Ansi.Color.DEFAULT),Ansi.Color.YELLOW));
        this.factory = new StreamBuilder(this)
                .put(new VariableCompileStream(this))
                .put(new CommentCompileStream(this))
                .put(new ControlCompileStream(this))
                .put(new MathCompileStream(this))
                .put(new FunctionCompileStream(this))
                .build();
    }

    //***************************************************//
    //*********************编译主方法**********************//
    //**************************************************//

    @Override
    public int compile(String... args) {
        return handler.executeCommand(args);
    }


    //***************************************************//
    //***********************PUBLIC**********************//
    //**************************************************//

    public String dispatch(String wenyan){
        return compile(wenyan);
    }

    public Class<?> compileToClass(String className,String... wenyanString){
        Class<?> clz = groovyCompiler.compile(getGroovyCode(false,wenyanString),className);

        this.serverLogger.info("得类为:"+clz.getName());
        return clz;
    }

    public Class<?> compileToClass(String... wenyanString){
        return groovyCompiler.compile(getGroovyCode(false,wenyanString));
    }



    public Object runDirectly(boolean out,String... wenyanString){
        serverLogger.info("---------------运行之--------------------");

        return shell.evaluate(getGroovyCode(out, wenyanString));

    }

    public String getTraditionalChinese(String wenyan){
        return ZhConverterUtil.convertToTraditional(wenyan);
    }

    public void runFile(String file){
        try {
            runFile(new File(file));
        }catch (IOException e){
            serverLogger.info("",e);
        }
    }

    public void runFile(String file,String[] args){

    }

    public void runFile(File file) throws IOException {

        runDirectly(false,getGroovyCodeByFile(file));
    }


    //---------------不建议作为API使用-----------------------//

    //***************************************************//
    //***********************内部调用*********************//
    //**************************************************//

    public int init(CompilerConfig compilerConfig){
        try {
            supportPinyin = compilerConfig.isSupportPinYin();
            String[] files = compilerConfig.getCompileFiles();
            String[] libs = compilerConfig.getCompileLib();
            String[] args = compilerConfig.getRunArgs();
            boolean isRun = compilerConfig.isRun();
            String out = compilerConfig.getOutFile();
            if (out == null || files == null) {
                serverLogger.info("必要: 输出文件路径和编译文件信息");
                return 1;
            }
            for (String file : files) {
                compileOut(new File(file), new File(out));
            }

            if(isRun){
                //加载libs
                for(String lib : libs){
                    if(lib.endsWith(".jar")){
                      shell.getClassLoader().addURL(new File(lib).toURI().toURL());
                    }else runFile(lib);
                }
                for(String file:files){
                    Class<?> clz = compileToClass(FileUtils.readLines(new File(file),System.getProperty("file.codings")).toArray(new String[0]));
                    Method method = Utils.getMethod(clz,"main",String[].class);
                    if(method!=null){
                        try {
                            method.invoke(null, clz,args);
                        }catch (Exception e){
                            serverLogger.error(e.getMessage());
                        }
                    }else {
                        runFile(file);
                    }
                }
            }
        }catch (IOException e){
            serverLogger.error("",e);
        }
        return 0;
    }

    public String replaceWenYan(String wenyan,Map<String,String> map){
        List<String> list = Utils.getStrings(WenYanLib.HASH(),wenyan);
        for(String s:list){
            if(map.get(s)!=null) {
                wenyan = wenyan.replace(s, map.get(s));
                if (hasOne(wenyan, WenYanLib.NEW_START()) && hasOne(wenyan, WenYanLib.NEW_END())) {
                    wenyan = wenyan.replace(WenYanLib.NEW_START(), WenYanLib.STRING_START())
                            .replace(WenYanLib.NEW_END(), WenYanLib.STRING_END());
                }
                wenyan = replaceWenYan(wenyan, map);
            }
        }
        return wenyan;
    }

    public String replaceName(String wenyan,Map<String,String> map){
        List<String> list = Utils.getStrings(WenYanLib.HASH_NAME(),wenyan);
        for(String s : list){
            wenyan = wenyan.replace(s,map.get(s));
        }
        return wenyan;
    }

    public String compile(String wenyan){
        try{
            StringBuilder builder = new StringBuilder();
            wenyans = base(wenyan);
            while (wenyans.length != 0) {
                now = Utils.getWenyanFromArray(wenyans);
                builder.append("\n").append(factory.compile(wenyans)[0]);
                this.clearCompiled();
            }
            return builder.toString();
        }catch (Exception e){
            String message = LogFormat.textFormat("[Syntax Error] "+e.getMessage(), Ansi.Color.RED)+fg(Ansi.Color.DEFAULT);
            this.serverLogger.error(message,e);
            return message;
        }
    }

    public String nameToHASH(String wenyan,Map<String,String> map){
        List<String> list = Utils.getStrings(WenYanLib.VAR_NAME_FOR(),wenyan);
        for(String s : list){
            int count = index++;
            String hash = "「{{"+count+"HASH~"+"}}」";
            wenyan = wenyan.replace(s,hash);
            map.put(hash,s);
        }
        return wenyan;
    }


    public String wenYansToHASH(String wenyan,Map<String,String> map){
        List<String> comments = Utils.getStrings(WenYanLib.STRING(),wenyan);
        for(String comment:comments){
            int count = index++;
            String hash = "{{"+count+"HASH~"+"}}";
            map.put(hash,comment);
            wenyan = wenyan.replace(comment,hash);

            wenyan = wenYansToHASH(wenyan,map);
        }
        return wenyan;
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

    public Map<Class<? extends CompileStream>, CompileStream> getStreamMap() {
        return streamMap;
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

    public boolean isSupportPinyin() {
        return supportPinyin;
    }

    public <T extends CompileStream> T getStream(Class<T> stream){
        return stream.cast(streamMap.get(stream));
    }

    public String getNow() {
        return now;
    }


    private String compileOut(File file,File outDir) throws IOException{
        String code = getGroovyCodeByFile(file);
        FileUtils.write(new File(outDir,file.getName()+".groovy"),code,System.getProperty("file.coding"));
        return code;
    }

    private String getGroovyCodeByFile(File wenyan) throws IOException{
        List<String> list = FileUtils.readLines(wenyan,System.getProperty("file.coding"));
        StringBuilder builder = new StringBuilder();
        for(String str:list){
            builder.append(str);
        }
        return compile(builder.toString());
    }

    private String[] base(String wenyan){
        index ++;

        serverLogger.info("吾译之于 "+index+" 行也,其为'"+SyntaxColor.getSyntaxColor(wenyan,this)+"'者乎");
        //暂时草率的实现这个符号
        if(Utils.getStrings(WenYanLib.HASH(),wenyan).size()!=0){
            throw new SyntaxException("此占位符不可存在: {{$numberHASH~}}");
        }
        Map<String,String> nowMap = new HashMap<>();
        wenyan = wenYansToHASH(wenyan,nowMap);
        wenyan = nameToHASH(wenyan,nowMap);
        wenyan = JuDouUtils.getWenYan(wenyan);
        serverLogger.info("断句者为: ");
        serverLogger.info(wenyan);
        wenyans = wenyan.split(WenYanLib.SPLIT());
        for(int j = 0;j<wenyans.length;j++){
            wenyans[j] = replaceName(wenyans[j],nowMap);
        }
        for(int j = 0;j<wenyans.length;j++){
            wenyans[j] = replaceWenYan(wenyans[j],nowMap).trim();
        }
        return new ArrayList<>(Arrays.asList(wenyans)).toArray(new String[0]);
    }

    private boolean hasOne(String s,String thing){
        return s.indexOf(thing) == s.lastIndexOf(thing);
    }

    private String getGroovyCode(boolean outInConsole,String... wenyanString){
        StringBuilder groovyCode = new StringBuilder();
        for(String code:wenyanString){
            String compile = compile(code);
            if(outInConsole){
                serverLogger.info(SyntaxColor.getSyntaxColor(code,this)+" => "+ compile);
            }
            groovyCode.append(compile).append("\n");
        }
        this.serverLogger.info("此事成也，得之");
        System.out.println("----------------------------WenYanConsole--------------------------------");
        return groovyCode.toString();
    }


}
