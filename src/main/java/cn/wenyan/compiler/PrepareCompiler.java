package cn.wenyan.compiler;


import cn.wenyan.compiler.lib.Define;
import cn.wenyan.compiler.lib.Defines;
import cn.wenyan.compiler.streams.FunctionCompileStream;
import cn.wenyan.compiler.utils.JuDouUtils;
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

        initMacro(get);
        // 宏工作
        List<String> strs = JuDouUtils.splitWenYan(wenyanCode);

        expansion(strs);

        List<String> format = JuDouUtils.splitWenYan(getString(strs));

        List<String> imports = getImportsWenYan(format);

        Map<String,String> macros = new HashMap<>();
        try {
            for (String im : imports) {
                if (Utils.classExists(im)) {
                    Class<?> clz = Class.forName(im);
                    Defines definesObj = clz.getAnnotation(Defines.class);
                    Define[] defines = definesObj.value();
                    for(Define define : defines){
                        macros.put(define.before(),define.after());
                    }
                }else{
                    String file = compiler.getSourcePath()+"/"+im.replace(".", File.separator);
                    initMacro(getMacroMapping(compiler.getWenYanCodeByFile(new File(file))));
                }
            }
        }catch (ClassNotFoundException| IOException e){
            compiler.getServerLogger().error("",e);
        }

        expansion(format);

        format = JuDouUtils.splitWenYan(getString(format));

        //第二次检测import

        // 导入
        // 1. wy文件 则找到宏，之后存起来
        // 2. class文件 若加载了，那么获取@Define
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
                        result = result.replace(ns.get(j),values.get(j));
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
            if(lists.get(i).matches(WenYanLib.IMPORT())){
                list.add(stream.getClassName(lists.get(i)).replace("import","").trim());
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
                mac = macro.replace(name,"[\\s\\S]+");
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
        char[] chars = JuDouUtils.trimWenYanX(wenyanCode).toCharArray();
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

}
