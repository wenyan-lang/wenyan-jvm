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
import cn.wenyan.compiler.script.libs.Library;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.streams.*;
import cn.wenyan.compiler.utils.LexerUtils;
import cn.wenyan.compiler.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.tools.Compiler;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static cn.wenyan.compiler.log.LogFormat.fg;


/**
 * 若施文言之术，必先用其器。古人云: 君子生非异，善假于物。
 * 此器以爪哇之法，行虚拟机之道，亦可广传文言于天下者。
 * 君用[run]之洋文可走之。吾欲将其译为java者也。
 */
public class WenYanCompilerImpl implements WenYanCompiler,Cloneable{

    private Map<File,File> wygFiles = new HashMap<>();

    protected Library library;

    private CompilerConfig compilerConfig;

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

    private String sourcePath;

    private String classPath;

    private List<String> compiled;

    private PrepareCompiler prepareCompiler;

    private String mainClass;

    //***************************************************//
    //*********************构造器*************************//
    //**************此为天地之造物者，乃于此乎。**************//
    //**************************************************//

    WenYanCompilerImpl(boolean supportPinyin,Language language){
        this(supportPinyin,language,null);
    }

    WenYanCompilerImpl(boolean supportPinyin, Language language, WenYanShell shell){
        this.languageType = language;
        this.library = new Library(language);
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
                .put(new TryCompileStream(this))
                .put(new ObjectCompileStream(this))
                .build();
        this.pluginManager = new PluginManager(this);
        this.runtime = new WenYanRuntime(this,shell==null?new WenYanShell(this):shell);
        this.compiled = new ArrayList<>();
        this.mainClass = "";
        this.sourcePath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).getParent();
        this.classPath = sourcePath;
        this.loadPlugins();
        this.prepareCompiler = new PrepareCompiler(this);
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
            this.sourcePath = compilerConfig.getSourcePath();
            this.mainClass = compilerConfig.getMainClass();
            this.compilerConfig = compilerConfig;
            this.supportPinyin = compilerConfig.isSupportPinYin();
            String[] files = compilerConfig.getCompileFiles();
            String[] args = compilerConfig.getRunArgs();
            String[] libs = compilerConfig.getCompileLib();
            String classFile = compilerConfig.getClassFile();
            String wenyuangeFile = compilerConfig.getWenyuangeFile();
            String wygDownload = compilerConfig.getWygDownload();
            boolean isRun = compilerConfig.isRun();
            classPath = compilerConfig.getOutFile();
            if(classPath == null)return 1;
            File cp = new File(classPath);
            if(wygDownload!=null){
                Runtime.getRuntime().exec("npm i -g @wenyanlang/wyg");
                Runtime.getRuntime().exec("wyg i "+wygDownload);
            }
            if(wenyuangeFile!=null){
                ///藏书楼的位置
                String wygf = wenyuangeFile+"/";
                File file = new File(wygf.equals("null/")?WYG:wygf+WYG);
                File[] fileLibs = file.listFiles();
                compileWygs(fileLibs,cp);
            }

            if ((classPath == null || files == null)&&!isRun) {
                serverLogger.info("必要: 输出文件路径和编译文件信息");
                return 1;
            }
            if(sourcePath == null&&!isRun){
                serverLogger.info("请指定sourcePath");
            }

            if(libs!=null){
                for(String lib : libs){
                    if(lib.endsWith(".jar")){
                        runtime.getShell().getRun().getClassLoader().addURL(new File(lib).toURI().toURL());
                    }
                }
            }

