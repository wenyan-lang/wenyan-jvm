package cn.wenyan.compiler.utils;

import java.util.List;

public class ResultEntry {

    private List<String> code;

    private String outCode;

    public ResultEntry(List<String> code, String outCode) {
        this.code = code;
        this.outCode = outCode;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }
}
