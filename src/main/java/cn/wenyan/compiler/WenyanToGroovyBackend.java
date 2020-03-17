package cn.wenyan.compiler;

import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.utils.GroovyUtils;
import cn.wenyan.compiler.utils.ResultEntry;
import cn.wenyan.compiler.utils.Utils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.tools.Compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cn.wenyan.compiler.WenYanCompiler.ERROR;

public class WenyanToGroovyBackend implements CompileBackend {

    private WenYanCompilerImpl compiler;

    public void init(WenYanCompilerImpl impl){
        this.compiler = impl;
    }


    public void appendClassName(int index, String mainClass, String className, List<String> codes, StringBuilder builder, String annotation, String pack){
        if(codes.size()>=1&&codes.get(0).equals(ERROR)){
            throw new RuntimeException("compile error");
        }
        if(index !=-1){
            builder.append("package ");
            builder.append(pack);
        }
        boolean isMain = mainClass.equals(className);
        String filter = "import";
        ResultEntry codeEntry = getStaticCode(makeCodeStatic(codes,!isMain),isMain);
        String code = filterAndToString(codeEntry.getCode(),filter);
        String imports = getImports(codes);

        builder.append("\n");
        builder.append(compiler.getLanguageType().getSyntax(Syntax.IMPORT_WITH));
        builder.append("\n");
        builder.append(imports);
        if(!isMain){

            builder.append("\n");
            builder.append(annotation);
            builder.append("class ");
            builder.append(className.replace(pack,"").replace(".",""));
            builder.append("{");
            builder.append("\n");
        }
        builder.append(code);
        if(!isMain) {
            builder.append("\nstatic{\n");
            builder.append(codeEntry.getOutCode());
            builder.append("}");
            builder.append("\n}");
        }
    }




    public void compileToClass(File out,File classFile) throws FileNotFoundException {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        PrintWriter writer = new PrintWriter(classFile);
        compilerConfiguration.setOutput(writer);
        compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8);
        compilerConfiguration.setTargetDirectory(compiler.getClassPath());
        compilerConfiguration.setClasspath(compiler.getClassPath());
        Compiler compiler = new Compiler(compilerConfiguration);
        compiler.compile(out);
        writer.close();
    }

    private String getImports(List<String> results){
        StringBuilder builder = new StringBuilder();
        for(String r :results) {
            if(r!=null&&!r.startsWith("import"))continue;
            builder.append("\n").append(r);
        }
        return builder.toString();
    }


    private ResultEntry getStaticCode(List<String> results,boolean isMain){
        if(isMain){
            return new ResultEntry(results,"");
        }
        StringBuilder builder = new StringBuilder();
        List<String> rs = new ArrayList<>(results);
        int index = 0;
        for(int i = 0;i<results.size();i++){
            int close = Utils.getClose(results.get(i));
            index += close;
            if(index == 0){
                if(close == 0){
                    if(!results.get(i).startsWith("static")&&!results.get(i).startsWith("import")) {
                        builder.append(results.get(i)).append("\n");
                        rs.set(i, null);
                    } else if(results.get(i).startsWith("static")&&results.get(i).contains("=")){
                        String[] defs = GroovyUtils.splitGroovyCode(results.get(i),"\n").toArray(new String[0]);
                        StringBuilder builder1 = new StringBuilder();
                        for(String def : defs){
                            String[] name_value = GroovyUtils.splitGroovyCode(def,"=").toArray(new String[0]);
                            builder1.append(name_value[0]).append("\n");
                            builder.append(name_value[0].split(" ")[2]).append("=").append(name_value[1]).append("\n");
                        }
                        rs.set(i,builder1.toString());
                    }
                }
            }
        }
        Iterator<String> iterator = rs.iterator();
        while (iterator.hasNext()){
            if(iterator.next() == null)iterator.remove();
        }
        return new ResultEntry(rs,builder.toString());
    }



    private List<String> makeCodeStatic(List<String> results,boolean get){
        if(get) {
            int index = 0;
            for (int i = 0; i < results.size(); i++) {
                if (index == 0) {
                    String result = results.get(i);
                    if (result.startsWith("def")||result.startsWith("class")){
                        StringBuilder builder = new StringBuilder();
                        String[] str = GroovyUtils.splitGroovyCode(result,"\n").toArray(new String[0]);
                        for(int z = 0;z<str.length;z++){
                            if(str[z].startsWith("def"))
                                builder.append("static ").append(str[z]).append("\n");
                            else
                                builder.append(str[z]).append("\n");
                        }
                        results.set(i,builder.toString());
                    }
                }
                index += Utils.getClose(results.get(i));
            }
        }
        return results;
    }







}
