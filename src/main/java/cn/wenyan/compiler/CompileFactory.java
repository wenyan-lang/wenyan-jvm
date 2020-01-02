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
        String prefix = "\n无是术也:";
        StringBuilder w = new StringBuilder(Utils.getWenyanFromArray(wenyan)).append("\n");
        int len = w.length()+prefix.length();
        for(int i = 0;i<len;i++){
            w.append(" ");
        }
        w.append("^");
        throw new SyntaxException(prefix+ w);
    }
}
