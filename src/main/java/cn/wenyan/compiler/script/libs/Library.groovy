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

    String get(String name){
        return getLibs().get(name)
    }
}