package cn.wenyan.compiler.utils

import cn.wenyan.compiler.WenYanLib

class GroovyUtils {

    static BigDecimal getNumber(String wenyanNumber){
        int isNot = 1
        BigDecimal maxNumber = 0
        BigDecimal result = 0
        def numbers = WenYanLib.numbers()
        def prefixs = WenYanLib.prefixs()
        if(!ScalaUtils.containsCommonNumber(wenyanNumber)){
            int len = wenyanNumber.length()-1
            if(wenyanNumber.contains("又")){
                len = wenyanNumber.split("又")[0].length() -1
            }
            boolean doubleNumber = false
            char[] numberChar = wenyanNumber.toCharArray()
            for(char str : numberChar){
                if(str == '又'){
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

}
