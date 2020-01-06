package cn.wenyan.compiler.factory;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.streams.CompileStream;
import cn.wenyan.compiler.utils.Assert;

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
        return Assert.syntaxError(wenyan[0]);
    }
}
