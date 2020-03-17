package cn.wenyan.compiler.plugins

import java.util.regex.Pattern

import cn.wenyan.compiler.streams.CompileStream

import scala.collection.mutable
import java.util

import cn.wenyan.compiler.{Init, WenYanCompiler}

abstract class Plugin extends Init{

    def addSyntaxRegex(map : mutable.Map[String,String])

    def addPatterns(map : mutable.Map[String,Pattern])

    def addCompileStream(map : util.List[CompileStream])

    def addListener(map : util.List[Listener])

    //如果你需要拦截高优先级的语法，则可以在这里拦截，可以参见
    //LexerUtils的canMatch中的三个if判断
    //如语法有夫「x」之长和夫「x」，为了防止分割夫「x」之长，可以在匹配夫「x」时
    //后面有没有之，来组合上面的语法
    def onCanMatch(p : (String,String),index : Int,strings: String,patterns : util.Map[String,String]) : Boolean

}
