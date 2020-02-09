package cn.wenyan.compiler;

import cn.wenyan.compiler.utils.Utils;


import java.util.Collections;
import java.util.List;

public class CompileResult {

    private boolean success;

    private List<String> result;

    public CompileResult(boolean success, List<String> result) {
        this.success = success;
        this.result = result;
    }

    public CompileResult(String result){
        this.success = true;
        this.result = Collections.singletonList(result);
    }

    public List<String> getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return Utils.getWenyanFromArray(result.toArray(new String[0]));
    }
}
