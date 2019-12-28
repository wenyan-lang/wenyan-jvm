package cn.wenyan;


import cn.wenyan.exceptions.SyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 若施文言之术，必先用其器。古人云: 君子生非异，善假于物。
 * 此器以爪哇之法，行虚拟机之道，亦可广传文言于天下者。
 * 君用[run]之洋文可走之。吾欲将其译为java者也。
 */
public class WenYanCompiler {

    private int varIndex = 0;

    // 据之以得上下文，而书之。
    private String now;

    //此为天地之造物者，乃于此乎。
    public WenYanCompiler(){

    }

    // 具之一句，而翻万里者也。
    public CompileResult compile(String wenyan) {
        now = wenyan;
        String[] wenyans = wenyan.split("。");
        if(wenyans[0].matches(WenYanLib.syntaxs().get(WenYanLib.DEFINE_VAR()).get())){
            Pattern typePattern = WenYanLib.patterns().get(WenYanLib.TYPE()).get();
            Matcher typeMacher = typePattern.matcher(wenyans[0]);
            char type = ' ';
            if(typeMacher.find()){
                type = typeMacher.group(0).charAt(0);
            }
            List<String> names = new ArrayList<String>();
            List<String> values = new ArrayList<String>();
            Pattern pattern = WenYanLib.patterns().get(WenYanLib.NUMBER()).get();
            Matcher matcher = pattern.matcher(wenyans[0]);
            Pattern pat = WenYanLib.patterns().get(WenYanLib.ALL()).get();
            if (matcher.find()){
                long number = getNumber(matcher.group(0));
                for(int i = 1;i<=number;i++){
                    if(wenyans.length == 1)break;
                    if(wenyans[i].matches(WenYanLib.syntaxs().get(WenYanLib.VAR_NAME()).get())){
                        Matcher mat = pat.matcher(wenyans[i]);
                        if(mat.find()){
                            String value = mat.group(0);
                            values.add(value.substring(value.indexOf("曰")+1));
                        }
                    }
                }

                for(int z = (int)number+1;z<wenyans.length;z++){
                    if(wenyans[z].matches(WenYanLib.syntaxs().get(WenYanLib.VAR_VALUE()).get())){
                        Pattern namePatt = WenYanLib.patterns().get(WenYanLib.VAR_GET_NAME()).get();
                        Matcher matcher1 = namePatt.matcher(wenyans[z]);
                        int j = 0;
                        while (matcher1.find()) {
                            String name = matcher1.group(j);
                            names.add(name.substring(name.indexOf("「") + 1, name.lastIndexOf("」")));
                            j++;
                        }
                    }
                }

            }

            return new CompileResult(true,appendVar(names,values,type));
        }
        return new CompileResult(false,wenyan);
    }

    //TODO
    private String appendVar(List<String> name,List<String> values,char type){
        StringBuilder head = new StringBuilder("def ");
        switch (type){
            case '數':
                if(name.size() == 0)throw new SyntaxException("此地无造物者");
                if(name.size()!=values.size())throw new SyntaxException("君有"+name.size()+"之变量,而吾得"+values.size()+"也，嗟乎");
                for(int i = 0;i<name.size();i++){
                    String def = name.get(i) + "=" + getNumber(values.get(i));
                    if(name.size() == 1||i == name.size()-1) {
                        head.append(def);
                    }else {
                        head.append(def+",");
                    }
                }
                return head.toString();
            case '言':
                return "";
            case '爻':
                return "";
            case '列':
                return "";
            case '物':
                return "";
            default:
                throw new SyntaxException("此'"+type+"'为何物邪?");
        }
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

}
