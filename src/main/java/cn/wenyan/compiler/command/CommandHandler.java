package cn.wenyan.compiler.command;

import cn.wenyan.compiler.WenYanCompilerImpl;
import cn.wenyan.compiler.exceptions.CommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandHandler {

    public static Map<String,Command> compileCommand = new ConcurrentHashMap<>();

    private WenYanCompilerImpl compiler;

    static {
        registerCommands();
    }

    public static void registerCommands(){
        compileCommand.put("-p",new PinYinCommand());
        compileCommand.put("-c",new CompileFileCommand());
    }

    public CommandHandler(WenYanCompilerImpl compiler){
        this.compiler =compiler;
    }

    public int executeCommand(String[] args){
        CompilerConfig compilerConfig = new CompilerConfig();
        for(int index = 0;index<args.length;index++){
            List<String> arr = new ArrayList<>();
            Command command = compileCommand.get(args[index]);
            if(command!=null){
                int len = command.getArgsLength();
                for(int j = 0;j<len;j++,index++){
                    if(compileCommand.get(args[index])!=null){
                        throw new CommandException("指令之参数不足也");
                    }
                    arr.add(args[index]);
                }
                command.execute(arr.toArray(new String[0]),compilerConfig);
            }else{
                if(index == 0){
                    throw new CommandException("吾亦不知君欲何");
                }
            }
        }

        return compiler.init(compilerConfig);
    }

    public static Map<String, Command> getCompileCommand() {
        return compileCommand;
    }
}
