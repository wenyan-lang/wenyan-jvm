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
    public CompileResult compile(String[] wenyan) {
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        if(Utils.matches(wenyan[0], WenYanLib.FOR())){
            Utils.inputWenyan(compiler,0);
            String str = Utils.getString(WenYanLib.FOR(),wenyan[0]);
            if(str != null) {
                str = stream.getNumberString(str)+"";
            }else{
                String var = Utils.getString(WenYanLib.VAR_NAME_FOR(),wenyan[0]);
                str = stream.getName(var,false);

            }
            index++;
            close++;
            return new CompileResult(LanguageUtils.forNumber(language,index+"",str));
        }
        if(Utils.matches(wenyan[0],WenYanLib.FOR_END())){
            close -- ;
            Utils.inputWenyan(compiler,0);
            return new CompileResult(language.getSyntax(Syntax.FOR_END));
        }
        if(Utils.matches(wenyan[0],WenYanLib.IF_END())){
            close --;
            Utils.inputWenyan(compiler,0);
            return new CompileResult(language.getSyntax(Syntax.IF_END));
        }
        if(Utils.matches(wenyan[0],WenYanLib.IF_START())){
            close++;
            Utils.inputWenyan(compiler,0);
            String bool = getBooleanSyntax(wenyan[0].substring(wenyan[0].indexOf("若")+1,wenyan[0].indexOf("者")));
            return new CompileResult(LanguageUtils.defineIf(language,bool));
        }
        if(Utils.matches(wenyan[0],WenYanLib.IF_BREAK())){
            close++;
            Utils.inputWenyan(compiler,0);
            String bool = getBooleanSyntax(wenyan[0].substring(wenyan[0].indexOf("若")+1,wenyan[0].indexOf("者")));
            return new CompileResult(LanguageUtils.ifBreak(language,bool));
        }
        if(Utils.matches(wenyan[0],WenYanLib.WHILE())){
            close++;
            Utils.inputWenyan(compiler,0);
            return new CompileResult(language.getSyntax(Syntax.WHILE_TRUE));
        }
        if(Utils.matches(wenyan[0],WenYanLib.ELSE())){
            close++;
            Utils.inputWenyan(compiler,0);
            return new CompileResult(language.getSyntax(Syntax.ELSE));
        }
        if(Utils.matches(wenyan[0],WenYanLib.BREAK())){
            Utils.inputWenyan(compiler,0);
            return new CompileResult(language.getSyntax(Syntax.BREAK));
        }
        if(Utils.matches(wenyan[0],WenYanLib.FOR_EACH())){
            Utils.inputWenyan(compiler,0);
            close++;
            List<String> names = Utils.getStrings(WenYanLib.VAR_NAME_FOR(),wenyan[0]);

            return new CompileResult(LanguageUtils.forEach(language,stream.getName(names.get(1),false),stream.getName(names.get(0),false)));
        }
        return new CompileResult(false,wenyan);
    }

    private String getBooleanSyntax(String wenYan){
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
        }
        throw new SyntaxException("无此对比也: "+wenYan);
    }

    //TODO 未完成
    public String getBool(String type,String wenYan){
        String[] numbers = wenYan.split(type);
        if(numbers.length != 2)throw new SyntaxException("此表达式之过也: "+wenYan);
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        return new StringBuilder().append(getValue(numbers[0],stream))
                .append(language.getSyntax(WenYanLib.bool().get(type).get()))
                .append(getValue(numbers[1],stream)).toString();
    }



}
