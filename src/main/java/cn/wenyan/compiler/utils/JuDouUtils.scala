package cn.wenyan.compiler.utils

import java.util

import cn.wenyan.compiler.WenYanLib

import scala.util.control.Breaks

/**
 * 实现断句
 */

object JuDouUtils {

    val loop = Breaks

    def getWenYan(string: String) : String ={
        val builder = new StringBuilder
        splitWenYan(string).stream().forEach(
            x=>if(!x.equals(""))builder.append(x).append("。")
        )
        builder.toString()
    }

    def getLine(string: Array[String]) : String ={
        var index =0
        val builder = new StringBuilder("\n")
        string.foreach(
            x=> {
                if(!x.equals("")){
                    builder.append(index).append(": ").append(x).append("\n")
                    index+=1
                }
            }
        )
        builder.toString()
    }

    def splitWenYan(strings: String) : java.util.List[String] ={
        val string = strings.replace(" ","")
        val list = new util.ArrayList[String]()
        var builder = new StringBuilder
        var index = 0
        println(strings)
        while(index < string.length){
            var isAppend = false
            val s = string(index)
            loop.breakable{

                if(s.toString.equals("{")){
                    builder.append("{")
                    while (!builder.endsWith("}}")){
                        index+=1
                        builder.append(string(index))
                    }
                    isAppend = true
                }


                if(s.toString.equals(WenYanLib.NAME_START)){
                    builder.append(WenYanLib.NAME_START)
                    while (!builder.endsWith(WenYanLib.NAME_END)){
                        index+=1
                        builder.append(string(index))
                    }
                    isAppend = true
                }

                if(isNumber(s)){
                    builder.append(s)
                    if(index+1<strings.length){
                        while (isNumber(string(index+1))){
                            index+=1
                            builder.append(string(index))
                        }
                    }
                    isAppend = true
                }


                if(s.toString.matches(WenYanLib.SPLIT)){
                    list.add(builder.toString())
                    builder = new StringBuilder
                    loop.break
                }

                if(!isAppend)builder.append(s)
                if(getString(builder.toString())){

                    list.add(builder.toString())
                    builder = new StringBuilder
                }
            }
            index+=1
        }
        list
    }

    private def isNumber(s:Char):Boolean = WenYanLib.numbers.contains(s)||WenYanLib.prefixs.contains(s);

    private def getString(target: String): Boolean ={
        val patterns = WenYanLib.syntaxs
        for(p <- patterns){
            if(target.matches(p._2)){
                return true
            }
        }
        false
    }

    var patterns = WenYanLib.patterns
    def splitComment(now : String): String ={
        var string = now
        var index = 0
        while(index < string.length){
            if(string(index).toString.matches(patterns(WenYanLib.NEW_COMMENT).toString)){
                var start = 0
                var started = false
                loop.breakable{
                    while (true){
                        if(string(index) == '「'){
                            if(!started)started = true
                            start += 1
                        }
                        if(string(index) == '」'){
                            start -= 1
                        }
                        if(started){
                            if(start == 0){
                                loop.break()
                            }
                        }
                        index+=1
                    }
                }
                //得到index
                val builder = new StringBuilder(string)
                if(!string(index+1).toString.matches(WenYanLib.SPLIT)){
                    builder.insert(index+1,"。")
                    string = builder.toString()
                }
            }
            index+=1
        }
        string
    }


}
