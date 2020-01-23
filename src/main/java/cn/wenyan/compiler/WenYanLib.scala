package cn.wenyan.compiler

import java.util.regex.Pattern

import cn.wenyan.compiler.script.libs.Syntax


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

  final val CLEAR_STACK : String = "clear_stack"

  final val ADD : String = "add"

  final val VAL : String = "value"

  final val ONLY_STRING : String = "only_string"

  final val VAL_1 : String = "val_1"

  final val GET : String = "get"

  final val REPLACE_ARRAY : String = "replace_arr"

  final val NEW_COMMENT : String = "new_comment"

  final val OTHER : String = "!"

  final val LENGTH : String = "「[\\s\\S]+」之(長|其餘)"

  final val RETURN_ : String = "return_"

  final val CONTINUE : String = "continue"

  final val ELSE_IF : String = "else_if"

  final val CONCAT : String = "concat"

  final val MACRO_BEFORE : String = "macro_before"

  final val MACRO_AFTER : String = "macro_after"

  final val STATEMENT : String = "statement"

  final val FUNC_ARG : String = "func_arg"

  final val TRY : String = "try"

  final val EXCEPTION_DEFINE : String = "exception_define"

  final val EXCEPTION_THROW : String = "exception_throw"

  final val CATCH : String = "catch"

  final val EXCEPTION_IF : String= "exception_if"

  final val EXCEPTION_ELSE : String = "exception_else"

  final val OBJECT_IT : String = "object_it"

  final val CATCH_END : String = "catch_end"

  final val GIVE_OBJECT : String = "give_object"

  final val GIVE_OBJECT_VALUE : String = "give_object_value"

  final val OBJECT_END : String = "object_end"

  final val DELETE : String ="delete"


  val MMap = scala.collection.mutable.Map

  private type BigDecimal0 = java.math.BigDecimal

  val prefixAfter = MMap[Char,BigDecimal0](
    '負' -> new BigDecimal0(-1),
    '百' -> new BigDecimal0(100),
    '千' -> new BigDecimal0(1000),
    '萬' -> new BigDecimal0(10000),
    '億' -> new BigDecimal0(1E8),
    '兆' -> new BigDecimal0("1E12"),
    '京' -> new BigDecimal0("1E16"),
    '垓' -> new BigDecimal0("1E20"),
    '秭' -> new BigDecimal0("1E24"),
    '穣' -> new BigDecimal0("1E28"),
    '溝' -> new BigDecimal0("1E32"),
    '澗' -> new BigDecimal0("1E36"),
    '正' -> new BigDecimal0("1E40"),
    '載' -> new BigDecimal0("1E44"),
    '分' -> new BigDecimal0("1E-1"),
    '釐' -> new BigDecimal0("1E-2"),
    '毫' -> new BigDecimal0("1E-3"),
    '絲' -> new BigDecimal0("1E-4"),
    '忽' -> new BigDecimal0("1E-5"),
    '微' -> new BigDecimal0("1E-6"),
    '塵' -> new BigDecimal0("1E-7"),
    '埃' -> new BigDecimal0("1E-8"),
    '渺' -> new BigDecimal0("1E-9"),
    '漠' -> new BigDecimal0("1E-10")
  )
  val prefixs = MMap[Char,BigDecimal0](
    '廿' -> new BigDecimal0(20),
    '卅' -> new BigDecimal0(30),
    '卌' -> new BigDecimal0(40),
    '皕' -> new BigDecimal0(200),
    '十' -> new BigDecimal0(10),
  ).addAll(prefixAfter)


  //二分三釐八毫八絲三忽八微

  val numbers = Map[Char,BigDecimal0](
    '〇' -> new BigDecimal0(0),
    '零' -> new BigDecimal0(0),
    '一' -> new BigDecimal0(1),
    '二' -> new BigDecimal0(2),
    '两' -> new BigDecimal0(2),
    '三' -> new BigDecimal0(3),
    '四' -> new BigDecimal0(4),
    '五' -> new BigDecimal0(5),
    '六' -> new BigDecimal0(6),
    '七' -> new BigDecimal0(7),
    '八' -> new BigDecimal0(8),
    '九' -> new BigDecimal0(9),
  )


  def getNumber(): String ={
    val stringBuilder = new StringBuilder("[")
    for((k,v)<-numbers){
      stringBuilder.append(k)
    }
    for((k,v)<-prefixs){
      stringBuilder.append(k)
    }
    stringBuilder.append("又")
    stringBuilder.append("·")
    stringBuilder.append("]+")
    stringBuilder.toString()
  }

  val numbersGet = getNumber()


  private val value = s"$VAL_DEF|$numbersGet|「「([\\s\\S]+|)」」|空無|其|陽|陰|其然|其不然|矣"
  private val myType = "[數言爻列物元]"
  private val comment = "[批注疏]"
  private val ONE_CHAR = "[\\s\\S]"
  private val ALL_CHARACTERS = s"$ONE_CHAR+"


  final val VALUE : String = "value"
  val syntaxs = MMap[String,String](
    DEFINE_VAR -> "(吾有|今有)",//
    VAR_NAME -> s"曰$ALL_CHARACTERS",//
    VAR_VALUE -> s"([以]|)名之(曰$VAL_DEF)+",//
    VAR_GET_NAME -> s"曰$VAL_DEF",//
    WRITE -> "書之",//
    SIMPLE_VAR -> s"有$myType$ALL_CHARACTERS",//
    CHANGE -> (s"昔之$VAL_DEF"+"者"),//
    AFTER_NAME -> s"今($value)(是|)",////
    COMMENT -> "(是也|是矣)",//
    FOR -> s"為是($numbersGet|「$ALL_CHARACTERS」)遍",//
    FOR_END -> "云云",//
    IF_START -> (s"若$ALL_CHARACTERS"+"者"),//
    IF_END -> "(也|终也)",//
    BREAK -> "乃止",//
    IF_BREAK -> (s"若$ALL_CHARACTERS"+"者乃止也"),//
    WHILE -> "恆為是",//
    ELSE -> "若非",//
    MATH_START -> s"[加減乘除]($value)",////
    MOD -> "所餘幾何",//
    IT_CHANGE -> IT_CHANGE,//
    AND_OR -> s"夫「$ALL_CHARACTERS」「$ALL_CHARACTERS」中(有陽|無陰)乎",//TODO
    FOR_EACH -> s"凡「$ALL_CHARACTERS」中之「$ALL_CHARACTERS」",//
    FUNCTION -> (numbersGet+"術"),//
    NO_ARGS -> "是術曰",//
    RETURN -> s"乃得($value)",//
    FUNCTION_END -> s"是謂「$ALL_CHARACTERS」之術[也矣]",//
    ARGS -> "欲行是術",//
    MUST -> "必先得",//
    DEFINE_ARG -> s"$numbersGet$myType",//
    DEFINE_END -> "乃行是術曰",//
    RUN_FUNCTION -> s"施「$ALL_CHARACTERS」",//
    ARGS_RUN -> s"於(「$ALL_CHARACTERS」|$ALL_CHARACTERS)",//
    IMPORT -> s"吾嘗觀((「「$ALL_CHARACTERS」」中)+|)「「$ALL_CHARACTERS」」之書",//
    IMPORT_STATIC -> s"方悟(「$ALL_CHARACTERS」)+之義",//
    CLEAR_STACK -> "噫",
    ADD -> s"充($value)",////
    VAL -> s"[以於]($value)",////
    GET -> s"夫(「$ALL_CHARACTERS」|其)之($value)",
    REPLACE_ARRAY -> (s"昔之"+VAL_DEF+"之("+value+")者"),
    NEW_COMMENT -> (comment+"曰"),
    OTHER -> s"變(「$ALL_CHARACTERS」|$TRUE|$FALSE)",
    LENGTH -> s"夫$LENGTH",
    RETURN_ -> "乃歸空無",
    CONTINUE -> "乃止是遍",
    ELSE_IF -> "或",
    CONCAT -> s"銜「$ALL_CHARACTERS」",
    MACRO_BEFORE -> s"云「「$ALL_CHARACTERS」」",
    MACRO_AFTER -> s"蓋謂「「$ALL_CHARACTERS」」",
    STATEMENT -> s"夫($VAL_DEF|$numbersGet|陽|陰|空無|「「([\\s\\S]+|)」」|其)",
    FUNC_ARG -> (s"取$numbersGet"+"以"),
    TRY -> "姑妄行此",
    EXCEPTION_DEFINE -> "嗚呼",
    EXCEPTION_THROW -> s"「「$ALL_CHARACTERS」」之禍",
    CATCH -> "如事不諧",
    EXCEPTION_IF -> s"豈「「$ALL_CHARACTERS」」之禍歟",
    EXCEPTION_ELSE -> "不知何禍歟",
    CATCH_END -> "乃作罷",
    OBJECT_IT -> "其物如是",
    GIVE_OBJECT -> s"物之「「$ALL_CHARACTERS」」者",
    GIVE_OBJECT_VALUE -> (myType+"曰("+value+")"),
    OBJECT_END -> s"是謂「$ALL_CHARACTERS」之物也",
    DELETE -> "今不復存矣"
  )




  //乃歸空無
  val patterns = MMap[String,Pattern](
    NUMBER -> Pattern.compile(numbersGet),
    ALL -> Pattern.compile("[\\s\\S]+"),
    TYPE -> Pattern.compile(myType),
    VAR_GET_NAME -> Pattern.compile(syntaxs(VAR_GET_NAME)),
    BEFORE_NAME -> Pattern.compile(syntaxs(CHANGE)),
    AFTER_NAME -> Pattern.compile(syntaxs(AFTER_NAME)),
    COMMENT -> Pattern.compile("(「「|『)[^(「「|」」|『|』)]+(」」|』)"),
    STRING -> Pattern.compile("(「「|『)[^(「「|」」|『|』)]+(」」|』)"),
    HASH -> Pattern.compile("\\{\\{[0-9]+HASH~\\}\\}"),//淘汰
    FOR -> Pattern.compile(numbersGet),
    VAR_NAME_FOR -> Pattern.compile(VAL_DEF),
    SPLIT_MATH ->Pattern.compile("[以於]"),
    AND_OR -> Pattern.compile("(有陽|無陰)"),
    HASH_NAME -> Pattern.compile("「\\{\\{[0-9]+HASH~\\}\\}」"),//淘汰
    ONLY_STRING -> Pattern.compile("(「「|『)[\\s\\S]+(」」|』)"),
    VAL -> Pattern.compile(s"($value)"),
    GET -> Pattern.compile(s"「[\\s\\S]+」之($value)"),
    NEW_COMMENT -> Pattern.compile(comment),
    MACRO_BEFORE -> Pattern.compile(syntaxs(MACRO_BEFORE)),
    MACRO_AFTER -> Pattern.compile(syntaxs(MACRO_AFTER)),
    VALUE -> Pattern.compile(value)
  )




  val bool = MMap[String,Syntax]()

  registerBool()

  def registerBool(): Unit ={
    bool(FALSE)= Syntax.FALSE
    bool(TRUE) = Syntax.TRUE
    bool(BIG_THAN) = Syntax.BIGGER
    bool(SMALL_THAN) = Syntax.SMALLER
    bool(NOT) = Syntax.NEGATE
    bool(NOT_BIG_THAN) = Syntax.NOT_BIG_THAN
    bool(NOT_SMALL_THAN) = Syntax.NOT_SMALL_THAN
    bool(EQUALS) = Syntax.EQUAL
    bool(NOT_EQUALS) = Syntax.NEGATE_EQUAL
  }

  val math = MMap[Char,Syntax]()

  registerMath()

  def registerMath(): Unit ={
    math('加') = Syntax.MATH_ADD
    math('減') = Syntax.MATH_LESS
    math('乘') = Syntax.MATH_MULTI
    math('除') = Syntax.MATH_EXCEPT
  }

  val define = MMap[Char,Syntax]()

  addDefine()

  def addDefine(): Unit ={
    define('數') = Syntax.DEFINE_INT
    define('言') = Syntax.DEFINE_STRING
    define('爻') = Syntax.FALSE
    define('列') = Syntax.DEFINE_ARRAY
    define('物') = Syntax.DEFINE_OBJECT
    define('元') = Syntax.NULL
  }

  val types = Map[String,Syntax](
    "數" -> Syntax.INT_TYPE,
    "言" -> Syntax.STRING_TYPE,
    "爻" -> Syntax.BOOL_TYPE,
    "列" -> Syntax.ARRAY_TYPE,
    "元" -> Syntax.NULL,
    "物" -> Syntax.NULL
  )

}