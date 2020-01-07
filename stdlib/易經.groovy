import cn.wenyan.compiler.lib.JSArray
def 圖靈機(諸律,始態,終態){
    def 帶=new JSArray()
    帶.put(null,'白')

    def 針=1
    def 左疆=1,右疆=1
    def 態 = 始態
    def 圖靈機畫法 = {

        def 畫帶=''
        def 筆 = 左疆
        while(true){
            if(筆>右疆){
                break
            }
            def ans_1=帶[(筆.class == java.lang.Integer.class?筆-1:筆)]

            def 符 = ans_1
            if(針==筆){
                def ans_2='〔'+符
                def ans_3=ans_2+'〕'
                符 = ans_3

            }else{
                def ans_4='、'+符
                def ans_5=ans_4+'、'
                符 = ans_5

            }
            def ans_6=畫帶+符
            畫帶 = ans_6

            def ans_7=1+筆
            筆 = ans_7

        }
        println(態+'《'+畫帶+'》')
    }
    def ans_8=圖靈機畫法()

    while(true){
        for(律 in 諸律){
            println((律.class == HashMap.Node.class?律.getValue():律)[0])
            def ans_9=律[(1-1)]
            def 甲態 = ans_9
            def ans_10=律[2-1]
            def 讀符 = ans_10
            def ans_11=律[3-1]
            def 乙態 = ans_11
            def ans_12=律[4-1]
            def 畫符 = ans_12
            def ans_13=律[5-1]
            def 移 = ans_13
            if(態==甲態){
                if(帶[(針.class == java.lang.Integer.class?針-1:針)]==讀符){
                    帶[(針.class == java.lang.Integer.class?針-1:針)] = 畫符

                    態 = 乙態

                    if(移=='左'){
                        def ans_14=針-1
                        針 = ans_14

                    }
                    if(移=='右'){
                        def ans_15=針+1
                        針 = ans_15

                    }
                    break
                }
            }
        }
        if(針<左疆){
            帶[(針.class == java.lang.Integer.class?針-1:針)] = '白'

            左疆 = 針

        }
        if(針>右疆){
            帶[(針.class == java.lang.Integer.class?針-1:針)] = '白'

            右疆 = 針

        }
        def ans_16=圖靈機畫法()

        if(態==終態){
            break
        }
    }
}
def 製律(諸律,甲態,讀符,乙態,畫符,移){
    def 律=new JSArray()
    律.put(null,甲態)
    律.put(null,讀符)
    律.put(null,乙態)
    律.put(null,畫符)
    律.put(null,移)

    諸律.put(null,律)

}
println('營營河狸。止于樊。')
def 諸律=new JSArray()
def 陽符 = '墨'
def 陰符 = '白'
def ans_17=製律(諸律,'甲',陰符,'乙',陽符,'右')
def ans_18=製律(諸律,'甲',陽符,'丙',陽符,'左')
def ans_19=製律(諸律,'乙',陰符,'甲',陽符,'左')
def ans_20=製律(諸律,'乙',陽符,'乙',陽符,'右')
def ans_21=製律(諸律,'丙',陰符,'乙',陽符,'左')
def ans_22=製律(諸律,'丙',陽符,'樊',陽符,'中')
def ans_23=圖靈機(諸律,'甲','樊')
