package cn.wenyan.compiler.utils;

import cn.wenyan.compiler.script.libs.Language;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.streams.VariableCompileStream;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static List<String> getStrings(String patternId,String... things){
        List<String> matchers = new ArrayList<>();
        Pattern typePattern = WenYanLib.patterns().get(patternId).get();
        for(String thing : things) {
            Matcher typeMatcher = typePattern.matcher(thing);
            while (typeMatcher.find()) {
                matchers.add(typeMatcher.group(0));
            }
        }
        return matchers;
    }

    public static String getString(String patternId,String thing){
        List<String> strs = getStrings(patternId, thing);
        if(strs.size() == 0)return null;
        return strs.get(0);
    }

    public static void inputWenyan(WenYanCompilerImpl compiler, int wenyanIndex){
        compiler.getNowCompiling().add(wenyanIndex);
    }

    public static String getStringFrom(String patternId,String thing,String start){
        String value = getString(patternId,thing);
        return value.substring(value.indexOf(start)+1);
    }

    public static void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
    }

    public static String getStringFrom(String patternId,String thing,String start,String end){
        String value = getString(patternId,thing);
        if(patternId.equals(WenYanLib.STRING())||patternId.equals(WenYanLib.COMMENT())){
            if(value == null){
                value = getString(WenYanLib.ONLY_STRING(),thing);
            }
        }
        return value.substring(value.indexOf(start)+start.length(),value.lastIndexOf(end));
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

    public static Method getMethod(Class<?> clz,String name,Class<?>... types){
        try {
            return clz.getDeclaredMethod(name, types);
        }catch (Exception e){
            return null;
        }
    }

    public static String getValue(String number, VariableCompileStream stream){
        Language language = stream.getLanguage();
        if(number.matches(WenYanLib.LENGTH())){
            //其餘
            if(number.endsWith("其餘")){
                return LanguageUtils.slice(language,stream.getName(number.substring(0,number.lastIndexOf("之")),false));
            }
            return LanguageUtils.size(language,stream.getName(number.substring(0,number.lastIndexOf("之")),false));
        }
        if(Utils.getString(WenYanLib.GET(),number)!=null)return stream.getArray(number,stream);
        if(number.equals("其"))return stream.getNowName();
        if(number.equals(WenYanLib.FALSE())||number.equals(WenYanLib.TRUE())){
            return language.getSyntax(WenYanLib.bool().get(number).get());
        }
        if(number.startsWith(WenYanLib.STRING_START())&&number.endsWith(WenYanLib.STRING_END())){
            return stream.getString(number);
        }else if(number.startsWith(WenYanLib.NAME_START())&&number.endsWith(WenYanLib.NAME_END())){
            return stream.getName(number,false);
        }else {
            return stream.getNumber(number)+"";
        }
    }

    public static boolean isName(String number){
        if(number.startsWith(WenYanLib.NAME_START())&&number.endsWith(WenYanLib.NAME_END()))return true;
        return false;
    }

    @Deprecated
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
