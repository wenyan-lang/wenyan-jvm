try{
    Exception ans_1 = new Exception("""滅頂""")
    throw ans_1
}catch(ans_2){
    if(ans_2.message.equals("""ReferenceError""")){
        def ans_3 = ''+"""本無此物。奈何用之"""+''
        println(ans_3 .toString())
    }else if(ans_2.message.equals("""SyntaxError""")){
        def ans_4 = ''+"""不合文法。不通之甚"""+''
        println(ans_4 .toString())
    }else if(ans_2.message.equals("""TypeError""")){
        def ans_5 = ''+"""物各其類。豈可混同"""+''
        println(ans_5 .toString())
    }else if(ans_2.message.equals("""滅頂""")){
        def ans_6 = ''+"""嗚呼哀哉。伏维尚飨"""+''
        println(ans_6 .toString())
    }else{
        def ans_7 = ans_2
        def 奇禍 = ans_7

        def ans_8 = ''+"""人坐家中。禍從天降"""+''
        println(ans_8 .toString())
    }
}
try{
    Exception ans_9 = new Exception("""無足輕重""")
    throw ans_9
}catch(ans_10){
}
//}
try{
    Exception ans_11 = new Exception("""事不關心""")
    throw ans_11
}catch(ans_12){
//}else{
    def ans_13 = ans_12
    println(ans_13 .toString())
}
//}