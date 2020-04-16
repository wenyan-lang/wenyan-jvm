package cn.wenyan.compiler.script.libs

import groovy.transform.CompileStatic

@CompileStatic
class Library {

    private Language language

    Library(Language language) {
        this.language = language
    }

    private final Map<Language,LinkedHashMap<String,String>> libs = [
            (Language.GROOVY) : [
                    "算經" : "cn.wenyan.lang.算經",
                    "位經" : "cn.wenyan.lang.位經",
                    "列經" : "cn.wenyan.lang.列經",
                    "物經" : "cn.wenyan.lang.物經",
                    "易經" : "cn.wenyan.lang.易經",
                    "格物" : "cn.wenyan.lang.格物",
                    "渾沌經":"cn.wenyan.lang.渾沌經",
                    "曆法":"cn.wenyan.lang.曆法",
                    "曆表":"cn.wenyan.lang.曆表",
                    "西曆法":"cn.wenyan.lang.西曆法",
                    "籌經":"cn.wenyan.lang.籌經",
                    "畫譜":"cn.wenyan.lang.畫譜"
            ]
    ]

    Map<String,String> getLibs(){
        return libs.get(language)
    }

    void addLib(String name,String packageName){
        libs.get(language).put(name,packageName)
    }

    String get(String name){
        String n = getLibs().get(name)
        return n == null?name:n
    }
}