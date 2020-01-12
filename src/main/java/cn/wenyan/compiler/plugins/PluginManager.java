package cn.wenyan.compiler.plugins;

import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.streams.CompileStream;
import cn.wenyan.compiler.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {

    private Map<String,Plugin> plugins = new HashMap<>();

    private WenYanCompilerImpl compiler;

    public PluginManager(WenYanCompilerImpl impl){
        this.compiler = impl;
    }

    public Plugin loadPlugin(File file){
        try {
            JavaPluginClassloader cl = new JavaPluginClassloader(file);
            JarFile jar = new JarFile(file);
            Main main = null;
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()){
               JarEntry entry = entries.nextElement();
               String name = Utils.getClassName(entry.getName());
               Class clz = cl.loadClass(name);
               main = (Main) clz.getAnnotation(Main.class);
               if(main != null){
                   break;
               }
            }
            if(main == null){
                throw new ClassNotFoundException("No main class");
            }else{
               String name = main.name();
               Class<?> clz = main.after();
               try {
                   Plugin plugin = (Plugin) clz.newInstance();
                   List<CompileStream> streamList = new ArrayList<>();
                   List<Listener> listeners = new ArrayList<>();
                   plugin.addCompileStream(streamList);
                   plugin.addSyntaxRegex(WenYanLib.syntaxs());
                   plugin.addListener(listeners);
                   plugin.addPatterns(WenYanLib.patterns());
                   compiler.getFactory().addStream(streamList,clz);
                   compiler.getListeners().addAll(listeners);
                   plugins.put(name,plugin);
                   compiler.getServerLogger().info("加载插件: "+name+" 成功");
                   return plugin;
               }catch (Exception e){
                   compiler.getServerLogger().error("",e);
               }

            }
        }catch (ClassNotFoundException|IOException e){
            compiler.getServerLogger().error("",e);
        }
        return null;
    }

    public Map<String, Plugin> getPlugins() {
        return plugins;
    }
}
