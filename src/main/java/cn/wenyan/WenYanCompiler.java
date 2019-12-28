package cn.wenyan;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 若施文言之术，必先用其器。古人云: 君子生非异，善假于物。
 * 此器以爪哇之法，行虚拟机之道，亦可广传文言于天下者。
 * 君用[run]之洋文可走之。吾欲将其译为java者也。
 */
public class WenYanCompiler {

    // 据之以得上下文，而书之。
    private String now;

    //此为天地之造物者，乃于此乎。
    public WenYanCompiler(){

    }

    // 具之一句，而翻万里者也。
    public int compile(String wenyan) {
        now = wenyan;
        String[] wenyans = wenyan.split("。");
        List<String> names = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        if(wenyans[0].matches("吾有[一二三四五六七八九十]+[數言爻列]")){
            Pattern pattern = Pattern.compile("[一二三四五六七八九十]+");
            Matcher matcher = pattern.matcher(wenyans[0]);
            if (matcher.find()){
                long number = getNumber(matcher.group(0));
                for(int i = 1;i<number;i++){
                    if(wenyans[i].matches("曰[\\s\\S]+")){
                        Pattern pat = Pattern.compile("[\\s\\S]+");
                        Matcher mat = pat.matcher(wenyans[i]);
                        if(mat.find()){
                            names.add(mat.group(0));
                        }
                        
                    }
                }
            }
        }


        return 0;
    }

    private long getNumber(String wenyanNumber){
        int maxNumber = 0;
        long result = 0;
        char[] chars = wenyanNumber.toCharArray();
        for(int i = 0;i<chars.length;i++){
            if(i+1<=chars.length-1){
                if(WenYanLib.prefixs().contains(chars[i+1])){
                    int max = (Integer)WenYanLib.prefixs().get(chars[i+1]).get();
                    if(maxNumber == 0||max < maxNumber) {
                        result += max *
                                (Integer) WenYanLib.numbers().get(chars[i]).get();
                        if(maxNumber == 0)maxNumber = max;
                    }else{
                        result += (Integer) WenYanLib.numbers().get(chars[i]).get();
                        result = result * max;
                        maxNumber = max;
                    }
                    i++;
                }else{
                    result += (Integer)WenYanLib.numbers().get(chars[i]).get();
                }
            }else{
                result += (Integer)WenYanLib.numbers().get(chars[i]).get();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        WenYanCompiler compiler = new WenYanCompiler();
        compiler.compile("吾有一數。");
        System.out.println(compiler.getNumber("三萬两千一百一十五萬"));
    }


}
