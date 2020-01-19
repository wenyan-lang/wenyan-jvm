package cn.wenyan.compiler.utils;

import cn.wenyan.compiler.script.libs.Language;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.streams.VariableCompileStream;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import com.github.houbb.opencc4j.util.ZhConverterUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static List<String> getStrings(String patternId, String... things) {
        List<String> matchers = new ArrayList<>();
        Pattern typePattern = WenYanLib.patterns().get(patternId).get();
        for (String thing : things) {
            Matcher typeMatcher = typePattern.matcher(thing);
            while (typeMatcher.find()) {
                matchers.add(typeMatcher.group(0));
            }
        }
        return matchers;
    }

    public static String getString(String patternId, String thing) {
        List<String> strs = getStrings(patternId, thing);
        if (strs.size() == 0) return null;
        return strs.get(0);
    }

    @Deprecated
    public static void inputWenyan(WenYanCompilerImpl compiler, int wenyanIndex) {
        compiler.getNowCompiling().add(wenyanIndex);
    }


    public static String getStringFrom(String patternId, String thing, String start) {
        String value = getString(patternId, thing);
        return value.substring(value.indexOf(start) + 1);
    }

    public static String getStringFrom(String patternId, String thing, String start, String end) {
        String value = getString(patternId, thing);
        if (patternId.equals(WenYanLib.STRING()) || patternId.equals(WenYanLib.COMMENT())) {
            if (value == null) {
                value = getString(WenYanLib.ONLY_STRING(), thing);
            }
        }
        return value.substring(value.indexOf(start) + start.length(), value.lastIndexOf(end));
    }

    public static boolean matches(String thing, String patternId) {
        return thing.matches(WenYanLib.syntaxs().get(patternId).get());
    }

    public static boolean matches(List<String> wenyan, String patternId) {
        if (wenyan.size() == 0) return false;
        return matches(wenyan.get(0), patternId);
    }

    public static String getWenyanFromArray(String[] wenyans) {
        StringBuilder builder = new StringBuilder();
        for (String wenyan : wenyans) {
            builder.append(wenyan).append("。");
        }
        return builder.toString();
    }

    public static String  getValue(String number, VariableCompileStream stream){
        return getValue(number,stream,false);
    }

    public static int indexOf(String str,char s){
        char[] arr = str.toCharArray();
        int stringCount = 0;
        int ind = 0;
        for(char x : arr){
            if(x == '「'){
                stringCount++;
            }
            if(x == '」'){
                stringCount--;
            }
            if(stringCount==0){
                if(x == '之'){
                    break;
                }
            }
            ind++;
        }
        return ind;
    }
    public static String getValue(String number, VariableCompileStream stream,boolean setNow) {
        Language language = stream.getLanguage();
        if (number.equals("其然")) {
            return stream.getNowName();
        }
        if (number.equals("其不然")) {
            return language.getSyntax(Syntax.NOT) + stream.getNowName();
        }
        if (number.equals("空無")) {
            return language.getSyntax(Syntax.NULL);
        }
        if (number.equals("矣")) {
            return stream.getNowName() == null ? "" : stream.getNowName();
        }

        if (number.matches(WenYanLib.LENGTH())) {
            //其餘
            if (number.endsWith("其餘")) {
                return LanguageUtils.slice(language, stream.getName(number.substring(0, number.lastIndexOf("之")), false));
            }
            return LanguageUtils.size(language, stream.getName(number.substring(0, number.lastIndexOf("之")), false));
        }
        if (Utils.getString(WenYanLib.GET(), number) != null) return stream.getArray(number, stream);
        if (number.equals("其")) {
            return stream.getNowName() == null ? language.getSyntax(Syntax.NULL) : stream.getNowName();
        }
        if (number.equals(WenYanLib.FALSE()) || number.equals(WenYanLib.TRUE())) {
            return language.getSyntax(WenYanLib.bool().get(number).get());
        }
        if (number.startsWith(WenYanLib.STRING_START()) && number.endsWith(WenYanLib.STRING_END())) {
            return stream.getString(number);
        } else if (number.startsWith(WenYanLib.NAME_START()) && number.endsWith(WenYanLib.NAME_END())) {
            return stream.getName(number, false,setNow);
        } else {
            return stream.getNumberString(number) + "";
        }
    }

    public static String getClassName(String name) {
        return name.substring(0, name.lastIndexOf(".")).replace(File.separator, ".");
    }

    public static String getTraditionalChinese(String wenyan) {
        return ZhConverterUtil.convertToTraditional(wenyan);
    }

    public static boolean classExists(String clz) {
        try {
            Class.forName(clz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static int getClose(String returned) {
        char[] chars = returned.toCharArray();
        int index = 0;
        for (char x : chars) {
            if (x == '{') index++;
            if (x == '}') index--;
        }
        return index;
    }


}
