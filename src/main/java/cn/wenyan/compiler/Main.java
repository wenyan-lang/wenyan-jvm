package cn.wenyan.compiler;


import cn.wenyan.compiler.command.CommandHandler;



public class Main {

    public static void main(String[] args) throws Exception{
        if (args.length == 0){
            CommandHandler.compileCommand.entrySet().stream().forEach(x->System.out.println(x.getValue().getOption()+": "+x.getValue().getClass().getSimpleName()));
        }
        WenYanCompiler compiler = new WenYanCompilerImpl(false);
        compiler.compile(args);
    }
}
