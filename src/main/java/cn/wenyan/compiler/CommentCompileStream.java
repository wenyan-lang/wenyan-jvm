package cn.wenyan.compiler;

import cn.wenyan.compiler.utils.Utils;

public class CommentCompileStream extends CompileStream {


    public CommentCompileStream(WenYanCompiler compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(String[] wenyan) {
        if(Utils.matches(wenyan[0],WenYanLib.COMMENT())){
            Utils.inputWenyan(compiler,0);
            Utils.inputWenyan(compiler,1);
            return new CompileResult("/*"+Utils.getStringFrom(WenYanLib.COMMENT(),wenyan[1],"「「","」」")+"*/");
        }
        return new CompileResult(false,wenyan);
    }
}
