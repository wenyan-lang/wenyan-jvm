package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.utils.Utils;

import java.util.List;

public class ObjectCompileStream extends CompileStream{

    public ObjectCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(List<String> wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan, WenYanLib.OBJECT_IT())){
            compiler.removeWenyan();
            StringBuilder builder = new StringBuilder();
            String arrName = stream.getNowName();
            while (true){
                if(Utils.matches(wenyan,WenYanLib.GIVE_OBJECT())) {
                    String nameDefine = compiler.removeWenyan();
                    String valueDefine;
                    if(Utils.matches(wenyan,WenYanLib.GIVE_OBJECT_VALUE())){
                        valueDefine = compiler.removeWenyan();
                    }else {
                        valueDefine = language.getSyntax(Syntax.NULL);

                    }

                    String name = Utils.getValue(Utils.getString(WenYanLib.STRING(), nameDefine), stream,true);
                    String value = Utils.getValue(valueDefine.substring(valueDefine.indexOf("曰") + 1),stream);
                    builder.append(LanguageUtils.arraySet(language, arrName, name, value)).append("\n");
                }else{
                    if(Utils.matches(wenyan,WenYanLib.OBJECT_END())){
                        compiler.removeWenyan();
                    }
                    break;
                }
            }
            return new CompileResult(builder.toString());
        }
        return new CompileResult(false,wenyan);
    }
}
