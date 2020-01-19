import static cn.wenyan.lang.算經.絕對
import static cn.wenyan.lang.算經.平方根
import static cn.wenyan.compiler.lib.ArrayUtils.*
import static cn.wenyan.compiler.lib.MathUtil.*
def 畫心 = {
    心語->
        def ans_1 = getArray(心語).size()
        def 長度 = ans_1

        def 填充符 = "一"

        def 换行符 = "\n"

        def ans_2 = 13/10
        def 乙 = ans_2

        def ans_3 = -11/10
        def 乙止 = ans_3

        def ans_4 = 40/1000
        def 甲步長 = ans_4

        def ans_5 = 6/100
        def 乙步長 = ans_5

        def 輸出位置 = 1

        def 果 = ""

        while(true){
            if(乙<乙止){
                break
            }
            def ans_6 = -11/10
            def 甲 = ans_6

            def ans_7 = 11/10
            def 甲止 = ans_7

            def 本行 = ""

            while(true){
                if(甲>甲止){
                    break
                }
                def ans_8 = 絕對(甲)
                def 甲絕對 = ans_8

                def ans_9 = 平方根(甲絕對)
                def 減數 = ans_9

                def ans_10 = 5*乙
                def ans_11 = ans_10/4
                def 被減數 = ans_11

                def ans_12 = 被減數-減數
                def 差 = ans_12

                def ans_13 = 差*差
                def 加數 = ans_13

                def ans_14 = 甲*甲
                def ans_15 = ans_14+加數
                def ans_16 = ans_15-1
                def 函數值 = ans_16

                if(函數值<=0){
                    def ans_17 = getArray(心語)[getIndex(輸出位置)]
                    def 字 = ans_17

                    def ans_18 = 本行+字
                    本行 = ans_18

                    def ans_19 = mod(輸出位置,長度)
                    def ans_20 = ans_19+1
                    輸出位置 = ans_20

                }else{
                    def ans_21 = 本行+填充符
                    本行 = ans_21

                }
                def ans_22 = 甲+甲步長
                甲 = ans_22

            }
            def ans_23 = 乙-乙步長
            乙 = ans_23

            def ans_24 = 本行+换行符
            本行 = ans_24

            def ans_25 = 果+本行
            果 = ans_25

        }
        def ans_26 = ''+果+''
        println(ans_26)
}
def ans_27 = 畫心("琉璃梳子撫青絲。畫心牽腸癡不癡。")