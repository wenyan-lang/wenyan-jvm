package cn.wenyan.compiler.command;

public abstract class Command {

    private String option;

    private int argsLength;

    public abstract Object execute(String[] args,CompilerConfig compilerConfig);

    public Command(String option, int argsLength) {
        this.option = option;
        this.argsLength = argsLength;
    }


    public int getArgsLength() {
        return argsLength;
    }

    public String getOption() {
        return option;
    }
}
