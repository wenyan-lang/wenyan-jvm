package cn.wenyan.compiler.script.libs

import cn.wenyan.compiler.GroovyCompiler
import cn.wenyan.compiler.LanguageCompiler

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
                    (Syntax.DEFINE_FUNCTION)       : "def $NAME($ARGS){",
                    (Syntax.FUNCTION_ARGS_SPLIT)   : ",",
                    (Syntax.IMPORT)                : "import $LIB",
                    (Syntax.IMPORT_STATIC)         : "import static $LIB.$METHOD",
                    (Syntax.IMPORT_STATIC_SEPARATE): "true",
                    (Syntax.IMPORT_SPLIT)          : "null",
                    (Syntax.MATH_ADD)              : "+",
                    (Syntax.MATH_LESS)             : "-",
                    (Syntax.MATH_MULTI)            : "*",
                    (Syntax.MATH_EXCEPT)           : "/",
                    (Syntax.MATH_REMAIN)           : "%",
                    (Syntax.BIGGER)                : ">",
                    (Syntax.SMALLER)               : "<",
                    (Syntax.EQUAL)                 : "==",
                    (Syntax.AND)                  : "&&",
                    (Syntax.OR)                    : "||",
                    (Syntax.PRINT)                 : "println($VALUE)",
                    (Syntax.NEGATE)                : "!",
                    (Syntax.CHANGE)                : "$NAME = $VALUE",
                    (Syntax.REPLACE_ARRAY)         : "$NAME[$INDEX] = $VALUE",
                    (Syntax.STRING)                : "'",
                    (Syntax.ARRAY_ADD)             : NAME+".put(null,$VALUE)",
                    (Syntax.INNER_FUNCTION)        : "def $NAME = {\n $ARGS->",
                    (Syntax.INNER_FUNCTION_NO_ARGS): "def $NAME = {\n ",
                    (Syntax.TRUE)                  : "true",
                    (Syntax.FALSE)                 : "false",
                    (Syntax.NOT_BIG_THAN)          : "<=",
                    (Syntax.NOT_SMALL_THAN)        : ">=",
                    (Syntax.NEGATE_EQUAL)          : "!=",
                    (Syntax.SLICE)                 : NAME+".slice(1)",
                    (Syntax.SIZE)                  : NAME+".size()",
                    (Syntax.RUN_FUNCTION)          : "def $VALUE = $NAME($ARGS)",
                    (Syntax.OBJECT_INNER)          : ".",
                    (Syntax.NUMBER_SUGAR)          : "((Integer)$NAME)",
                    (Syntax.STRING_APPEND)         : "+",
                    (Syntax.ARRAY_GET)             : "("+NAME+".getClass() == HashMap.Node.class?"+NAME+".getValue():$NAME)[("+INDEX+".class == Integer.class?$INDEX-1:$INDEX)]",
            ],new GroovyCompiler()
    );

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

    Language(syntaxLib,compiler){
        this.syntaxLib = syntaxLib
        this.compiler = compiler
    }

    String getSyntax(Syntax property){
        return syntaxLib[property]
    }

    LanguageCompiler languageCompiler(){
        return compiler
    }
}
