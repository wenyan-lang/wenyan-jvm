package cn.wenyan.compiler;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface CompileBackend extends Init{

    void appendClassName(int index, String mainClass, String className, List<String> codes, StringBuilder builder, String annotation, String pack);

    default String filterAndToString(List<String> results,String filter){
        StringBuilder builder = new StringBuilder();
        for(String r :results) {
            if(r!=null&&filter!=null&&r.startsWith(filter))continue;
            builder.append("\n").append(r);
        }
        return builder.toString();
    }



    void compileToClass(File out, File classFile) throws FileNotFoundException;
}
