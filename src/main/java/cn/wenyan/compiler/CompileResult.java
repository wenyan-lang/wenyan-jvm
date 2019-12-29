package cn.wenyan.compiler;

public class CompileResult {

    private boolean success;

    private String result;

    public CompileResult(boolean success, String result) {
        this.success = success;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return result;
    }
}
