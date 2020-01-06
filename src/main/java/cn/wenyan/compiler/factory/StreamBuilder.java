package cn.wenyan.compiler.factory;



import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.streams.CompileStream;

import java.util.ArrayList;
import java.util.List;

public class StreamBuilder {

    private List<CompileStream> streams = new ArrayList<>();

    private WenYanCompilerImpl compiler;

    public StreamBuilder(WenYanCompilerImpl compiler){
        this.compiler = compiler;
    }

    public StreamBuilder put(CompileStream stream){
        streams.add(stream);
        return this;
    }

    public CompileFactory build(){
        return new CompileFactory(streams,compiler);
    }


}
