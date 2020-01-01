package cn.wenyan.compiler;

import cn.wenyan.compiler.exceptions.SyntaxException;

public class ControlCompileStream extends CompileStream {

    public ControlCompileStream(WenYanCompilerImpl compiler) {
        super(compiler);
    }

    @Override
    public CompileResult compile(String[] wenyan) {
        return new CompileResult(false,wenyan);
    }

    public static void main(String[] args) throws Exception{

        System.out.println(new ControlCompileStream(new WenYanCompilerImpl(true)).getBooleanSyntax("一不小於二"));
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
        }
        throw new SyntaxException("无此对比也: "+wenYan);
    }

    public String getBool(String type,String wenYan){
        String[] numbers = wenYan.split(type);
        VariableCompileStream stream = compiler.getStream(VariableCompileStream.class);
        return stream.getNumber(numbers[0])+WenYanLib.bool().get(type).get()+stream.getNumber(numbers[1]);
    }
}
