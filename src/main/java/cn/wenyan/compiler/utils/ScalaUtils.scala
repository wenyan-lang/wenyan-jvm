package cn.wenyan.compiler.utils

import cn.wenyan.compiler.WenYanLib

object ScalaUtils {

  def containsCommonNumber(wenyan:String) : Boolean ={
    val numbers = WenYanLib.prefixs
    for((x,y)<-numbers){
      if(wenyan.contains(x))return true
    }
    false
  }
}
