package cn.wenyan.compiler.command;

public class CompilerConfig {

    private boolean supportPinYin;//

    private String[] compileFiles;//

    private String[] compileLib;//

    private String outFile;//

    private boolean run;//

    private String[] runArgs;

    public boolean isSupportPinYin() {
        return supportPinYin;
    }

    public void setSupportPinYin(boolean supportPinYin) {
        this.supportPinYin = supportPinYin;
    }

    public String[] getCompileFiles() {
        return compileFiles;
    }

    public void setCompileFiles(String... compileFiles) {
        this.compileFiles = compileFiles;
    }

    public String[] getCompileLib() {
        return compileLib;
    }

    public void setCompileLib(String... compileLib) {
        this.compileLib = compileLib;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public String[] getRunArgs() {
        return runArgs;
    }

    public void setRunArgs(String... runArgs) {
        this.runArgs = runArgs;
    }

    public String getOutFile() {
        return outFile;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
