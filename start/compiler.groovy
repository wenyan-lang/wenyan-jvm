import cn.wenyan.compiler.lib.JSArray
import static cn.wenyan.compiler.lib.ArrayUtils.getArray
import static cn.wenyan.compiler.lib.ArrayUtils.getIndex
def 圖靈機(JSArray 諸律,String 始態,String 終態){
    JSArray 帶 = new JSArray()


    int 針 = 1

    int 左疆 = 1
    int 右疆 = 1

    String 態 = 始態

    def 圖靈機畫法 = {

        String 畫帶 = ''

        int 筆 = 左疆

        while(true){
            if(筆>右疆){
                break
            }
            def ans_1 = getArray(帶)[getIndex(筆)]
            def 符 = ans_1

            if(針==筆){
                def ans_2 = '〔'+符
                def ans_3 = ans_2+'〕'
                符 = ans_3

            }else{
                def ans_4 = '、'+符
                def ans_5 = ans_4+'、'
                符 = ans_5

            }
            def ans_6 = 畫帶+符
            畫帶 = ans_6

            def ans_7 = 1+筆
            筆 = ans_7

        }
        def ans_8 = ''+態+''+'《'+''+畫帶+''+'》'+''
        println(ans_8)
    }
    def ans_9 = 圖靈機畫法()

    while(true){
        for(律 in 諸律){
            def ans_10 = getArray(律)[getIndex(1)]
            def 甲態 = ans_10

            def ans_11 = getArray(律)[getIndex(2)]
            def 讀符 = ans_11

            def ans_12 = getArray(律)[getIndex(3)]
            def 乙態 = ans_12

            def ans_13 = getArray(律)[getIndex(4)]
            def 畫符 = ans_13

            def ans_14 = getArray(律)[getIndex(5)]
            def 移 = ans_14

            if(態==甲態){
                if(getArray(帶)[getIndex(針)]==讀符){
                    getArray(帶)[getIndex(針)] = 畫符

                    態 = 乙態

                    if(移=='左'){
                        def ans_15 = 針-1
                        針 = ans_15

                    }
                    if(移=='右'){
                        def ans_16 = 針+1
                        針 = ans_16

                    }
                    break
                }
            }
        }
        if(針<左疆){
            getArray(帶)[getIndex(針)] = '白'

            左疆 = 針

        }
        if(針>右疆){
            getArray(帶)[getIndex(針)] = '白'

            右疆 = 針

        }
        def ans_17 = 圖靈機畫法()

        if(態==終態){
            break
        }
    }
}
def 製律(JSArray 諸律,String 甲態,String 讀符,String 乙態,String 畫符,String 移){
    JSArray 律 = new JSArray()

    律.add(讀符)
    律.add(乙態)
    律.add(畫符)
    律.add(移)


}
def ans_18 = ''+'營營河狸。止于樊。'+''
println(ans_18)
JSArray 諸律 = new JSArray()

String 陽符 = '墨'

String 陰符 = '白'

def ans_19 = 製律(諸律,'甲',陰符,'乙',陽符,'右')
def ans_20 = 製律(諸律,'甲',陽符,'丙',陽符,'左')
def ans_21 = 製律(諸律,'乙',陰符,'甲',陽符,'左')
def ans_22 = 製律(諸律,'乙',陽符,'乙',陽符,'右')
def ans_23 = 製律(諸律,'丙',陰符,'乙',陽符,'左')
def ans_24 = 製律(諸律,'丙',陽符,'樊',陽符,'中')
def ans_25 = 圖靈機(諸律,'甲','樊')