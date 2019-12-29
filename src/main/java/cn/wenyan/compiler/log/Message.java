package cn.wenyan.compiler.log;

public class Message{
    String message;
    Throwable throwable;

    public Message(String message, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }



}