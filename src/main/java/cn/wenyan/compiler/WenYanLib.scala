package cn.wenyan.compiler

import java.util.regex.Pattern

object WenYanLib {

  final val NUMBER : String = "number"

  final val ALL : String = "all"

  final val TYPE : String = "type"

  final val DEFINE_VAR : String = "define_var"

  final val VAR_NAME : String = "var_name"

  final val VAR_VALUE : String = "var_value"

  final val VAR_GET_NAME : String = "get_name"

  final val WRITE : String = "write"

  final val SIMPLE_VAR : String = "simple_var"

  final val CHANGE : String = "change"

  final val BEFORE_NAME : String = "before_name"

  final val AFTER_NAME : String = "after_name"

  final val COMMENT : String = "comment"

  final val TRUE = "陽"

  final val FALSE = "陰"


  val prefixs = Map[Char,Int](
    '十' -> 10,
    '百' -> 100,
    '千' -> 1000,
    '萬' -> 10000
  )
  val numbers = Map[Char,Int](
    '零' -> 0,
    '一' -> 1,
    '二' -> 2,
    '两' -> 2,
    '三' -> 3,
    '四' -> 4,
    '五' -> 5,
    '六' -> 6,
    '七' -> 7,
    '八' -> 8,
    '九' -> 9,
  )



  val syntaxs = Map[String,String](
    DEFINE_VAR -> "吾有[一二三四五六七八九十]+[數言爻列物]",
    VAR_NAME -> "曰[\\s\\S]+",
    VAR_VALUE -> "名之(曰「[\\s\\S]+」)+",
    VAR_GET_NAME -> "曰「[\\s\\S]+」",
    WRITE -> "書之",
    SIMPLE_VAR -> "有[數言爻列物][\\s\\S]+",
    CHANGE -> "昔之「[\\s\\S]+」者",
    AFTER_NAME -> "今「[\\s\\S]+」是也",
    COMMENT -> "[疏注批]曰"
  )


  val patterns = Map[String,Pattern](
    NUMBER -> Pattern.compile("[一二三四五六七八九十]+"),
    ALL -> Pattern.compile("[\\s\\S]+"),
    TYPE -> Pattern.compile("[數言爻列物]"),
    VAR_GET_NAME -> Pattern.compile(syntaxs(VAR_GET_NAME)),
    BEFORE_NAME -> Pattern.compile(syntaxs(CHANGE)),
    AFTER_NAME -> Pattern.compile(syntaxs(AFTER_NAME)),
    COMMENT -> Pattern.compile( "「「[\\s\\S]+」」")
  )


  val bool = Map[String,String](
    FALSE -> "false",
    TRUE -> "true"
  )

}
