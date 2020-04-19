package cn.wenyan.compiler.utils

import groovy.transform.CompileStatic;

@CompileStatic
class GroovyPrettyCode implements PrettyCode {

    @Override
    String pretty(String string) {
        StringBuilder builder = new StringBuilder()
        int close = 0 //检验是否在字符串里
        //字符串的数量%2 == 0
        List l = GroovyUtils.splitGroovyCode(string,"\n")
        for(String code in l){
            int clo = getClose(code)
            if(clo < 0){
                close += clo
            }
            builder.append("\t"*close).append(code).append("\n")
            if(clo > 0){
                close += clo
            }
        }
        return builder
    }

    private static int getClose(String code){
        int close = 0
        int string = 0
        for(s in code){
            if(s == "'"||s == "\"") {
                string ++
            }
            if(string %2 == 0){
                if(s == "{"){
                    close ++
                }
                if(s == "}"){
                    close --
                }
            }
        }
        close
    }
}