            if(files!=null) {
                for (String file : files) {
                    compileOut(sourcePath, new File(file), cp, mainClass, compilerConfig.isGroovy());
                }
            }
            // -jar a.jar;b.jar -o classPath -n className -r args
            if(isRun){

                if(classFile!=null){
                    try {
                        runtime.getShell().getRun().getClassLoader().addURL(new File(classPath).toURI().toURL());
                        Class<?> clz = runtime.getShell().getRun().getClassLoader().loadClass(classFile.replace(classPath,"").replace(File.separator,"."));
                        clz.getDeclaredMethod("main",String[].class).invoke(null,(Object)args);
                    }catch (Exception e){
                        serverLogger.error("",e);
                    }
                }
            }
            wygFiles.forEach((x,y)->x.renameTo(y));
        }catch (IOException e){
            serverLogger.error("",e);
        }
        return 0;
    }


    public List<String> compileToList(String wenyan,boolean outError){
        List<String> results = new ArrayList<>();
        try {
            wenyans = prepareCompiler.macroPrepare(wenyan);
            serverLogger.info(LexerUtils.getLine(wenyans));
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
            e.printStackTrace();
            results.add(0,ERROR);
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

    public void compileToClass(File out,File classFile) throws FileNotFoundException {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        PrintWriter writer = new PrintWriter(classFile);
        compilerConfiguration.setOutput(writer);
        compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8);
        compilerConfiguration.setTargetDirectory(classPath);
        compilerConfiguration.setClasspath(classPath);
        Compiler compiler = new Compiler(compilerConfiguration);
        compiler.compile(out);
        writer.close();
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

    public String getWenYanCodeByFile(File wenyan) throws IOException{
        List<String> list = FileUtils.readLines(wenyan,System.getProperty("file.coding"));
        StringBuilder builder = new StringBuilder();
        for(String str:list){
            String strNoT = trimWenYan(str);
            builder.append(strNoT);
        }
        return builder.toString();
    }



    public void compileOut(String classPath,File file, File outDir,String mainClass,boolean groovy) throws IOException{
        compileToGroovy(file,classPath,outDir, getWenYanCodeByFile(file),mainClass,groovy);
    }
    public String getSourcePath() {
        return sourcePath;
    }

    public String getClassPath() {
        return classPath;
    }

    public String getMainClass() {
        return mainClass;
    }

    public Library getLibrary() {
        return library;
    }

    @Override
    public WenYanCompilerImpl clone() throws CloneNotSupportedException {
        return (WenYanCompilerImpl) super.clone();
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

    private String getClassName(File thisFile,String sc){
        String className = thisFile.toString().replace(sc,"");
        if(className.startsWith(File.separator)){
            className = className.substring(1);
        }
        className = className.replace(File.separator,".");
        return className.substring(0,className.lastIndexOf("."));
    }
    //TODO 如果未来实现了类，必须要把类剥离出来
    //TODO groovy独特
    private File compileToGroovy(File thisFile,String sc,File file,String wenyanString,String mainClass,boolean isGroovy){
        try {
            String annotation = prepareCompiler.toAnnotation(wenyanString);
            String className = getClassName(thisFile,sc);
            StringBuilder builder = new StringBuilder();
            int index = className.lastIndexOf(".");
            String pack = "";
            if(index !=-1){
                pack = className.substring(0,index);
                builder.append("package ");
                builder.append(pack);
            }
            File parent = new File(file+File.separator+pack.replace(".",File.separator));
            if(!parent.exists())parent.mkdirs();
            String name = File.separator+thisFile.getName().split("\\.")[0];
            File out = new File(parent,name+".groovy");

            File classFile = new File(parent,name+".class");
            if(!compiled.contains(classFile.toString())){
                compiled.add(classFile.toString());
            }else {
                return out;
            }
            List<String> codes = compileToList(wenyanString,false);
            boolean get = true;
            if(mainClass.equals(className)){
                get = false;
            }
            String code = makeStaticTocode(codes,"import",get);
            String imports = getImports(codes);

            builder.append("\n");
            builder.append(languageType.getSyntax(Syntax.IMPORT_WITH));
            builder.append("\n");
            builder.append(imports);
            if(!mainClass.equals(className)){

                builder.append("\n");
                builder.append(annotation);
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

            if(!classFile.exists())classFile.createNewFile();


            if(isGroovy)
                FileUtils.write(out,code,System.getProperty("file.coding"));
            if(!parent.exists())parent.mkdirs();

            compileToClass(out,classFile);

            serverLogger.info("得文件为: "+out);
            if(isGroovy)
                return out;
            else
                return null;
        }catch (Exception e){
            serverLogger.error("Syntax Error",e);
            return null;
        }
    }

    private void compileWyg(File f,File cp) throws IOException{
        File xu = new File(f,WYG_LIB);
        File xuAfter = new File(f,f.getName()+".wy");
        xu.renameTo(xuAfter);
        compileOut(sourcePath,xuAfter,cp,mainClass,compilerConfig.isGroovy());
        library.addLib(f.getName(),getClassName(xuAfter,sourcePath));
        wygFiles.put(xuAfter,xu);
    }

    private void compileWygs(File[] fileLibs,File cp) throws IOException{
        if(fileLibs!=null){
            for(File f : fileLibs){
                File[] fs = f.listFiles();
                if(fs!=null&&fs.length != 1){
                    compileWygs(fs, cp);
                }
                if(f.isDirectory()){
                    compileWyg(f,cp);
                }
            }
        }
    }

    private String trimWenYan(String s){
        return LexerUtils.trimWenYanX(s);
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
