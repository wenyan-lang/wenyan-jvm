package cn.wenyan.compiler;

/**
 * 此为编译器之行动者乎，以规编译器之正式编译语
 *
 * @author 卢昶存
 */

public interface Compile {

    /**
     * 若以此，需行标准之指令选项哉。
     * @param args 选项之令
     * @return 变数之阴阳
     */
    int compile(String... args);
}
