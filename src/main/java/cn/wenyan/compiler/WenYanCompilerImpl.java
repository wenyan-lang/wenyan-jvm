package cn.wenyan.compiler;



import cn.wenyan.compiler.command.CommandHandler;
import cn.wenyan.compiler.command.CompilerConfig;
import cn.wenyan.compiler.factory.CompileFactory;
import cn.wenyan.compiler.factory.StreamBuilder;
import cn.wenyan.compiler.log.LogFormat;
import cn.wenyan.compiler.log.ServerLogger;
import cn.wenyan.compiler.plugins.Listener;
import cn.wenyan.compiler.plugins.PluginManager;
import cn.wenyan.compiler.script.libs.Language;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.streams.*;
import cn.wenyan.compiler.utils.JuDouUtils;
import cn.wenyan.compiler.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.tools.Compiler;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static cn.wenyan.compiler.log.LogFormat.fg;


/**
 * 若施文言之术，必先用其器。古人云: 君子生非异，善假于物。
 * 此器以爪哇之法，行虚拟机之道，亦可广传文言于天下者。
 * 君用[run]之洋文可走之。吾欲将其译为java者也。
 */
public class WenYanCompilerImpl implements WenYanCompiler {

    private List<Listener> listeners;

    private Map<String,String> nameType = new HashMap<>();

    private int indexCode;

    private boolean supportPinyin;

    private List<Integer> nowCompiling = new ArrayList<>();

    private LanguageCompiler groovyCompiler;

    private ServerLogger serverLogger;

    private CompileFactory factory;


    private List<String> wenyans;

    private CommandHandler handler;

    private Language languageType;

    private PluginManager pluginManager;

    private WenYanRuntime runtime;

    private Map<Class<? extends CompileStream>,CompileStream> streamMap;

    //***************************************************//
    //*********************构造器*************************//
    //**************此为天地之造物者，乃于此乎。**************//
    //**************************************************//

    WenYanCompilerImpl(boolean supportPinyin,Language language){
        this(supportPinyin,language,null);
    }

    WenYanCompilerImpl(boolean supportPinyin, Language language, WenYanShell shell){
        this.languageType = language;
        this.streamMap = new HashMap<>();
        this.groovyCompiler = language.languageCompiler();
        this.serverLogger = new ServerLogger(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile());
        this.handler = new CommandHandler(this);
        this.supportPinyin = supportPinyin;
        if(File.separator.equals("\\")) AnsiConsole.systemInstall();
        this.serverLogger.info(LogFormat.textFormat(LogFormat.Control.BOLD.getAnsi()+"WenYan Lang JVM Compiler"+ fg(Ansi.Color.DEFAULT),Ansi.Color.YELLOW));
        this.serverLogger.info("@CopyRight wy-lang.org || github: https://github.com/LingDong-/wenyan-lang");
        this.serverLogger.info("WenYan 3rd Party Compiler : github: https://github.com/MagicLu550/wenyan-lang_jvm/blob/master/README.md");
        this.serverLogger.info("文言文语言的语法规则最终由LingDong的wenyan-lang为基本要素");
        this.listeners = new ArrayList<>();
        this.factory = new StreamBuilder(this)
                .put(new VariableCompileStream(this))
                .put(new CommentCompileStream(this))
                .put(new ControlCompileStream(this))
                .put(new MathCompileStream(this))
                .put(new FunctionCompileStream(this))
                .put(new ArrayCompileStream(this))
                .build();
        this.pluginManager = new PluginManager(this);
        this.runtime = new WenYanRuntime(this,shell==null?new WenYanShell(this):shell);
        this.loadPlugins();
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
        return compile(wenyan,true);
    }

    public Class<?> compileToClass(String className,String... wenyanString){
        Class<?> clz = groovyCompiler.compile(getGroovyCode(true,false,wenyanString),className);

        this.serverLogger.info("得类为:"+clz.getName());
        return clz;
    }

    public Class<?> compileToClass(String... wenyanString){
        return groovyCompiler.compile(getGroovyCode(true,false,wenyanString));
    }

    public WenYanRuntime getRuntime() {
        return runtime;
    }

    //---------------不建议作为API使用-----------------------//

    //***************************************************//
    //***********************内部调用*********************//
    //**************************************************//

