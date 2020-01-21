import 格物
import static 格物.取物
import static 格物.置物
import static 格物.列物之端
import static 格物.識類

def 引號 = "「」"

def ans_1 = getArray(引號)[getIndex(1)]
def 引起 = ans_1

def ans_2 = getArray(引號)[getIndex(2)]
def 引迄 = ans_2

def 位名 = new JSArray()

位名.add("〇")
位名.add("一")
位名.add("二")
位名.add("三")
位名.add("四")
位名.add("五")
位名.add("六")
位名.add("七")
位名.add("八")
位名.add("九")

def 斬渾沌 = {
    渾沌語->
        def 諸咒 = new JSArray()

        諸咒.add("物")
        諸咒.add("言")
        諸咒.add("數")
        諸咒.add("爻")
        諸咒.add("列")
        諸咒.add("之")
        諸咒.add("也")

        def 渾沌碎 = new JSArray()

        def 讀 = 1

        def 層 = 0

        def 辭 = ''

        while(true){
            if(讀>getArray(渾沌語).size()){
                break
            }
            if(getArray(渾沌語)[getIndex(讀)]==引起){
                if(層!=0){
                    def ans_3 = getArray(渾沌語)[getIndex(讀)]
                    def ans_4 = ans_3+辭
                    辭 = ans_4

                }
                def ans_5 = 層+1
                層 = ans_5

            }else
            if(getArray(渾沌語)[getIndex(讀)]==引迄){
                def ans_6 = 層-1
                層 = ans_6

                if(層==0){
                    渾沌碎.add(辭)

                    辭 = ""

                }else{
                    def ans_7 = getArray(渾沌語)[getIndex(讀)]
                    def ans_8 = ans_7+辭
                    辭 = ans_8

                }
            }else
            if(層>0){
                def ans_9 = getArray(渾沌語)[getIndex(讀)]
                def ans_10 = ans_9+辭
                辭 = ans_10

            }else{
                for(咒 in 諸咒){
                    if(getArray(渾沌語)[getIndex(讀)]==咒){
                        渾沌碎.add(咒)

                        break
                    }
                }
            }
            def ans_11 = 1+讀
            讀 = ans_11

        }
        return 渾沌碎
}
def 食渾沌 = {
    渾沌語__1__食渾沌->
        def ans_12 = 斬渾沌(渾沌語__1__食渾沌)
        def 渾沌碎 = ans_12

        def 食數 = {
            數名->
                def 正負 = 1

                def ans_13 = getArray(數名)[getIndex(1)]
                if(ans_13=="負"){
                    def ans_14 = getArray(數名).slice(1)
                    數名 = ans_14

                    正負 = -1

                }
                def 整 = 0
                def 小 = 0

                def 讀 = 1
                def 小長 = 1

                def 小耶 = FALSE

                while(true){
                    if(讀>getArray(數名).size()){
                        break
                    }
                    if(讀=="·"){
                        小耶 = true

                    }else{
                        def 位 = 1

                        while(true){
                            if(位>getArray(位名).size()){
                                break
                            }
                            if(getArray(位名)[getIndex(位)]==getArray(數名)[getIndex(讀)]){
                                break
                            }
                            def ans_15 = 1+位
                            位 = ans_15

                        }
                        def ans_16 = 位-1
                        位 = ans_16

                        if(小耶){
                            def ans_17 = 小*10
                            def ans_18 = ans_17+位
                            小 = ans_18

                            def ans_19 = 小長+1
                            小長 = ans_19

                        }else{
                            def ans_20 = 整*10
                            def ans_21 = ans_20+位
                            整 = ans_21

                        }
                    }
                    def ans_22 = 1+讀
                    讀 = ans_22

                }
                for(_ans1 in 1..小長){
                    def ans_23 = 小*0.1
                    小 = ans_23

                }
                def ans_24 = 整+小
                def ans_25 = ans_24*正負
                return ans_25
        }
        def 食列 = {
            渾沌碎__2__食渾沌->
                def 渾沌列 = new JSArray()

                def 讀 = 1

                while(true){
                    if(讀>getArray(渾沌碎__2__食渾沌).size()){
                        break
                    }
                    def ans_26 = getArray(渾沌碎__2__食渾沌)[getIndex(讀)]
                    def 類 = ans_26

                    if(類=="數"){
                        def ans_27 = 讀+1
                        def ans_28 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_28)]

                        def ans_29 = 食數(ans_29)
                        渾沌列.add(ans_29)

                        def ans_30 = 讀+2
                        讀 = ans_30

                    }else
                    if(類=="言"){
                        def ans_31 = 讀+1
                        def ans_32 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_32)]
                        渾沌列.add(ans_32)

                        def ans_33 = 讀+2
                        讀 = ans_33

                    }else
                    if(類=="爻"){
                        def ans_34 = 讀+1
                        def ans_35 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_35)]
                        if(ans_35=="陰"){
                            渾沌列.add(false)

                        }else{
                            渾沌列.add(true)

                        }
                        def ans_36 = 讀+2
                        讀 = ans_36

                    }else{
                        def 層 = 0

                        def ans_37 = 讀+1
                        def 次讀 = ans_37

                        def 句 = new JSArray()

                        while(true){
                            if(次讀>getArray(渾沌碎__2__食渾沌).size()){
                                break
                            }
                            if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="物"){
                                def ans_38 = 層+1
                                層 = ans_38

                            }else
                            if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="列"){
                                def ans_39 = 層+1
                                層 = ans_39

                            }else
                            if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="也"){
                                if(層==0){
                                    if(類=="物"){
                                        def ans_40 = 食物(句)
                                        渾沌列.add(ans_40)

                                    }else{
                                        def ans_41 = 食列(句)
                                        渾沌列.add(ans_41)

                                    }
                                    break
                                }
                                def ans_42 = 層-1
                                層 = ans_42

                            }
                            def ans_43 = getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]
                            句.add(ans_43)

                            def ans_44 = 次讀+1
                            次讀 = ans_44

                        }
                        def ans_45 = 次讀+1
                        讀 = ans_45

                    }
                }
                return 渾沌列
        }
        def 食物 = {
            渾沌碎__2__食渾沌->
                def 渾沌物 = [:]

                def 讀 = 2

                while(true){
                    if(讀>getArray(渾沌碎__2__食渾沌).size()){
                        break
                    }
                    def ans_46 = getArray(渾沌碎__2__食渾沌)[getIndex(讀)]
                    def 端 = ans_46

                    def ans_47 = 讀+1
                    def ans_48 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_48)]
                    def 類 = ans_48

                    if(類=="數"){
                        def ans_49 = 讀+2
                        def ans_50 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_50)]

                        def ans_51 = 食數(ans_51)
                        def ans_52 = 置物(渾沌物,端,ans_51)
                        def ans_53 = 讀+4
                        讀 = ans_53

                    }else
                    if(類=="言"){
                        def ans_54 = 讀+2
                        def ans_55 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_55)]
                        def ans_56 = 置物(渾沌物,端,ans_55)
                        def ans_57 = 讀+4
                        讀 = ans_57

                    }else
                    if(類=="爻"){
                        def ans_58 = 讀+2
                        def ans_59 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_59)]
                        if(ans_59=="陰"){
                            def ans_60 = 置物(渾沌物,端,false)
                        }else{
                            def ans_61 = 置物(渾沌物,端,true)
                        }
                        def ans_62 = 讀+4
                        讀 = ans_62

                    }else{
                        def 層 = 0

                        def ans_63 = 讀+2
                        def 次讀 = ans_63

                        def 句 = new JSArray()

                        while(true){
                            if(次讀>getArray(渾沌碎__2__食渾沌).size()){
                                break
                            }
                            if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="物"){
                                def ans_64 = 層+1
                                層 = ans_64

                            }else
                            if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="列"){
                                def ans_65 = 層+1
                                層 = ans_65

                            }else
                            if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="也"){
                                if(層==0){
                                    if(類=="物"){
                                        def ans_66 = 食物(句)
                                        def ans_67 = 置物(渾沌物,端,ans_66)
                                    }else{
                                        def ans_68 = 食列(句)
                                        def ans_69 = 置物(渾沌物,端,ans_68)
                                    }
                                    break
                                }
                                def ans_70 = 層-1
                                層 = ans_70

                            }
                            def ans_71 = getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]
                            句.add(ans_71)

                            def ans_72 = 次讀+1
                            次讀 = ans_72

                        }
                        def ans_73 = 次讀+2
                        讀 = ans_73

                    }
                }
                return 渾沌物
        }
        def ans_74 = getArray(渾沌碎).slice(1)

        def ans_75 = 食物(ans_75)
        return ans_75
}
def 包渾沌 = {
    渾沌物__1__包渾沌->
        def 挪符 = "　"

        def 抬符 = "\n"

        def 包數 = {
            甲->
                def 正負 = ''

                if(甲<0){
                    def ans_76 = 甲*-1
                    甲 = ans_76

                    正負 = "負"

                }
                def ans_77 = mod(甲,1)
                def 小數 = ans_77

                def ans_78 = 小數-甲
                def 整數 = ans_78

                def 小 = ''
                def 整 = ''

                while(true){
                    if(整數<=0){
                        break
                    }
                    def ans_79 = mod(整數,10)
                    def 位 = ans_79

                    def ans_80 = 位+1
                    def ans_81 = getArray(位名)[getIndex(ans_81)]
                    def ans_82 = ans_81+整
                    整 = ans_82

                    def ans_83 = 整數-位
                    def ans_84 = ans_83/10
                    整數 = ans_84

                }
                while(true){
                    if(小數<=0){
                        break
                    }
                    def ans_85 = 小數*10
                    小數 = ans_85

                    def ans_86 = mod(小數,1)
                    def 位 = ans_86

                    def ans_87 = 位+1
                    def ans_88 = getArray(位名)[getIndex(ans_88)]
                    def ans_89 = ans_88+小
                    小 = ans_89

                    def ans_90 = 小數-位
                    小數 = ans_90

                }
                def ans_91 = 正負+整
                整 = ans_91

                def ans_92 = getArray(小).size()
                if(ans_92){
                    def ans_93 = 整+"·"
                    def ans_94 = ans_93+小
                    return ans_94
                }
                return 整
        }
        def 暗包渾沌 = {
            類__2__包渾沌,實,挪抬->
                def 渾沌語 = ''

                if(類__2__包渾沌=="物"){
                    def ans_95 = 渾沌語+抬符
                    渾沌語 = ans_95

                    def ans_96 = 實
                    def ans_97 = 挪抬+1

                    def ans_98 = 包物(ans_97,ans_98)
                    def ans_99 = ans_98+渾沌語
                    渾沌語 = ans_99

                    for(_ans2 in 1..挪抬){
                        def ans_100 = 渾沌語+挪符
                        渾沌語 = ans_100

                    }
                    def ans_101 = 渾沌語+"也"
                    def ans_102 = ans_101+抬符
                    渾沌語 = ans_102

                }else
                if(類__2__包渾沌=="列"){
                    def ans_103 = 渾沌語+抬符
                    渾沌語 = ans_103

                    def ans_104 = 實
                    def ans_105 = 挪抬+1

                    def ans_106 = 包列(ans_105,ans_106)
                    def ans_107 = ans_106+渾沌語
                    渾沌語 = ans_107

                    for(_ans3 in 1..挪抬){
                        def ans_108 = 渾沌語+挪符
                        渾沌語 = ans_108

                    }
                    def ans_109 = 渾沌語+"也"
                    def ans_110 = ans_109+抬符
                    渾沌語 = ans_110

                }else
                if(類__2__包渾沌=="言"){
                    def ans_111 = 渾沌語+引起
                    def ans_112 = ans_111+實
                    def ans_113 = ans_112+引迄
                    def ans_114 = ans_113+抬符
                    渾沌語 = ans_114

                }else
                if(類__2__包渾沌=="爻"){
                    def 爻名 = "陽"

                    def ans_115 = 實
                    if(!ans_115){
                        爻名 = "陰"

                    }
                    def ans_116 = 渾沌語+引起
                    def ans_117 = ans_116+爻名
                    def ans_118 = ans_117+引迄
                    def ans_119 = ans_118+抬符
                    渾沌語 = ans_119

                }else
                if(類__2__包渾沌=="數"){
                    def ans_120 = 包數(實)
                    def 數名 = ans_120

                    def ans_121 = 渾沌語+引起
                    def ans_122 = ans_121+數名
                    def ans_123 = ans_122+引迄
                    def ans_124 = ans_123+抬符
                    渾沌語 = ans_124

                }
                return 渾沌語
        }
        def 包列 = {
            渾沌列__2__包渾沌,挪抬->
                def 渾沌語 = ''

                for(實 in 渾沌列__2__包渾沌){
                    def ans_125 = 識類(實)
                    def 類__2__包渾沌 = ans_125

                    for(_ans4 in 1..挪抬){
                        def ans_126 = 渾沌語+挪符
                        渾沌語 = ans_126

                    }
                    def ans_127 = 渾沌語+類__2__包渾沌
                    渾沌語 = ans_127

                    def ans_128 = 暗包渾沌(類__2__包渾沌,實,挪抬)
                    def ans_129 = ans_128+渾沌語
                    渾沌語 = ans_129

                }
                return 渾沌語
        }
        def 包物 = {
            渾沌物__2__包渾沌,挪抬->
                def 渾沌語 = ''

                def ans_130 = 列物之端(渾沌物__2__包渾沌)
                def 諸端 = ans_130

                for(端 in 諸端){
                    def ans_131 = 取物(渾沌物__2__包渾沌,端)
                    def 實 = ans_131

                    def ans_132 = 識類(實)
                    def 類__2__包渾沌 = ans_132

                    for(_ans5 in 1..挪抬){
                        def ans_133 = 渾沌語+挪符
                        渾沌語 = ans_133

                    }
                    def ans_134 = 渾沌語+"之"
                    def ans_135 = ans_134+引起
                    def ans_136 = ans_135+端
                    def ans_137 = ans_136+引迄
                    def ans_138 = ans_137+類__2__包渾沌
                    渾沌語 = ans_138

                    def ans_139 = 暗包渾沌(類__2__包渾沌,實,挪抬)
                    def ans_140 = ans_139+渾沌語
                    渾沌語 = ans_140

                }
                return 渾沌語
        }
        def ans_141 = 包物(渾沌物__1__包渾沌,1)
        def 餛飩語 = ans_141

        def ans_142 = 抬符+餛飩語
        def ans_143 = ans_142+"物"
        def ans_144 = ans_143+"也"
        return ans_144
}