package cn.wenyan.compiler.exceptions;

public class SyntaxException extends RuntimeException {

    public SyntaxException() {
    }

    public SyntaxException(String message) {
        super(message);
    }
}
