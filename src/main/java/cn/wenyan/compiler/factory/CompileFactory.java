package cn.wenyan.compiler.factory;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.streams.CompileStream;
import cn.wenyan.compiler.utils.Assert;

import java.util.ArrayList;
import java.util.List;

public class CompileFactory {

    private List<CompileStream> streamList;

    private WenYanCompilerImpl compiler;

    private List<String> copy;

    public CompileFactory(List<CompileStream> streams,WenYanCompilerImpl compiler){
        this.compiler = compiler;
        this.streamList = streams;
    }

    public void addStream(List<CompileStream> stream,Class after){
        for(int i = 0;i<streamList.size();i++){
            if(streamList.get(i).getClass() == after){
                streamList.addAll(i+1,stream);
                break;
            }
        }
    }

    public List<String> compile(int index,List<String> wenyan){
        copy = new ArrayList<>();
        copy.addAll(wenyan);
        for(CompileStream stream : streamList){
            compiler.callListenerStart(stream,wenyan);
            CompileResult result = stream.compile(wenyan);
            if(result.isSuccess()){
                compiler.callListenerEnd(stream,result.getResult());
                return result.getResult();
            }else{
                if(!wenyan.equals(copy)){
                    wenyan.clear();
                    wenyan.addAll(copy);
                    compiler.clearIndexCode();
                }
                compiler.callListenerFailed(stream,wenyan);
            }
        }
        return Assert.syntaxError(index+"语句组 Line: "+(compiler.getIndexCode())+": "+wenyan.get(0));
    }
}
