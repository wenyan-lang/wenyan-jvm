package cn.wenyan.compiler.utils;

import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.log.LogFormat;
import org.fusesource.jansi.Ansi;

import java.util.ArrayList;
import java.util.List;

public class SyntaxColor {


    public static String[] command = new String[]{
        "吾有","有","曰","名之","書之","數","言","爻","列","物",
            "昔之","者","今","是","疏","注","批","為是",
            "遍","云云","也","若","者","乃止","恆為是","若非",
            "加","減","乘","除","以","於","矣","所餘幾何","夫",
            "有陽","無陰","乎","中","凡","中之","一術","是術曰",
            "乃得","是謂","之術也","欲行是術","必先","乃行","施",
            "於","吾嘗觀","之書","方悟","之義"
    };
    public static String getSyntaxColor(String wenyan){
        for(String cmd : command){
            wenyan = wenyan.replace(cmd, LogFormat.textFormat(cmd,Ansi.Color.MAGENTA)+LogFormat.fg(Ansi.Color.DEFAULT));
        }
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
