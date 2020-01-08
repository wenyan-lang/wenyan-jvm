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

  final val STRING : String = "string"

  final val HASH : String = "hash"

  final val IF_START : String = "if_start"

  final val IF_END : String = "if_end"

  final val SPLIT : String = "[。！，、]"

  final val STRING_START : String = "「「"

  final val STRING_END : String = "」」"

  final val NEW_START : String = "『"

  final val NEW_END : String = "』"

  final val NAME_START : String = "「"

  final val NAME_END : String = "」"

  final val BIG_THAN : String = "大於"

  final val SMALL_THAN : String = "小於"

  final val EQUALS : String = "等於"

  final val NOT_EQUALS : String = "不"+EQUALS

  final val ELSE : String = "若非"

  final val NOT : String = "不"

  final val SPLIT_MATH : String = "split_math"

  final val NOT_BIG_THAN : String = NOT+BIG_THAN

  final val NOT_SMALL_THAN : String = NOT+SMALL_THAN

  final val VAL_DEF : String= NAME_START+"[^(「|」)]+"+NAME_END

  final val VAR_NAME_FOR : String = "var_name_for"

  final val FOR_EACH : String = "for_each"

  final val MOD : String = "mod"

  final val FOR : String = "for"

  final val FOR_END : String = "for_end"

  final val BREAK : String = "break"

  final val IF_BREAK : String = "if_break" //顺应语法糖

  final val MATH_START : String = "math_start"

  final val MATH_APPEND : String = "math_append"

  final val WHILE : String = "while"

  final val AND_OR : String = "and_or"

  final val IT_CHANGE : String = "今其是矣"

  final val TRUE : String = "陽"

  final val FALSE : String = "陰"

  final val FUNCTION : String="function"

  final val NO_ARGS : String = "no_args"

  final val RETURN : String = "return"

  final val FUNCTION_END : String = "function_end"

  final val ARGS : String = "args"

  final val MUST : String = "must"

  final val DEFINE_ARG : String = "define_arg"

  final val DEFINE_END : String = "define_end"

  final val RUN_FUNCTION : String = "run_function"

  final val ARGS_RUN : String = "run_function_args"

  final val IMPORT : String = "import"

  final val IMPORT_STATIC : String = "import_static"

  final val HASH_NAME : String = "hash_name"

  final val YI : String = "yi"

  final val ADD : String = "add"

  final val VAL : String = "value"

  final val ONLY_STRING : String = "only_string"

  final val VAL_1 : String = "val_1"

  final val GET : String = "get"

  final val REPLACE_ARRAY : String = "replace_arr"

  final val NEW_COMMENT : String = "new_comment"

  final val OTHER : String = "!"

  final val LENGTH : String = "「[\\s\\S]+」之(長|其餘)"

  val prefixs = Map[Char,Int](
    '十' -> 10,
    '百' -> 100,
    '千' -> 1000,
    '萬' -> 10000,
    '億' -> 100000000,
    '廿' -> 20,
    '卅' -> 30,
    '卌' -> 40,
    '皕' -> 200,
  )
  val numbers = Map[Char,Int](
    '〇' -> 0,
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
    DEFINE_VAR -> "(吾有|今有)",
    VAR_NAME -> "曰[\\s\\S]+",
    VAR_VALUE -> ("([以]|)名之(曰"+VAL_DEF+")+"),
    VAR_GET_NAME -> ("曰"+VAL_DEF),
    WRITE -> "書之",
    SIMPLE_VAR -> "有[數言爻列物][\\s\\S]+",
    CHANGE -> ("昔之"+VAL_DEF+"者"),
    AFTER_NAME -> ("今("+VAL_DEF+"|[一二三四五六七八九十百千萬億零〇]+|「「[\\s\\S]+」」|\\{\\{[0-9]+HASH~\\}\\})(是|)"),
    COMMENT -> "[也矣]",
    FOR -> "為是([一二三四五六七八九十百千萬億零〇]+|「[\\s\\S]+」)遍",
    FOR_END -> "云云",
    IF_START -> "若[\\s\\S]+者",
    IF_END -> "[也]",
    BREAK -> "乃止",
    IF_BREAK -> "若[\\s\\S]+者乃止也",
    WHILE -> "恆為是",
    ELSE -> "若非",
    MATH_START -> "[加減乘除]([一二三四五六七八九十百千萬億零〇]+|「[\\s\\S]+」|其|「「[\\s\\S]+」」|\\{\\{[0-9]+HASH~\\}\\})",
    MOD -> "所餘幾何",
    IT_CHANGE -> IT_CHANGE,
    AND_OR -> "夫「[\\s\\S]+」「[\\s\\S]+」中(有陽|無陰)乎",
    FOR_EACH -> "凡「[\\s\\S]+」中之「[\\s\\S]+」",
    FUNCTION -> "一術",
    NO_ARGS -> "是術曰",
    RETURN -> "乃得[\\s\\S]+",
    FUNCTION_END -> "是謂「[\\s\\S]+」之術也",
    ARGS -> "欲行是術",
    MUST -> "必先得",
    DEFINE_ARG -> "[一二三四五六七八九十百千萬億零〇]+[數言爻列物]",
    DEFINE_END -> "乃行是術曰",
    RUN_FUNCTION -> "施「[\\s\\S]+」",
    ARGS_RUN -> "於(「[\\s\\S]+」|[\\s\\S]+)",
    IMPORT -> "吾嘗觀「「[\\s\\S]+」」之書",
    IMPORT_STATIC -> "方悟(「[\\s\\S]+」)+之義",
    YI -> "噫",
    ADD -> "充「[\\s\\S]+」",
    VAL -> "[以於](「[\\s\\S]+」|[一二三四五六七八九十百千萬億零〇]+|「「[\\s\\S]+」」|\\{\\{[0-9]+HASH~\\}\\})",
    GET -> "夫「[\\s\\S]+」之(「[\\s\\S]+」|[一二三四五六七八九十百千萬億零〇]+|「「[\\s\\S]+」」)",
    REPLACE_ARRAY -> ("昔之"+VAL_DEF+"之("+VAL_DEF+"|「「[\\s\\S]+」」)者"),
    NEW_COMMENT -> "[批注疏]曰",
    OTHER -> "變「[\\s\\S]+」",
    LENGTH -> ("夫"+LENGTH)
  )


  val patterns = Map[String,Pattern](
    NUMBER -> Pattern.compile("[一二三四五六七八九十]+"),
    ALL -> Pattern.compile("[\\s\\S]+"),
    TYPE -> Pattern.compile("[數言爻列物]"),
    VAR_GET_NAME -> Pattern.compile(syntaxs(VAR_GET_NAME)),
    BEFORE_NAME -> Pattern.compile(syntaxs(CHANGE)),
    AFTER_NAME -> Pattern.compile(syntaxs(AFTER_NAME)),
    COMMENT -> Pattern.compile("(「「|『)[^(「「|」」|『|』)]+(」」|』)"),
    STRING -> Pattern.compile("(「「|『)[^(「「|」」|『|』)]+(」」|』)"),
    HASH -> Pattern.compile("\\{\\{[0-9]+HASH~\\}\\}"),
    FOR -> Pattern.compile("[一二三四五六七八九十百十千萬億]+"),
    VAR_NAME_FOR -> Pattern.compile(VAL_DEF),
    SPLIT_MATH ->Pattern.compile("[以於]"),
    AND_OR -> Pattern.compile("(有陽|無陰)"),
    HASH_NAME -> Pattern.compile("「\\{\\{[0-9]+HASH~\\}\\}」"),
    ONLY_STRING -> Pattern.compile("(「「|『)[\\s\\S]+(」」|』)"),
    VAL -> Pattern.compile("(「\\s\\S」|[一二三四五六七八九十百千萬億零〇]+|「「[\\s\\S]+」」)"),
    GET -> Pattern.compile("「[\\s\\S]+」之(「[\\s\\S]+」|[一二三四五六七八九十百千萬億零〇]+|「「[\\s\\S]+」」)"),
    NEW_COMMENT -> Pattern.compile("[批注疏]")
  )


  val bool = Map[String,String](
    FALSE -> "false",
    TRUE -> "true",
    BIG_THAN -> ">",
    SMALL_THAN -> "<",
    NOT -> "=",
    NOT_BIG_THAN -> "<=",
    NOT_SMALL_THAN -> ">=",
    EQUALS -> "==",
    NOT_EQUALS -> "!="
  )

  val math = Map[Char,String](
    '加' -> "+",
    '減' -> "-",
    '乘' -> "*",
    '除' -> "/"
  )

  val define = Map[Char,String](
    '數' -> "0",
    '言' -> "''",
    '爻' -> "false",
    '列' -> "new JSArray()",
    '物' -> "null",
  )

}