package cn.wenyan.lang;

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.MathContext

class 算經 {

    static final CONTEXT = new MathContext(16)

    static BigDecimal 正弦(BigDecimal i){
        return BigDecimalMath.sin(i,CONTEXT)
    }

    static BigDecimal 餘弦(BigDecimal i){
        return BigDecimalMath.cos(i,CONTEXT)
    }

    static BigDecimal 絕對(BigDecimal i){
        return i>=0.0?i:-i
    }

    static BigDecimal 平方根(BigDecimal i){
        return BigDecimalMath.sqrt(i,CONTEXT)
    }

}
