package cn.wenyan.compiler.streams;

import cn.wenyan.compiler.CompileResult;
import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.WenYanLib;
import cn.wenyan.compiler.exceptions.SyntaxException;
import cn.wenyan.compiler.script.libs.LanguageUtils;
import cn.wenyan.compiler.script.libs.Syntax;
import cn.wenyan.compiler.utils.Utils;

import java.util.List;

import static cn.wenyan.compiler.utils.Utils.getValue;

public class ControlCompileStream extends CompileStream {


    long index = 0;

    long close = 0;

    public ControlCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(List<String> wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan, WenYanLib.FOR())){
            String value = compiler.removeWenyan();
            String str = Utils.getString(WenYanLib.FOR(),value);
            if(str != null) {
                str = stream.getNumberString(str)+"";
            }else{
                String var = Utils.getString(WenYanLib.VAR_NAME_FOR(),value);
                str = stream.getName(var,false);

            }
            index++;
            close++;
            return new CompileResult(LanguageUtils.forNumber(language,index+"",str));
        }
        if(Utils.matches(wenyan,WenYanLib.FOR_END())){
            close -- ;
            compiler.removeWenyan();
            return new CompileResult(language.getSyntax(Syntax.FOR_END));
        }
        if(Utils.matches(wenyan,WenYanLib.IF_END())){
            close --;
            compiler.removeWenyan();
            return new CompileResult(language.getSyntax(Syntax.IF_END));
        }
        if(Utils.matches(wenyan,WenYanLib.IF_START())){
            close++;
            String value = compiler.removeWenyan();
            String bool = getBooleanSyntax(value.substring(value.indexOf("若")+1,value.lastIndexOf("者")),stream);
            return new CompileResult(LanguageUtils.defineIf(language,bool));
        }
        if(Utils.matches(wenyan,WenYanLib.IF_BREAK())){
            close++;
            String value = compiler.removeWenyan();
            String bool = getBooleanSyntax(value.substring(value.indexOf("若")+1,value.lastIndexOf("者")),stream);
            return new CompileResult(LanguageUtils.ifBreak(language,bool));
        }
        if(Utils.matches(wenyan,WenYanLib.WHILE())){
            close++;
            compiler.removeWenyan();
            return new CompileResult(language.getSyntax(Syntax.WHILE_TRUE));
        }
        if(Utils.matches(wenyan,WenYanLib.ELSE())){
            close++;
            compiler.removeWenyan();
            return new CompileResult(language.getSyntax(Syntax.ELSE));
        }
        if(Utils.matches(wenyan,WenYanLib.BREAK())){
            compiler.removeWenyan();
            return new CompileResult(language.getSyntax(Syntax.BREAK));
        }
        if(Utils.matches(wenyan,WenYanLib.FOR_EACH())){
            String value = compiler.removeWenyan();
            close++;
            List<String> names = Utils.getStrings(WenYanLib.VAR_NAME_FOR(),value);

            return new CompileResult(LanguageUtils.forEach(language,stream.getName(names.get(1),false),stream.getName(names.get(0),false)));
        }
        if(Utils.matches(wenyan,WenYanLib.CONTINUE())){
            compiler.removeWenyan();
            return new CompileResult(language.getSyntax(Syntax.CONTINUE));
        }
        if(Utils.matches(wenyan,WenYanLib.ELSE_IF())){
            compiler.removeWenyan();
            if(Utils.matches(wenyan,WenYanLib.MACRO_BEFORE())){
                compiler.removeWenyan();
                return new CompileResult("");
            }
            return new CompileResult(language.getSyntax(Syntax.ELSE_IF));
        }
        return new CompileResult(false,wenyan);
    }

    private String getBooleanSyntax(String wenYan,VariableCompileStream stream){
        if(wenYan.contains(WenYanLib.NOT_BIG_THAN())){
            return getBool(WenYanLib.NOT_BIG_THAN(),wenYan);
        }else if(wenYan.contains(WenYanLib.BIG_THAN())){
            return getBool(WenYanLib.BIG_THAN(),wenYan);
        }else if(wenYan.contains(WenYanLib.NOT_SMALL_THAN())){
            return getBool(WenYanLib.NOT_SMALL_THAN(),wenYan);
        }else if(wenYan.contains(WenYanLib.SMALL_THAN())){
            return getBool(WenYanLib.SMALL_THAN(),wenYan);
        }else if(wenYan.contains(WenYanLib.NOT_EQUALS())){
            return getBool(WenYanLib.NOT_EQUALS(),wenYan);
        }else if(wenYan.contains(WenYanLib.EQUALS())){
            return getBool(WenYanLib.EQUALS(),wenYan);
        }else{
            return getValue(wenYan,stream);
        }
    }

    //TODO 未完成
    public String getBool(String type,String wenYan){
        String[] numbers = wenYan.split(type);
        if(numbers.length != 2)throw new SyntaxException("此表达式之过也: "+wenYan);
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        StringBuilder builder = new StringBuilder();
        builder.append(getValue(numbers[0],stream));
        builder.append(language.getSyntax(WenYanLib.bool().get(type).get()));
        builder.append(getValue(numbers[1],stream));
        return builder.toString();
    }



}
