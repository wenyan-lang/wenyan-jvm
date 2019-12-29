package cn.wenyan.compiler.utils;

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
}
