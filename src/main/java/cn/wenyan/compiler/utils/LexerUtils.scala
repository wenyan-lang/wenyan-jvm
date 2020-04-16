package cn.wenyan.compiler.utils

import java.util

import cn.wenyan.compiler.{WenYanCompilerImpl, WenYanLib}
import cn.wenyan.compiler.exceptions.SyntaxException


import scala.util.control.Breaks

/**
 * 实现断句
 */

object LexerUtils {

    class Counter{
        var count = 0
        def add(): Unit = count += 1
        def cut(): Unit = count -= 1
    }

    val loop = Breaks

    def getWenYan(string: String,compiler : WenYanCompilerImpl) : String ={
        val builder = new StringBuilder
        wenYanLexer(string,compiler).stream().forEach(
            x=>if(!x.equals(""))builder.append(x).append("。")
        )
        builder.toString()
    }

    def getLine(string: util.List[String]) : String ={
        var index =0
        val builder = new StringBuilder("\n")
        string.stream().forEach(
            x=> {
                if(!x.equals("")){
                    builder.append(index).append(": ").append(x).append("\n")
                    index+=1
                }
            }
        )
        builder.toString()
    }

    def newStringToCommon(string : String): String ={

        var i = 0
        var count = 0
        var builder = new StringBuilder
        while (i<string.length){
            var s = string(i)
            //
            if(s == '「'||s == '『')count+=1
            if(s == '」'||s == '』')count-=1
            if(count == 1){
                if(s == '『'){
                    builder.append("「「")
                }else{
                    builder.append(s)
                }
            }else if(count == 0){
                if(s == '』'){
                    builder.append("」」")
                }else{
                    builder.append(s)
                }
            }else{
                builder.append(s)
            }
            i+=1
        }

        builder.toString()
    }

    def wenYanLexer(strings: String,compiler : WenYanCompilerImpl) : java.util.List[String] ={
        val string = trimWenYanX(newStringToCommon(strings.trim))
        val list = new util.ArrayList[String]()
        var builder = new StringBuilder
        var index = 0
        var count = 0
        while(index < string.length){
            loop.breakable{

                var isAppend = false
                val s = string(index)

                if(s == '「'){
                    count += 1
                    //拼接字符串
                    builder.append(s)
                    while (count != 0){
                        index+=1
                        val s = string(index)
                        if(s == '「') count += 1
                        if(s == '」') count -= 1
                        builder.append(s)
                    }
                    isAppend = true
                }


                if(s.toString.matches(WenYanLib.patterns(WenYanLib.NEW_COMMENT).toString)){
                    var start = 0
                    var started = false
                    var builder01 = new StringBuilder
                    loop.breakable{
                        while (true){
                            if(string(index) == '「'){
                                if(!started)started = true
                                start += 1
                            }
                            if(string(index) == '」'){
                                start -= 1
                            }
                            builder01.append(string(index))
                            if(!started){
                                if(string(index) == '曰'){
                                    list.add(builder01.toString())
                                    builder01 = new StringBuilder
                                    if(string(index+1).toString.matches(WenYanLib.SPLIT)){
                                        index+=1
                                    }
                                }
                            }
                            if(started){
                                if(start == 0){
                                    list.add(builder01.toString())
                                    builder = new StringBuilder
                                    loop.break()
                                }
                            }
                            index+=1
                        }
                    }
                    isAppend = true
                }
                if(isNumber(s)){
                    builder.append(s)
                    if(index+1<string.length){
                        while (index+1<string.length&&isNumber(string(index+1))){
                            index+=1
                            builder.append(string(index))
                        }
                    }
                    isAppend = true
                }

                if(s.toString.matches(WenYanLib.SPLIT)){
                    if(builder.nonEmpty){
                        list.add(builder.toString())
                    }
                    builder = new StringBuilder
                    loop.break
                }

                if(!isAppend)builder.append(s)

                if(canMatch(builder.toString(),strings,index,compiler)){
                    list.add(builder.toString())
                    builder = new StringBuilder
                }

            }
            index+=1
        }
        if(builder.nonEmpty){
            throw new SyntaxException("INDEX: "+builder.size+" Error: "+builder.toString())
        }
        list
    }

    private def isNumber(s:Char):Boolean = s.toString.matches(WenYanLib.numbersGet)

    private def canMatch(target: String,strings : String,index : Int,wenYanCompilerImpl: WenYanCompilerImpl): Boolean ={
        val patterns = WenYanLib.syntaxs
        for(p <- patterns){
            if(target.matches(p._2)){
                if(!SpecialSyntaxLexer.matches(p,index,strings,patterns)){
                    return false
                }
                //对于夫的特殊处理
                if(!wenYanCompilerImpl.callPluginOnMatch(p,index,strings,WenYanLib.mapSyntaxs)){
                    return false
                }
                return true
            }
        }
        false
    }

    def getUtilsMap(map : scala.collection.mutable.Map[String,String]): util.Map[String,String] ={
        val hashMap = new util.HashMap[String,String]()
        map.foreach{
            x =>{
                hashMap.put(x._1,x._2)
            }
        }
        hashMap
    }




    def trimWenYanX(s: String):String = {
        val counter = new Counter
        s.trim.filter(x=>{
            var bool = true
            if (x == '「' || x == '『') {
                counter.add()
            }
            if (x == '」' || x == '』'){
                counter.cut()
            }

            if(counter.count == 0){
                if(x == '\t'||x == '\n'||x == ' '){
                    bool = false
                }
            }

            bool

        })
    }



}
