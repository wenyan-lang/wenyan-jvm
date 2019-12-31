package cn.wenyan.compiler;

import cn.wenyan.compiler.utils.Utils;

public class CompileResult {

    private boolean success;

    private String[] result;

    public CompileResult(boolean success, String[] result) {
        this.success = success;
        this.result = result;
    }

    public CompileResult(String result){
        this.success = true;
        this.result = new String[]{result};
    }

    public String[] getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setResult(String[] result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return Utils.getWenyanFromArray(result);
    }
}
