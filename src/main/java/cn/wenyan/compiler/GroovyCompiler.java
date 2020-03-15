package cn.wenyan.compiler;

import groovy.lang.GroovyClassLoader;

/**
 *
 * 欲语此类用者何，成名一诗少不了。
 *
 * 若以成者行此中，必先编译以横行。
 * 此类不出实体者，即编即用以交互。
 *
 * 先得加载对象者，终以源码得英雄，
 * 此类内涵不言耳，故以工具以敝之。
 *
 */
public class GroovyCompiler implements LanguageCompiler{

    /**
     * 获取脚本的class
     * @param script
     * @return
     */
    public Class<?> compile(String script) {
        GroovyClassLoader loader = getDefaultParentGroovyClassLoader();
        return loader.parseClass(script);
    }

    /**
     * 获取脚本的class对象
     * @param script  groovy 脚本
     * @param className 类名字
     */
    public Class<?> compile(String script, String className) {
        GroovyClassLoader loader = getDefaultParentGroovyClassLoader();
        return loader.parseClass(script, className);
    }

    /**
     * 默认的父加载器为GroovyCompiler().getClass().getClassLoader()
     * @return  返回groovy的类加载器
     */
    private GroovyClassLoader getDefaultParentGroovyClassLoader() {
        ClassLoader cl = GroovyCompiler.class.getClassLoader();
        return new GroovyClassLoader(cl);
    }


}