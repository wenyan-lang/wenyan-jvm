package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.utils.Utils;

import java.util.Arrays;

public class ArrayCompileStream extends CompileStream {

    public ArrayCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(String[] wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan[0], WenYanLib.ADD())){
            String name = Utils.getValue(Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[0]),stream);
            StringBuilder result = new StringBuilder();
            Utils.inputWenyan(compiler,0);
            String[] args = wenyan[1].split("以");
            Utils.inputWenyan(compiler,1);
            for(String arg : args){
                if(!arg.equals(""))
                    result.append(name).append(".add(").append(Utils.getValue(arg,stream)).append(")").append("\n");
            }
            return new CompileResult(result.toString());
        }
        if(Utils.matches(wenyan[0],WenYanLib.GET())){
            Utils.inputWenyan(compiler,0);
            String[] get = wenyan[0].substring(wenyan[0].indexOf("夫")+1).split("之");
            String name = Utils.getValue(get[0],stream);
            String index = Utils.getValue(get[1],stream);
            return new CompileResult(stream.getAnsName()+"="+name+"["+index+"]");
        }
        return new CompileResult(false,wenyan);
    }
}