    public int init(CompilerConfig compilerConfig){
        try {
            supportPinyin = compilerConfig.isSupportPinYin();
            String[] files = compilerConfig.getCompileFiles();
            String[] args = compilerConfig.getRunArgs();
            String[] libs = compilerConfig.getCompileLib();
            String classPath = compilerConfig.getSourcePath();
            String mainClass = compilerConfig.getMainClass();
            boolean isRun = compilerConfig.isRun();
            String out = compilerConfig.getOutFile();
            if (out == null || files == null) {
                serverLogger.info("必要: 输出文件路径和编译文件信息");
                return 1;
            }
            if(classPath == null){
                serverLogger.info("请指定sourcePath");
            }
            List<File> files1 = new ArrayList<>();
            for (String file : files) {
                files1.add(compileOut(classPath,new File(file), new File(out),mainClass,compilerConfig.isGroovy()));
            }

            if(isRun){
                //加载libs
                if(libs!=null){
                    for(String lib : libs){
                        if(lib.endsWith(".jar")){
                            runtime.getShell().getRun().getClassLoader().addURL(new File(lib).toURI().toURL());
                        }else runtime.runFile(lib);
                    }
                }

                for (File file : files1) {
                    if(file !=null)
                        runtime.getShell().getRun().run(file,args);
                }
            }
        }catch (IOException e){
            serverLogger.error("",e);
        }
        return 0;
    }


    public List<String> compileToList(String wenyan,boolean outError){
        List<String> results = new ArrayList<>();
        wenyans = JuDouUtils.splitWenYan(wenyan);
        serverLogger.info(JuDouUtils.getLine(wenyans));
        try {
            while (wenyans.size() != 0) {

                results.add(factory.compile(0, wenyans).get(0));
            }
            return results;
        }catch (Exception e){
            String message = LogFormat.textFormat("[Syntax Error] " + e.getMessage(), Ansi.Color.RED) + fg(Ansi.Color.DEFAULT);
            if(outError) {
                this.serverLogger.error(message, e);
            }else{
                this.serverLogger.error(message);
            }
            results.set(0,ERROR);
            return results;
        }
    }

    public String compile(String wenyan,boolean outError){
        List<String> results = compileToList(wenyan,outError);
        if(results.size()!=0) {
            if (results.get(0).equals(ERROR)) {
                return ERROR;
            }
        }

        return codesTocode(results,null);
    }

    public ServerLogger getServerLogger() {
        return serverLogger;
    }



    private String codesTocode(List<String> results,String filter){
        StringBuilder builder = new StringBuilder();
        for(String r :results) {
            if(r!=null&&filter!=null&&r.startsWith(filter))continue;
            builder.append("\n").append(r);
        }
        return builder.toString();
    }

    private String makeStaticTocode(List<String> results,String filter,boolean get){
        if(get) {
            int index = 0;
            for (int i = 0; i < results.size(); i++) {
                if (index == 0) {
                    String result = results.get(i);
                    if (result.startsWith("def"))
                        results.set(i, "static " + results.get(i));
                }
                index += Utils.getClose(results.get(i));
            }
        }
        return codesTocode(results,filter);
    }


    private String getImports(List<String> results){
        StringBuilder builder = new StringBuilder();
        for(String r :results) {
            if(r!=null&&!r.startsWith("import"))continue;
            builder.append("\n").append(r);
        }
        return builder.toString();
    }

