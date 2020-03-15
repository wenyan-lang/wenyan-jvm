package cn.wenyan.compiler;


/**
 * 万事成功皆包容，狭隘皆使万事崩。
 * 忆昔太宗盛名日，悲叹百年屈辱中。
 *
 * 故建此类包万物，万语皆可编译通。
 * 君仅继承此类者，并用语言类去行。
 *
 * 亦吾一词在心中，此器不只爪哇乎。
 */
public interface LanguageCompiler {

    Class<?> compile(String script);

    Class<?> compile(String script, String className);


}
