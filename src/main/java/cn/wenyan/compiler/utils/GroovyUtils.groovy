package cn.wenyan.compiler.utils

import cn.wenyan.compiler.WenYanLib

class GroovyUtils {

    static String formatString(String value){
        StringBuilder builder = new StringBuilder()
        for(int i = 0;i<value.length();i++){
            if(!(value[i] == "\\"&&i+1<=value.length()-1&&value[i+1]!= "\\")){
                builder.append(value[i])
            }
        }
        return builder.toString()
    }

    static List<String> splitGroovyCode(String code,char chars){
        int index = 0
        def arr = []
        StringBuilder builder = new StringBuilder()
        boolean start = false
        for(int i = 0;i<code.length();i++){
            if(code[i].equals("\"")) {
                if (!start) {
                    index++
                    start = true
                } else {
                    index--
                    start = false
                }
            }
            if(index == 0){
                if(code[i] == chars.toString()){
                    arr.add(builder.toString())
                    builder = new StringBuilder()
                }else{
                    builder.append(code[i])
                }
            }else{
                builder.append(code[i])
            }
        }
        if(!builder.toString().isEmpty())arr.add(builder.toString())
        arr
    }

    static BigDecimal getNumber(String wenyanNumber){
        int isNot = 1
        BigDecimal maxNumber = 0
        BigDecimal result = 0
        def numbers = WenYanLib.numbers()
        def prefixs = WenYanLib.prefixs()
        if(!ScalaUtils.containsCommonNumber(wenyanNumber)){
            int len = wenyanNumber.length()-1
            if(wenyanNumber.contains("·")){
                len = wenyanNumber.split("·")[0].length() -1
            }
            boolean doubleNumber = false
            char[] numberChar = wenyanNumber.toCharArray()
            for(char str : numberChar){
                if(str == '·'){
                    doubleNumber = true
                    len = 1
                    continue
                }
                if(str == '負'){
                    isNot = -1
                    continue
                }
                if(!doubleNumber){
                    result += numbers.get(str).get() * new BigDecimal(10).pow(len)
                    len --
                }else{
                    result += numbers.get(str).get() * 0.1.pow(len)
                    len++
                }
            }
            return isNot * result
        }

        if(wenyanNumber.startsWith("負")&&startWith(wenyanNumber.substring(1))){
            wenyanNumber = "負一"+wenyanNumber.substring(1)
        }else if(startWith(wenyanNumber)){
            wenyanNumber = "一"+wenyanNumber
        }
        char[] chars = wenyanNumber.toCharArray()
        //bug
        for(int i = 0;i<chars.length;i++){
            if(chars[i].toString() == "又"){
                continue
            }
            if(chars[i].toString() == "負"){
                isNot = -1
                continue
            }
            if(i+1<=chars.length-1){
                if(prefixs.contains(chars[i+1])){
                    BigDecimal max = prefixs.get(chars[i+1]).get()
                    if(maxNumber == 0||max < maxNumber) {
                        result += max *
                                numbers.get(chars[i]).get()
                        if(maxNumber == 0)maxNumber = max
                    }else{
                        result += numbers.get(chars[i]).get()
                        result = result * max
                        maxNumber = max
                    }
                    i++
                }else{
                    result += numbers.get(chars[i]).get()
                }
            }else{
                result += numbers.get(chars[i]).get()
            }
        }
        return result * isNot
    }

    private static boolean startWith(String number){
        def list = WenYanLib.prefixs().keysIterator()
        while (list.hasNext()){
            if(number.startsWith(list.next().toString())){
                return true
            }
        }
        return false
    }

    static int getMax(String wenyan){
        BigDecimal max = 0
        int maxIndex = -1
        char[] chars = wenyan.toCharArray()
        for(int index =0;index<wenyan.length();index++){
            if(WenYanLib.prefixs().contains(chars[index])){
                def number = WenYanLib.prefixs().get(chars[index]).get()
                if(number>max){
                    max = number
                    maxIndex = index
                }
            }
        }
        if(max == 1000||max == 100||max == 10)return -1
        return maxIndex
    }

    static String replace(String before,String replaced,String after,List range){
        int close = 0
        StringBuilder afterBuilder = new StringBuilder()
        StringBuilder now = new StringBuilder()
        int afterStart = 0
        for(s in before){
            if(s == range[0]){
                close ++
            }
            if(s == range[1]){
                close --
            }
            now.append(s)
            if(s == replaced[afterStart]){
                afterStart++
            }else{
                afterStart = 0
                afterBuilder.append(now)
                now = new StringBuilder()
            }
            if(afterStart == replaced.size()){
                if(close == 0){
                    afterBuilder.append(after)
                }else{
                    afterBuilder.append(now)
                }
                afterStart = 0
                now = new StringBuilder()
            }
        }
        return afterBuilder
    }

    static String replaceWithOutString(String before,String replaced,String after){
        replace(before,replaced,after,["「","」"])
    }

}
