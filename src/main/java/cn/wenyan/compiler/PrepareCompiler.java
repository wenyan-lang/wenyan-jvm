package cn.wenyan.compiler;


import cn.wenyan.compiler.lib.Define;
import cn.wenyan.compiler.lib.Defines;
import cn.wenyan.compiler.streams.FunctionCompileStream;
import cn.wenyan.compiler.utils.LexerUtils;
import cn.wenyan.compiler.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrepareCompiler {

    private WenYanCompilerImpl compiler;

    private Map<String,String> macroMapping;//macro -- wenyan

    private Map<String,List<String>> macroNames; //macro -- names

    // pattern macro
    private Map<String,String> macroPatterns;

    public PrepareCompiler(WenYanCompilerImpl compiler) {
        this.compiler = compiler;
        this.macroMapping = new HashMap<>();
        this.macroNames = new HashMap<>();
        this.macroPatterns = new HashMap<>();
    }

    private void initMacro(Map<String,String> get){
        macroMapping.putAll(get);
        Map<String,List<String>> names = getMacroNames(get);
        macroNames.putAll(names);

        macroPatterns.putAll(getMacroPatterns(get,names));

        macroPatterns.forEach((x,y)->WenYanLib.syntaxs().put(y,x));

    }



    public List<String> macroPrepare(String wenyanCode){

        Map<String,String> get = getMacroMapping(wenyanCode);

        //初始化本文件的macro
        initMacro(get);

        //导入其他文件macro，前提是import不是宏的
        List<String> strs = LexerUtils.wenYanLexer(wenyanCode);


        List<String> imports = getImportsWenYan(strs);

        importMacro(imports);

        //展开
        expansion(strs);


        //重新解析
        List<String> format = LexerUtils.wenYanLexer(getString(strs));

        imports = getImportsWenYan(format);

        //导入之前宏定义的导入语句的宏
        importMacro(imports);

        expansion(format);

        format = LexerUtils.wenYanLexer(getString(format));

        //编译import的文件
        compileImports(imports);

        return format;
    }

    private String getString(List<String> strs){
        StringBuilder builder = new StringBuilder();
        for(String str : strs){
            builder.append(str);
        }
        return builder.toString();
    }

    private void expansion(List<String> strs){
        for(int i = 0;i<strs.size();i++){
            int index = i;
            String str = strs.get(i);
            macroPatterns.forEach((k,v)->{
                if(str.matches(k)){
                    List<String> ns = macroNames.get(v);
                    List<String> values = Utils.getStrings(WenYanLib.VALUE(),str);
                   
                    String result = macroMapping.get(v);
                    for(int j = 0;j<values.size();j++){
                        result = result.replace(ns.get(j), values.get(j));
                    }
                    strs.set(index,result);
                    return;
                }
            });
        }
    }

    private List<String> getImportsWenYan(List<String> lists){
        List<String> list = new ArrayList<>();
        FunctionCompileStream stream = compiler.getStream(FunctionCompileStream.class);
        for(int i = 0;i<lists.size();i++){
            if(Utils.matches(lists.get(i),WenYanLib.IMPORT())){
                list.add(compiler.library.get(stream.getClassName(lists.get(i)).replace("import","").trim()));
            }
        }
        return list;
    }

    private Map<String,String> getMacroPatterns(Map<String,String> macros,Map<String,List<String>> names){
        Map<String,String> macrosPatterns = new HashMap<>();
        macros.forEach((macro,value)->{
            List<String> macroNames = names.get(macro);
            String mac = macro;
            for(String name : macroNames){
                mac = mac.replace(name,"[\\s\\S]+");
            }

            macrosPatterns.put(mac,macro);
        });
        return macrosPatterns;
    }

    String toAnnotation(String wenyanCode){
        StringBuilder builder = new StringBuilder("@"+Defines.class.getSimpleName()+"([");
        getMacroMapping(wenyanCode).forEach((x,y)->builder
                .append("\t@")
                .append(Define.class.getSimpleName())
                .append("(before = ")
                .append("'")
                .append(x)
                .append("'")
                .append(",after = ")
                .append("'")
                .append(y)
                .append("'")
                .append("),\n")
        );
        return builder.toString()+"])\n";
    }

    private Map<String,List<String>> getMacroNames(Map<String,String> macros){
        Map<String,List<String>> map = new HashMap<>();

        macros.forEach((macro,name)->map.put(macro,Utils.getStrings(WenYanLib.VAR_NAME_FOR(),macro)));
        return map;
    }
    //被替换，替换成
    private Map<String,String> getMacroMapping(String wenyanCode){
        Map<String,String> map = new HashMap<>();
        List<String> macros = getMacro(wenyanCode);
        for(int i = 0;i<macros.size();i+=2){
            String first = macros.get(i);
            String end = macros.get(i+1);
            first = first.substring(first.indexOf("「「")+2,first.lastIndexOf("」」"));
            end = end.substring(first.indexOf("「「")+5,end.lastIndexOf("」」"));
            map.put(first,end);
        }
        return map;
    }

    private List<String> getMacro(String wenyanCode){
        //从import中得到其他文件的宏
        List<String> macros = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        char[] chars = LexerUtils.trimWenYanX(wenyanCode).toCharArray();
        int close = 0;
        for(int i = 0;i<chars.length;i++){
            char c = chars[i];
            close += getClose(c);
            if(close==0){
                if(c == '或'||c == '蓋'){
                    i++;
                    builder.append(c);
                    if((c == '或'&&chars[i] == '云')||(c =='蓋'&& chars[i] == '謂')){
                       builder.append(chars[i]);
                       boolean start = false;
                       while (true){
                           i++;
                           if(chars[i] == '「'){
                               if(!start)start = true;
                           }
                           builder.append(chars[i]);
                           close += getClose(chars[i]);
                           if(close == 0&&start)break;
                       }

                       macros.add(builder.toString());
                       builder = new StringBuilder();
                    }
                }
            }
        }
        return macros;
    }



    private static int getClose(char c){
        if(c == '「')return 1;
        if(c == '」')return  -1;
        return 0;
    }

    private void importMacro(List<String> imports){
        try {
            Map<String,String> macros = new HashMap<>();
            for (String im : imports) {
                if (Utils.classExists(im)) {
                    Class<?> clz = Class.forName(im);
                    Defines definesObj = clz.getAnnotation(Defines.class);
                    if(definesObj!=null) {
                        Define[] defines = definesObj.value();
                        for (Define define : defines) {
                            macros.put(define.before(), define.after());
                        }
                        initMacro(macros);
                    }
                }else{
                    String file = compiler.getSourcePath()+"/"+im.replace(".", File.separator)+".wy";
                    initMacro(getMacroMapping(compiler.getWenYanCodeByFile(new File(file))));
                }
            }
        }catch (ClassNotFoundException| IOException e){
            compiler.getServerLogger().error("",e);
        }
    }

    private void compileImports(List<String> imports){
        for(String imp : imports) {
            if (compiler.getSourcePath() != null && !Utils.classExists(imp)) {

                String path = compiler.getSourcePath();
                String filePath = path + File.separator + imp.replace(".", File.separator) + ".wy";
                try {
                    compiler.compileOut(compiler.getSourcePath(), new File(filePath), new File(compiler.getClassPath()), compiler.getMainClass(), true);
                } catch (IOException e) {
                    compiler.getServerLogger().error("", e);
                }
            }
        }
    }

}
