/*
Jsmod2 is a java-based scpsl cn.jsmod2.server initiated by jsmod2.cn.
It needs to rely on smod2 and proxy. jsmod2 is an open source
free plugin that is released under the GNU license. Please read
the GNU open source license before using the software. To understand
the appropriateness, if infringement, will be handled in accordance
with the law, @Copyright Jsmod2 China,more can see <a href="http://jsmod2.cn">that<a>
 */
package cn.wenyan.compiler.log;

import org.fusesource.jansi.Ansi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.fusesource.jansi.Ansi.Color.*;

public class LogFormat {

    private static Map<Ansi.Color,String> colors;

    static {
        colors = new HashMap<>();
        colors.put(BLACK,"\033[30m");
        colors.put(RED,"\033[31m");
        colors.put(GREEN,"\033[32m");
        colors.put(YELLOW,"\033[33m");
        colors.put(BLUE,"\033[34m");
        colors.put(MAGENTA,"\033[35m");
        colors.put(CYAN,"\033[36m");
        colors.put(WHITE,"\033[37m");
        colors.put(DEFAULT,"\033[0m");
    }

    public static String fg(Ansi.Color c){
        return colors.get(c);
    }

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String format(String message, String type, Ansi.Color color,String prefix){
        return format(message,type,color,prefix,false);
    }

    public static String format(String message, String type, Ansi.Color color,String prefix,boolean controlMain){
        return prefix+fg(MAGENTA)+dateTimeFormatter.format(LocalDateTime.now())+fg(DEFAULT)+"["+fg(color)+(controlMain?control(type, Control.GLISTEN):type)+fg(DEFAULT)+"\t]"+fg(DEFAULT)+" "+message;
        //return ansi().eraseScreen().a(prefix).fg(MAGENTA).a(dateTimeFormatter.format(LocalDateTime.now())).fg(DEFAULT).a("[").fg(color).a(type).fg(DEFAULT).a("\t]").fg(BLUE).a(" "+message).reset();
    }

    public static String textFormat(String message, Ansi.Color color){
        return fg(color)+message;
        //return ansi().eraseScreen().fg(color).a(message).reset();
    }

    public static String control(String message,Control c){
        return c.getAnsi()+message;
    }

    public enum Control{

        GLISTEN("\033[5m");


        private String ansi;
        Control(String ansi){
            this.ansi = ansi;
        }

        public String getAnsi() {
            return ansi;
        }
    }

}
