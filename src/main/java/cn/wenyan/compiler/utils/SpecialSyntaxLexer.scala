package cn.wenyan.compiler.utils

import cn.wenyan.compiler.WenYanLib

object SpecialSyntaxLexer {


    def matches(p : (String,String),index : Int,strings: String,patterns : scala.collection.mutable.Map[String,String]) : Boolean={
        if(p._2.equals(patterns(WenYanLib.STATEMENT))){
            if(index+1<=strings.length-1&&strings(index+1) == '之')return false
        }
        if(p._2.equals(patterns(WenYanLib.GET))){
            if(index+1<=strings.length-1&&strings(index+1) == '餘')return false
        }
        if(p._2.equals(patterns(WenYanLib.AND_OR))){
            if(index+1<=strings.length-1&&strings(index+1) == '「')return false
        }
        true
    }
}
