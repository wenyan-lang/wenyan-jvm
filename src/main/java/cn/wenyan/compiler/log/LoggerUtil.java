package cn.wenyan.compiler.log;

import org.apache.commons.io.FileUtils;
import org.fusesource.jansi.Ansi;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class LoggerUtil {

    public static Object println(Object message){
        System.out.println(message);
        return message;
    }

    //log文件的格式 2019-12-21-12.log
    public static void fileDatePrintln(Object message, File parentDir) throws IOException {
        String name = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)+"-"+LocalDateTime.now().getHour()+".log";
        if(!parentDir.exists())parentDir.mkdirs();
        File file = new File(parentDir,name);
        if(!file.exists()){
            if(!file.createNewFile())return;
        }
        String encoding = System.getProperty("file.encoding");
        List<String> lines = FileUtils.readLines(file,encoding);
        Thread thread = Thread.currentThread();
        lines.add(new StringBuilder()
                .append("[")
                .append("Thread")
                .append(" ")
                .append(thread.getName())
                .append("-")
                .append(thread.getId())
                .append("]")
                .append(message.toString()
                        .replaceAll("\\033\\[[0-9]+m","")).toString());
        FileUtils.writeLines(file,encoding,lines);
    }

    public static StackTraceElement getStackTraceElement(){
        StackTraceElement[] elements = new Throwable().getStackTrace();
        StackTraceElement element = elements[elements.length-1];
        int index = elements.length-1;
        while (element.getClassName().startsWith("java")){
            element = elements[index--];
        }
        return element;
    }


    public static String simpleClassName(String className){
        String[] fields = className.split("\\.");
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<fields.length-1;i++){
            String after = fields[i].substring(0,1);
            builder.append(after);
            builder.append(".");
        }
        builder.append(fields[fields.length-1]);
        return builder.toString();
    }

    public static String getStandardLogMessageForMichel(String level, String message, Ansi.Color messageColor,boolean control){
        StackTraceElement element = getStackTraceElement();
        return LogFormat.format(message,level,messageColor,LogFormat.textFormat("["+element.getLineNumber()+":"+simpleClassName(element.getClassName())+"]"+LogFormat.fg(Ansi.Color.DEFAULT), Ansi.Color.CYAN),control);
    }

    public static String getStandardLogMessageForMichel(String level, String message, Ansi.Color messageColor){
        return getStandardLogMessageForMichel(level, message, messageColor,false);
    }

    public static void printException(Throwable t,ILogger logger){
        if(t == null){
            return;
        }
        logger.error(t.toString());
        for(StackTraceElement e : t.getStackTrace()){
            logger.error("\tat "+e);
        }
        logger.error("\tFINISHED");
        printException(t.getCause(),logger);
    }

    public static void printRedLog(String prefix,String message,String level,File parentDir,ILogger logger,Throwable t){
        try {
            LoggerUtil.fileDatePrintln(println(LoggerUtil.getStandardLogMessageForMichel(level, (prefix==null||prefix.equals("")?"":"["+prefix+"]")+message, Ansi.Color.RED, true)), parentDir);
            LoggerUtil.printException(t,logger);
        }catch (IOException e) {
            LoggerUtil.printException(e,logger);
        }
    }


    public static void printCommonLog(String prefix,String message, String level, Ansi.Color color,File parentDir,ILogger logger,Throwable t){
        try {
            LoggerUtil.fileDatePrintln(println(LoggerUtil.getStandardLogMessageForMichel(level,(prefix==null||prefix.equals("")?"":"["+prefix+"]")+ message, color, false)), parentDir);
            LoggerUtil.printException(t,logger);
        }catch (IOException e) {
            LoggerUtil.printException(e,logger);
        }
    }
}
