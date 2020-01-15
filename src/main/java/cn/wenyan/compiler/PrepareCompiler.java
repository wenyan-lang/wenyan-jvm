package cn.wenyan.compiler;


import cn.wenyan.compiler.lib.Define;
import cn.wenyan.compiler.lib.Defines;
import cn.wenyan.compiler.utils.JuDouUtils;
import cn.wenyan.compiler.utils.Utils;

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


    public String macroPrepare(String wenyanCode){

        Map<String,String> get = getMacroMapping(wenyanCode);

        macroMapping.putAll(get);

        Map<String,List<String>> names = getMacroNames(get);
        macroNames.putAll(names);

        macroPatterns.putAll(getMacroPatterns(get,names));

        macroPatterns.forEach((x,y)->WenYanLib.syntaxs().put(y,x));
        // 宏工作
        List<String> strs = JuDouUtils.splitWenYan(wenyanCode);

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

        StringBuilder builder = new StringBuilder();

        for(String str : strs){
            builder.append(str);
        }



        // 导入
        // 1. wy文件 则找到宏，之后存起来
        // 2. class文件 若加载了，那么获取@Define


        return builder.toString();

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
            System.out.println(first);
            System.out.println(end);
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
