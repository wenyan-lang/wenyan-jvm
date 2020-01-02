package cn.wenyan.compiler;

import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.utils.Utils;

import java.util.List;

public class CompileFactory {

    private List<CompileStream> streamList;


    public CompileFactory(List<CompileStream> streams){
        streamList = streams;
    }

    public String[] compile(String[] wenyan){
        for(CompileStream stream : streamList){
            CompileResult result = stream.compile(wenyan);
            if(result.isSuccess()){
                return result.getResult();
            }
            wenyan = result.getResult();
        }
        throw new SyntaxException("无是术也:"+ Utils.getWenyanFromArray(wenyan));
    }
}
