package cn.wenyan.compiler.script.libs

class Library {

    private Language language

    Library(language) {
        this.language = language
    }

    private final Map<Language,Map<String,String>> libs = [
            (Language.GROOVY) : [
                    "算經" : "cn.wenyan.lang.算經"
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