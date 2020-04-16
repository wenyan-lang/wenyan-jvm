package cn.wenyan.compiler;

import cn.wenyan.compiler.utils.Utils;


import java.util.Collections;
import java.util.List;

/**
 * 欲语此类用者何，成名一诗少不了。
 *
 * 若使君知何成者，成果皆或败事否。
 * 功成不少家书至，故以两者记事和。
 *
 * 此用流程以明理，成功之字以终行。
 * 此用流程以明志，结果之符以结行。
 *
 * 春风君者何时至，红灯不删绿灯鸣。
 */
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
