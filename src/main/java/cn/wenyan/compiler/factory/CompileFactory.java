package cn.wenyan.compiler.factory;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.streams.CompileStream;

import java.util.List;

public class CompileFactory {

    private List<CompileStream> streamList;

    private WenYanCompilerImpl compiler;

    public CompileFactory(List<CompileStream> streams,WenYanCompilerImpl compiler){
        this.compiler = compiler;
        this.streamList = streams;
    }

    public String[] compile(String[] wenyan){
        for(CompileStream stream : streamList){
            CompileResult result = stream.compile(wenyan);
            if(result.isSuccess()){
                return result.getResult();
            }
            wenyan = result.getResult();
        }
        compiler.clearCompiled();
        String prefix = "\n于此言有误: ";
        throw new SyntaxException(prefix+ wenyan[0]+"\n");
    }
}
