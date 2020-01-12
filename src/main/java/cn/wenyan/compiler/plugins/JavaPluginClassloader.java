package cn.wenyan.compiler.plugins;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class JavaPluginClassloader extends URLClassLoader {

    public JavaPluginClassloader(File file) throws MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, JavaPluginClassloader.class.getClassLoader());
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if(name.startsWith("cn.wenyan")){
            throw new ClassNotFoundException("you couldn't use cn.wenyan package name");
        }
        return super.loadClass(name, resolve);
    }

}
