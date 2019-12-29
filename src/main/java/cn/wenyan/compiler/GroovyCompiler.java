package cn.wenyan.compiler;

import groovy.lang.GroovyClassLoader;

/**
 * groovy 编译器
 */
public class GroovyCompiler {

    /**
     * 获取脚本的class
     * @param script
     * @return
     */
    public Class<?> compile(String script) {
        GroovyClassLoader loader = getDefaultParentGroovyClassLoader();
        Class<?> groovyClass = loader.parseClass(script);
        return groovyClass;
    }

    /**
     * 获取脚本的class对象
     * @param script  groovy 脚本
     * @param className 类名字
     */
    public Class<?> compile(String script, String className) {
        GroovyClassLoader loader = getDefaultParentGroovyClassLoader();
        Class<?> groovyClass = loader.parseClass(script, className);
        return groovyClass;
    }

    /**
     * 默认的父加载器为GroovyCompiler().getClass().getClassLoader()
     * @return  返回groovy的类加载器
     */
    public GroovyClassLoader getDefaultParentGroovyClassLoader() {
        ClassLoader cl = new GroovyCompiler().getClass().getClassLoader();
        return new GroovyClassLoader(cl);
    }
}