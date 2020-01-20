import cn.wenyan.compiler.lib.JSArray
import static cn.wenyan.compiler.lib.ArrayUtils.*
def 賈憲三角 = {
    層數->
        def 前層之得 = new JSArray()

        前層之得.add(1)

        def ans_1 = 前層之得
        println(ans_1)
        if(層數==1){
            return
        }
        前層之得.add(1)

        def ans_2 = 前層之得
        println(ans_2)
        if(層數==2){
            return
        }
        def 計甲 = 3

        while(true){
            if(計甲>層數){
                break
            }
            def 此層之得 = new JSArray()

            此層之得.add(1)

            def 計乙 = 1

            def ans_3 = getArray(前層之得).size()
            def 層長 = ans_3

            while(true){
                if(計乙>=層長){
                    break
                }
                def ans_4 = 1+計乙
                def 計乙又一 = ans_4

                def ans_5 = getArray(前層之得)[getIndex(計乙)]
                def 數甲 = ans_5

                def ans_6 = getArray(前層之得)[getIndex(計乙又一)]
                def 數乙 = ans_6

                def ans_7 = 數甲+數乙
                def 新數 = ans_7

                此層之得.add(新數)

                def ans_8 = 計乙+1
                計乙 = ans_8

            }
            此層之得.add(1)

            def ans_9 = 此層之得
            println(ans_9)
            前層之得 = 此層之得

            def ans_10 = 計甲+1
            計甲 = ans_10

        }
}
def ans_11 = 賈憲三角(9)