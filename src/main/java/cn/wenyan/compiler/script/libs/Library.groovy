package cn.wenyan.compiler.script.libs

class Library {

    private Language language

    Library(language) {
        this.language = language
    }

    private final Map<Language,Map<String,String>> libs = [
            (Language.GROOVY) : [
                    "算經" : "cn.wenyan.lang.算經",
                    "位經" : "cn.wenyan.lang.位經",
                    "列經" : "cn.wenyan.lang.列經",
                    "物經" : "cn.wenyan.lang.物經",
                    "易經" : "cn.wenyan.lang.易經",
                    "格物" : "cn.wenyan.lang.格物",
                    "混沌經":"cn.wenyan.lang.混沌經"
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