package cn.wenyan.compiler.log;


import java.util.function.BiConsumer;


public enum LogLevel {

    NONE((Message m, ILogger logger)->{}),
    EMERGENCY((Message m, ILogger logger)->logger.emergency(m.message,m.throwable)),
    ALERT((Message m, ILogger logger)->logger.alert(m.message,m.throwable)),
    CRITICAL((Message m, ILogger logger)->logger.critical(m.message,m.throwable)),
    ERROR((Message m, ILogger logger)->logger.critical(m.message,m.throwable)),
    WARNING((Message m, ILogger logger)->logger.warning(m.message,m.throwable)),
    NOTICE((Message m, ILogger logger)->logger.notice(m.message,m.throwable)),
    INFO((Message m, ILogger logger)->logger.info(m.message,m.throwable)),
    DEBUG((Message m, ILogger logger)->logger.debug(m.message,m.throwable));

    private BiConsumer<Message, ILogger> func;

    LogLevel(BiConsumer<Message,ILogger> func) {
        this.func = func;
    }

    public BiConsumer<Message, ILogger> getFunc() {
        return func;
    }



}
