package cn.wenyan.compiler.script.libs

import cn.wenyan.compiler.GroovyCompiler
import cn.wenyan.compiler.LanguageCompiler
import cn.wenyan.compiler.lib.ArrayUtils
import cn.wenyan.compiler.lib.MathUtil
import cn.wenyan.compiler.utils.GroovyPrettyCode
import cn.wenyan.compiler.utils.PrettyCode
import groovy.transform.CompileStatic

@CompileStatic
enum Language {

    GROOVY(
            [
                    (Syntax.VAR_DEFINE)           : "def $NAME = $VALUE",
                    (Syntax.FOR_NUMBER)            : "for(_ans$INDEX in 1..$RANGE){",
                    (Syntax.COMMENT)               : "/*$COMMENT*/",
                    (Syntax.FOR_EACH)              : "for($ELEMENT in $ARRAY){",
                    (Syntax.FOR_END)               : "}",
                    (Syntax.IF)                    : "if($BOOL){",
                    (Syntax.IF_END)               : "}",
                    (Syntax.IF_BREAK)              : "if($BOOL)break",
                    (Syntax.WHILE_TRUE)            : "while(true){",
                    (Syntax.ELSE)                  : "}else{",
                    (Syntax.BREAK)                 : "break",
                    (Syntax.RETURN)                : "return $VALUE",
                    (Syntax.FUNCTION_END)          : "}",
                    (Syntax.DEFINE_FUNCTION)       : "def $NAME($ARGS){",//
                    (Syntax.FUNCTION_ARGS_SPLIT)   : ",",
                    (Syntax.IMPORT)                : "import $LIB",
                    (Syntax.IMPORT_STATIC)         : "import static $LIB.$METHOD",
                    (Syntax.IMPORT_STATIC_SEPARATE): "true",
                    (Syntax.IMPORT_SPLIT)          : "null",
                    (Syntax.MATH_ADD)              : "$NAME+$VALUE",
                    (Syntax.MATH_LESS)             : "$NAME-$VALUE",
                    (Syntax.MATH_MULTI)            : "$NAME*$VALUE",
                    (Syntax.MATH_EXCEPT)           : "$NAME/$VALUE",
                    (Syntax.MATH_REMAIN)           : "mod($NAME,$VALUE)",
                    (Syntax.BIGGER)                : ">",
                    (Syntax.SMALLER)               : "<",
                    (Syntax.EQUAL)                 : "==",
                    (Syntax.AND)                  : "&&",
                    (Syntax.OR)                    : "||",
                    (Syntax.PRINT)                 : "println($VALUE .toString())",
                    (Syntax.NEGATE)                : "!",
                    (Syntax.CHANGE)                : "$NAME = $VALUE",
                    (Syntax.REPLACE_ARRAY)         : "$NAME[getIndex($INDEX)] = $VALUE",
                    (Syntax.STRING)                : "\"\"\"",
                    (Syntax.ARRAY_ADD)             : NAME+".add($VALUE)",
                    (Syntax.INNER_FUNCTION)        : "def $NAME \n $NAME = {\n $ARGS->",
                    (Syntax.INNER_FUNCTION_NO_ARGS): "def $NAME \n $NAME = {\n ",
                    (Syntax.TRUE)                  : "true",
                    (Syntax.FALSE)                 : "false",
                    (Syntax.NOT_BIG_THAN)          : "<=",
                    (Syntax.NOT_SMALL_THAN)        : ">=",
                    (Syntax.NEGATE_EQUAL)          : "!=",
                    (Syntax.SLICE)                 : "ArrayUtils.slice(getArray($NAME))",
                    (Syntax.SIZE)                  : "ArrayUtils.length(getArray($NAME))",
                    (Syntax.RUN_FUNCTION)          : "def $VALUE = $NAME($ARGS)",
                    (Syntax.OBJECT_INNER)          : ".",
                    (Syntax.NUMBER_SUGAR)          : "$NAME",
                    (Syntax.STRING_APPEND)         : "+",
                    (Syntax.ARRAY_GET)             : "getArray($NAME)[getIndex($INDEX)]",
                    (Syntax.INT_TYPE)              : "BigDecimal",
                    (Syntax.STRING_TYPE)           : "String",
                    (Syntax.ARRAY_TYPE)            : "JSArray",
                    (Syntax.BOOL_TYPE)             : "boolean",
                    (Syntax.VAR_TYPE)              : "def",
                    (Syntax.DOUBLE_TYPE)           : "double",
                    (Syntax.FUNCTION_ARG_DEFINE)   : "$TYPE $NAME",
                    (Syntax.NULL)                  : "null",
                    (Syntax.DEFINE_ARRAY)          : "new JSArray()",
                    (Syntax.DEFINE_INT)            : "0",
                    (Syntax.DEFINE_STRING)         : "''",
                    (Syntax.IMPORT_WITH)           : ("import cn.wenyan.compiler.lib.*\nimport static "+ArrayUtils.name+".getArray\nimport static "+ArrayUtils.name+".getIndex\nimport static "+ MathUtil.name+".mod"),
                    (Syntax.NOT)                   : "!",
                    (Syntax.REQUIRE_SCRIPT)        : "",
                    (Syntax.CONTINUE)              : "continue",
                    (Syntax.ELSE_IF)               : "}else ",
                    (Syntax.CONCAT)                : (NAME+".putAll($VALUE)"),
                    (Syntax.TRY)                   : "try{",
                    (Syntax.THROW)                 : ("Exception $NAME = new Exception($EXCEPTION)\nthrow $NAME"),
                    (Syntax.CATCH)                 : "}catch($NAME){",
                    (Syntax.EXCEPTION_IF)          : (NAME+".message.equals($EXCEPTION)"),
                    (Syntax.CATCH_END)             : "}",
                    (Syntax.SHELL_VAR)             : "$NAME = $VALUE",
                    (Syntax.DEFINE_OBJECT)         : "[:]",
                    (Syntax.DELETE)                : (NAME+".remove($INDEX)"),
                    (Syntax.OBJECT_TYPE)           : ("Map "),
                    (Syntax.DEFINE)                : "def $NAME",
                    (Syntax.GIVE_FUNCTION)         : "$NAME = {\n $ARGS->",
                    (Syntax.DEFINE_GIVE_FUNCTION)  : "def $NAME = {\n $ARGS->"
            ],new GroovyCompiler(),new GroovyPrettyCode()
    );

    static final String EXCEPTION ="%{exception}%"

    static final String TYPE = "%{type}%"

    static final String NAME = "%{name}%"

    static final String VALUE = "%{value}%"

    static final String INDEX = "%{index}%"

    static final String RANGE = "%{range}%"

    static final String COMMENT = "%{comment}%"

    static final String ELEMENT = "%{element}%"

    static final String ARRAY = "%{array}%"

    static final String BOOL = "%{bool}%"

    static final String ARGS = "%{args}%"

    static final String LIB = "%{lib}%"

    static final String METHOD = "%{method}%"


    protected Map<Syntax,String> syntaxLib

    protected LanguageCompiler compiler

    protected PrettyCode pretty

    Language(Map syntaxLib,LanguageCompiler compiler,PrettyCode pretty){
        this.syntaxLib = syntaxLib
        this.compiler = compiler
        this.pretty = pretty
    }

    String getSyntax(Syntax property){
        return syntaxLib[property]
    }

    LanguageCompiler languageCompiler(){
        return compiler
    }

    PrettyCode getPretty() {
        return pretty
    }
}
