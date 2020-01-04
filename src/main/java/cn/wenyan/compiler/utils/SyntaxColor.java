package cn.wenyan.compiler.utils;

import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.log.LogFormat;
import org.fusesource.jansi.Ansi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyntaxColor {


    public static String[] command = new String[]{
        "吾","有","曰","名","書","數","言","爻","列","物",
            "昔","者","今","是","疏","注","批","為",
            "遍","云","也","若","者","止","恆","若非",
            "加","減","乘","除","以","於","矣","所餘幾何","夫",
            "有","陽","無","陰","乎","中","凡","中","術","術",
            "得","謂","術","欲行是術","必先","行","施",
            "於","嘗觀","書","方悟","義","是","乃","之","欲"
    };
    public static String getSyntaxColor(String wenyan, WenYanCompilerImpl compiler){
        Map<String,String> stringMap = new HashMap<>();
        wenyan = compiler.wenYansToHASH(wenyan,stringMap);
        for(String cmd : command){
            wenyan = wenyan.replace(cmd, LogFormat.textFormat(cmd,Ansi.Color.MAGENTA)+LogFormat.fg(Ansi.Color.DEFAULT));
        }
        wenyan = compiler.replaceWenYan(wenyan,stringMap);
        List<String> stringList = Utils.getStrings(WenYanLib.FOR(),wenyan);
        for(String str : stringList){
            wenyan = wenyan.replaceAll(WenYanLib.patterns().get(WenYanLib.FOR()).get().toString(),LogFormat.textFormat(str,Ansi.Color.BLUE)+LogFormat.fg(Ansi.Color.DEFAULT));
        }
        stringList = Utils.getStrings(WenYanLib.STRING(),wenyan);
        for(String str : stringList){
            wenyan = wenyan.replaceAll(str,LogFormat.textFormat(str,Ansi.Color.GREEN)+LogFormat.fg(Ansi.Color.DEFAULT));
        }
        return wenyan;
    }
}