    //TODO 如果未来实现了类，必须要把类剥离出来
    //TODO groovy独特
    private File compileToGroovy(File thisFile,String sc,File file,String wenyanString,String mainClass,boolean isGroovy){
        try {
            String className = thisFile.toString().replace(sc,"");
            if(className.startsWith(File.separator)){
                className = className.substring(1);
            }
            className = className.substring(0,className.lastIndexOf("."));
            className = className.replace(File.separator,".");
            List<String> codes = compileToList(wenyanString,false);
            boolean get = true;
            if(mainClass.equals(className)){
                get = false;
            }
            String code = makeStaticTocode(codes,"import",get);
            String imports = getImports(codes);

            int index = className.lastIndexOf(".");
            StringBuilder builder = new StringBuilder();
            String pack = "";
            if(index !=-1){
                pack = className.substring(0,index);
                builder.append("package ");
                builder.append(pack);
            }
            builder.append("\n");
            builder.append(languageType.getSyntax(Syntax.IMPORT_WITH));
            if(!mainClass.equals(className)){

                builder.append("\n");
                builder.append(imports);
                builder.append("\n");
                builder.append("class ");
                builder.append(className.replace(pack,"").replace(".",""));
                builder.append("{");
                builder.append("\n");
            }
            builder.append(code);
            if(!mainClass.equals(className)) {

                builder.append("\n}");
            }

            code = builder.toString();
            File parent = new File(file+File.separator+pack.replace(".",File.separator));
            if(!parent.exists())parent.mkdirs();
            String name = File.separator+thisFile.getName().split("\\.")[0];
            File out = new File(parent,name+".groovy");
            CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
            File classFile = new File(parent,name+".class");
            if(!classFile.exists())classFile.createNewFile();
            PrintWriter writer = new PrintWriter(classFile);
            compilerConfiguration.setOutput(writer);
            compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8);
            compilerConfiguration.setTargetDirectory(parent.getParent());
            Compiler compiler = new Compiler(compilerConfiguration);
            if(isGroovy)
                FileUtils.write(out,code,System.getProperty("file.coding"));
            if(!parent.exists())parent.mkdirs();
            compiler.compile(out);
            writer.close();
            serverLogger.info("得文件为: "+file);
            if(isGroovy)
                return out;
            else
                return null;
        }catch (Exception e){
            serverLogger.error("Syntax Error",e);
            return null;
        }
    }

    public Map<Class<? extends CompileStream>, CompileStream> getStreamMap() {
        return streamMap;
    }

    public List<Integer> getNowCompiling() {
        return nowCompiling;
    }

    public boolean isSupportPinyin() {
        return supportPinyin;
    }

    public <T extends CompileStream> T getStream(Class<T> stream){
        return stream.cast(streamMap.get(stream));
    }

    public int getIndexCode() {
        return indexCode;
    }

    public void setIndexCode() {
        indexCode++;
    }

    public CompileFactory getFactory() {
        return factory;
    }

    public String removeWenyan(){
        setIndexCode();
        return this.wenyans.remove(0);
    }


    public Map<String, String> getNameType() {
        return nameType;
    }

    public Language getLanguageType() {
        return languageType;
    }

    public List<Listener> getListeners() {
        return listeners;
    }

    public void callListener(CompileStream stream,List<String> wenyans,boolean start){
        if(start){
            for(Listener listener : listeners){
                listener.onCompileStart(stream,wenyans);
            }
        }else{
            for(Listener listener : listeners){
                listener.onCompileFinish(stream,wenyans);
            }
        }
    }

    public String getGroovyCode(boolean addNow,boolean outInConsole,String... wenyanString){
        StringBuilder groovyCode = new StringBuilder();
        if(addNow)
            groovyCode.append(languageType.getSyntax(Syntax.IMPORT_WITH));
        for(String code:wenyanString){
            String compile = compile(code,true);
            if(outInConsole){
                serverLogger.info(code+" => "+ compile);
            }
            groovyCode.append(compile).append("\n");
        }
        this.serverLogger.info("此事成也，得之");
        System.out.println("----------------------------WenYanConsole--------------------------------");
        indexCode = 0;
        return groovyCode.toString();
    }

    public String getGroovyCodeByFile(File wenyan) throws IOException{
        List<String> list = FileUtils.readLines(wenyan,System.getProperty("file.coding"));
        StringBuilder builder = new StringBuilder();
        for(String str:list){
            String strNoT = trimWenYan(str);
            builder.append(strNoT);
        }
        return builder.toString();
    }



    private File compileOut(String classPath,File file, File outDir,String mainClass,boolean groovy) throws IOException{

        return compileToGroovy(file,classPath,outDir,getGroovyCodeByFile(file),mainClass,groovy);

    }



    private String trimWenYan(String s){
        return JuDouUtils.trimWenYanX(s);
    }

    private void loadPlugins(){
        File pluginFile = new File(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile()+"/plugins");
        if(!pluginFile.exists()) pluginFile.mkdirs();
        File[] plugins = pluginFile.listFiles();
        if(plugins != null){
            for(File f : plugins){
                if (f.toString().endsWith(".jar"))
                    pluginManager.loadPlugin(f);
            }
        }
    }


}
