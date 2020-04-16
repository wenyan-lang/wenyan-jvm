package cn.wenyan.compiler.plugins;

import cn.wenyan.compiler.streams.CompileStream;

import java.util.List;

public abstract class Listener {

    public abstract void onCompileStart(CompileStream stream,List<String> list);

    public abstract void onCompileFinish(CompileStream stream, List<String> list);

    public abstract void onCompileFailed(CompileStream stream,List<String> list);
}
