package cn.wenyan.compiler.utils;

import cn.wenyan.compiler.WenYanCompiler;
import cn.wenyan.compiler.WenYanLib;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static List<String> getStrings(String patternId,String... things){
        List<String> matchers = new ArrayList<>();
        Pattern typePattern = WenYanLib.patterns().get(patternId).get();
        for(String thing : things) {
            Matcher typeMatcher = typePattern.matcher(thing);
            int i = 0;
            while (typeMatcher.find()) {
                matchers.add(typeMatcher.group(i));
                i++;
            }
        }
        return matchers;
    }

    public static String getString(String patternId,String thing){
        return getStrings(patternId, thing).get(0);
    }

    public static void inputWenyan(WenYanCompiler compiler, int wenyanIndex){
        compiler.getNowCompiling().add(wenyanIndex);
    }

    public static String getStringFrom(String patternId,String thing,String start){
        String value = getString(patternId,thing);
        return value.substring(value.indexOf(start)+1);
    }

    public static String getStringFrom(String patternId,String thing,String start,String end){
        String value = getString(patternId,thing);
        return value.substring(value.indexOf(start)+1,value.lastIndexOf(end));
    }

    public static boolean matches(String thing,String patternId){
        return thing.matches(WenYanLib.syntaxs().get(patternId).get());
    }

    public static String getWenyanFromArray(String[] wenyans){
        StringBuilder builder = new StringBuilder();
        for(String wenyan: wenyans){
            builder.append(wenyan).append("。");
        }
        return builder.toString();
    }
    public static void appendSplit(List<String> newWenyans,String[] wenyans){
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
