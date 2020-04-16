package cn.wenyan.compiler.lib

import groovy.transform.CompileStatic

@CompileStatic
class MathUtil {

    static Number mod(BigDecimal decimal1,BigDecimal decimal2){
        if(decimal1.toString().contains(".")||decimal2.toString().contains(".")){
            return decimal1
        }else{
            return decimal1.toBigInteger()%(decimal2.toBigInteger())
        }
    }

}
