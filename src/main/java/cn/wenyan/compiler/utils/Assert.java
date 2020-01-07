package cn.wenyan.compiler.utils;

import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.exceptions.SyntaxException;

public class Assert {

    public static void typeCheck(WenYanCompilerImpl stream, String name, String code, String... types){
        String type = stream.getNameType().get(name);
        for(String s: types){
            if(type.equals(s)){
                throw new SyntaxException("昔"+name+"为"+type+"者，不能迭代之: "+code);
            }
        }
    }

    public static String[] syntaxError(String wenyan) throws SyntaxException{
        String prefix = "\n于此言有误: ";
        throw new SyntaxException(prefix+ wenyan+"\n");
    }
}
