package cn.wenyan.compiler;

public interface LanguageCompiler {

    Class<?> compile(String script);

    Class<?> compile(String script, String className);

}
