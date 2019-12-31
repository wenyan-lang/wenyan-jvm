package cn.wenyan.compiler;

import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.utils.Utils;

public class StreamBuilder {

    public static String[] compile(String[] wenyan,CompileStream... streams){

        for(CompileStream stream : streams){
            CompileResult result = stream.compile(wenyan);
            if(result.isSuccess()){
                return result.getResult();
            }
            wenyan = result.getResult();
        }
        throw new SyntaxException("无是术也:"+ Utils.getWenyanFromArray(wenyan));
    }
}
