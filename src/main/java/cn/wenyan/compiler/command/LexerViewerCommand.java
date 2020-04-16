package cn.wenyan.compiler.command;

public class LexerViewerCommand extends Command{


    public LexerViewerCommand(){
        super("-lv",0);
    }

    @Override
    public Object execute(String[] args, CompilerConfig compilerConfig) {
        compilerConfig.setLexerViewer(true);
        return null;
    }

    @Override
    public String help() {
        return "可視化分詞信息";
    }
}
