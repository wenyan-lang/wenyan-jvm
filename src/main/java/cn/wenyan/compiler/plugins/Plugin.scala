package cn.wenyan.compiler.plugins

import java.util.regex.Pattern

import cn.wenyan.compiler.streams.CompileStream

import scala.collection.mutable

import java.util

abstract class Plugin {

    def addSyntaxRegex(map : mutable.Map[String,String])

    def addPatterns(map : mutable.Map[String,Pattern])

    def addCompileStream(map : util.List[CompileStream])

    def addListener(map : util.List[Listener])

}
