import static cn.wenyan.compiler.lib.ArrayUtils.getArray
import static cn.wenyan.compiler.lib.ArrayUtils.getIndex
def 曼德博(寬,高){
/*曼德博集。亦稱曼德布洛特复数集合。复平面上组成分形之点之集合也。*/
    def 皴法 = '丁龘蠹臺龜畫龍淼蔑高五三二'

/*皴法者。圖畫之法也*/
    def ans_1 = 0-1
    def 上 = ans_1

    def ans_2 = 0+1
    def 下 = ans_2

    def ans_3 = 0-2
    def 左 = ans_3

    def ans_4 = 0+2
    def 右 = ans_4

    def ans_5 = 右-左
    def ans_6 = ans_5/寬
    def 橫步 = ans_6

    def ans_7 = 下-上
    def ans_8 = ans_7/高
    def 縱步 = ans_8

    def 戊 = 0

    while(true){
        if(戊==高){
            break
        }
        def ans_9 = 縱步*戊
        def ans_10 = ans_9+上
        def 虛 = ans_10

        def 行 = ''

        def 戌 = 0

        while(true){
            if(戌==寬){
                break
            }
            def ans_11 = 橫步*戌
            def ans_12 = ans_11+左
            def 實 = ans_12

            def 虛虛 = 虛

            def 實實 = 實

/*凡每一像素。皆算令其收斂之最大疊代數*/
            def 己 = 0

            while(true){
                if(己==12){
                    break
                }
                def ans_13 = 實實*實實
                def ans_14 = 虛虛*虛虛
                def 甲 = ans_13
                def 乙 = ans_14

                def ans_15 = 甲+乙
                def 丙 = ans_15

                if(丙>4){
                    break
                }
                def ans_16 = 虛虛*實實
                def ans_17 = ans_16*2
                def ans_18 = ans_17+虛
                虛虛 = ans_18

                def ans_19 = 甲-乙
                def ans_20 = ans_19+實
                實實 = ans_20

                def ans_21 = 1+己
                己 = ans_21

            }
/*既得疊代數。乃以皴法圖之*/
            def ans_22 = 13-己
            def 巳 = ans_22

            def ans_23 = getArray(皴法)[getIndex(巳)]
            def 墨 = ans_23

            def ans_24 = 行+墨
            行 = ans_24

            def ans_25 = 1+戌
            戌 = ans_25

        }
        def ans_26 = ''+行+''
        println(ans_26)
        def ans_27 = 1+戊
        戊 = ans_27

    }
}
/*畫曼德博集合之法。至是盡矣。乃一試之*/
def ans_28 = 曼德博(59,24)