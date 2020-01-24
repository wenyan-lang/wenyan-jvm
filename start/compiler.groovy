package cn.wenyan.lang
import cn.wenyan.compiler.lib.*
import static cn.wenyan.compiler.lib.ArrayUtils.getArray
import static cn.wenyan.compiler.lib.ArrayUtils.getIndex
import static cn.wenyan.compiler.lib.MathUtil.mod

class 渾沌經{

    static def 引號

    static def ans_744

    static def 引起

    static def ans_745

    static def 引迄

    static def 位名

    static def 斬渾沌(渾沌語){

            def 諸咒 = new JSArray()

            諸咒.add("""物""")
            諸咒.add("""言""")
            諸咒.add("""數""")
            諸咒.add("""爻""")
            諸咒.add("""列""")
            諸咒.add("""之""")
            諸咒.add("""也""")

            def 渾沌碎 = new JSArray()

            def 讀 = 1

            def 層 = 0

            def 辭 = ''

            while(true){
                if(讀>ArrayUtils.length(getArray(渾沌語))){
                    break
                }
                if(getArray(渾沌語)[getIndex(讀)]==引起){
                    if(層!=0){
                        def ans_746 = getArray(渾沌語)[getIndex(讀)]
                        def ans_747 = 辭+ans_746
                        辭 = ans_747

                    }
                    def ans_748 = 層+1
                    層 = ans_748

                }else
                if(getArray(渾沌語)[getIndex(讀)]==引迄){
                    def ans_749 = 層-1
                    層 = ans_749

                    if(層==0){
                        渾沌碎.add(辭)

                        辭 = """"""

                    }else{
                        def ans_750 = getArray(渾沌語)[getIndex(讀)]
                        def ans_751 = 辭+ans_750
                        辭 = ans_751

                    }
                }else
                if(層>0){
                    def ans_752 = getArray(渾沌語)[getIndex(讀)]
                    def ans_753 = 辭+ans_752
                    辭 = ans_753

                }else{
                    for(咒 in 諸咒){
                        if(getArray(渾沌語)[getIndex(讀)]==咒){
                            渾沌碎.add(咒)

                            break
                        }
                    }
                }
                def ans_754 = 1+讀
                讀 = ans_754

            }
            return 渾沌碎
    }
    static def 食渾沌 = {
        渾沌語__1__食渾沌->

            def ans_755 = 斬渾沌(渾沌語__1__食渾沌)
            def 渾沌碎 = ans_755

            def 食數
            def 食列
            def 食物
            食數 = {
                數名->
                    def 正負 = 1

                    def ans_756 = getArray(數名)[getIndex(1)]
                    if(ans_756=="""負"""){
                        def ans_757 = ArrayUtils.slice(getArray(數名))
                        數名 = ans_757

                        正負 = -1

                    }
                    def 整 = 0
                    def 小 = 0

                    def 讀 = 1
                    def 小長 = 1

                    def 小耶 = false

                    while(true){
                        if(讀>ArrayUtils.length(getArray(數名))){
                            break
                        }
                        if(讀=="""·"""){
                            小耶 = true

                        }else{
                            def 位 = 1

                            while(true){
                                if(位>ArrayUtils.length(getArray(位名))){
                                    break
                                }
                                if(getArray(位名)[getIndex(位)]==getArray(數名)[getIndex(讀)]){
                                    break
                                }
                                def ans_758 = 1+位
                                位 = ans_758

                            }
                            def ans_759 = 位-1
                            位 = ans_759

                            if(小耶){
                                def ans_760 = 小*10
                                def ans_761 = ans_760+位
                                小 = ans_761

                                def ans_762 = 小長+1
                                小長 = ans_762

                            }else{
                                def ans_763 = 整*10
                                def ans_764 = ans_763+位
                                整 = ans_764

                            }
                        }
                        def ans_765 = 1+讀
                        讀 = ans_765

                    }
                    for(_ans3 in 1..小長){
                        def ans_766 = 小*0.1
                        小 = ans_766

                    }
                    def ans_767 = 整+小
                    def ans_768 = ans_767*正負
                    return ans_768
            }
            食列 = {
                渾沌碎__2__食渾沌->
                    def 渾沌列 = new JSArray()

                    def 讀 = 1

                    while(true){
                        if(讀>ArrayUtils.length(getArray(渾沌碎__2__食渾沌))){
                            break
                        }
                        def ans_769 = getArray(渾沌碎__2__食渾沌)[getIndex(讀)]
                        def 類 = ans_769

                        if(類=="""數"""){
                            def ans_770 = 讀+1
                            def ans_771 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_770)]

                            def ans_772 = 食數(ans_771)
                            渾沌列.add(ans_772)

                            def ans_773 = 讀+2
                            讀 = ans_773

                        }else
                        if(類=="""言"""){
                            def ans_774 = 讀+1
                            def ans_775 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_774)]
                            渾沌列.add(ans_775)

                            def ans_776 = 讀+2
                            讀 = ans_776

                        }else
                        if(類=="""爻"""){
                            def ans_777 = 讀+1
                            def ans_778 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_777)]
                            if(ans_778=="""陰"""){
                                渾沌列.add(false)

                            }else{
                                渾沌列.add(true)

                            }
                            def ans_779 = 讀+2
                            讀 = ans_779

                        }else{
                            def 層 = 0

                            def ans_780 = 讀+1
                            def 次讀 = ans_780

                            def 句 = new JSArray()

                            while(true){
                                if(次讀>ArrayUtils.length(getArray(渾沌碎__2__食渾沌))){
                                    break
                                }
                                if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="""物"""){
                                    def ans_781 = 層+1
                                    層 = ans_781

                                }else
                                if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="""列"""){
                                    def ans_782 = 層+1
                                    層 = ans_782

                                }else
                                if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="""也"""){
                                    if(層==0){
                                        if(類=="""物"""){
                                            def ans_783 = 食物(句)
                                            渾沌列.add(ans_783)

                                        }else{
                                            def ans_784 = 食列(句)
                                            渾沌列.add(ans_784)

                                        }
                                        break
                                    }
                                    def ans_785 = 層-1
                                    層 = ans_785

                                }
                                def ans_786 = getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]
                                句.add(ans_786)

                                def ans_787 = 次讀+1
                                次讀 = ans_787

                            }
                            def ans_788 = 次讀+1
                            讀 = ans_788

                        }
                    }
                    return 渾沌列
            }
            食物 = {
                渾沌碎__2__食渾沌->
                    def 渾沌物 = [:]

                    def 讀 = 2

                    while(true){
                        if(讀>ArrayUtils.length(getArray(渾沌碎__2__食渾沌))){
                            break
                        }
                        def ans_789 = getArray(渾沌碎__2__食渾沌)[getIndex(讀)]
                        def 端 = ans_789

                        def ans_790 = 讀+1
                        def ans_791 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_790)]
                        def 類 = ans_791

                        if(類=="""數"""){
                            def ans_792 = 讀+2
                            def ans_793 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_792)]

                            def ans_794 = 食數(ans_793)
                            def ans_795 = 置物(渾沌物,端,ans_794)
                            def ans_796 = 讀+4
                            讀 = ans_796

                        }else
                        if(類=="""言"""){
                            def ans_797 = 讀+2
                            def ans_798 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_797)]
                            def ans_799 = 置物(渾沌物,端,ans_798)
                            def ans_800 = 讀+4
                            讀 = ans_800

                        }else
                        if(類=="""爻"""){
                            def ans_801 = 讀+2
                            def ans_802 = getArray(渾沌碎__2__食渾沌)[getIndex(ans_801)]
                            if(ans_802=="""陰"""){
                                def ans_803 = 置物(渾沌物,端,false)
                            }else{
                                def ans_804 = 置物(渾沌物,端,true)
                            }
                            def ans_805 = 讀+4
                            讀 = ans_805

                        }else{
                            def 層 = 0

                            def ans_806 = 讀+2
                            def 次讀 = ans_806

                            def 句 = new JSArray()

                            while(true){
                                if(次讀>ArrayUtils.length(getArray(渾沌碎__2__食渾沌))){
                                    break
                                }
                                if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="""物"""){
                                    def ans_807 = 層+1
                                    層 = ans_807

                                }else
                                if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="""列"""){
                                    def ans_808 = 層+1
                                    層 = ans_808

                                }else
                                if(getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]=="""也"""){
                                    if(層==0){
                                        if(類=="""物"""){
                                            def ans_809 = 食物(句)
                                            def ans_810 = 置物(渾沌物,端,ans_809)
                                        }else{
                                            def ans_811 = 食列(句)
                                            def ans_812 = 置物(渾沌物,端,ans_811)
                                        }
                                        break
                                    }
                                    def ans_813 = 層-1
                                    層 = ans_813

                                }
                                def ans_814 = getArray(渾沌碎__2__食渾沌)[getIndex(次讀)]
                                句.add(ans_814)

                                def ans_815 = 次讀+1
                                次讀 = ans_815

                            }
                            def ans_816 = 次讀+2
                            讀 = ans_816

                        }
                    }
                    return 渾沌物
            }
            def ans_817 = ArrayUtils.slice(getArray(渾沌碎))

            def ans_818 = 食物(ans_817)
            return ans_818
    }
    static def 包渾沌 = {
        渾沌物__1__包渾沌->

            def 挪符 = """　"""

            def 抬符 = """\n"""

            def 包數
            def 暗包渾沌
            def 包列
            def 包物
            包數 = {
                甲__2__包渾沌->
                    def 正負 = ''

                    if(甲__2__包渾沌<0){
                        def ans_819 = 甲__2__包渾沌*-1
                        甲__2__包渾沌 = ans_819

                        正負 = """負"""

                    }
                    def ans_820 = mod(甲__2__包渾沌,1)
                    def 小數 = ans_820

                    def ans_821 = 甲__2__包渾沌-小數
                    def 整數 = ans_821

                    def 小 = ''
                    def 整 = ''

                    while(true){
                        if(整數<=0){
                            break
                        }
                        def ans_822 = mod(整數,10)
                        def 位 = ans_822

                        def ans_823 = 位+1
                        def ans_824 = getArray(位名)[getIndex(ans_823)]
                        def ans_825 = ans_824+整
                        整 = ans_825

                        def ans_826 = 整數-位
                        def ans_827 = ans_826/10
                        整數 = ans_827

                    }
                    while(true){
                        if(小數<=0){
                            break
                        }
                        def ans_828 = 小數*10
                        小數 = ans_828

                        def ans_829 = mod(小數,1)
                        def 位 = ans_829

                        def ans_830 = 位+1
                        def ans_831 = getArray(位名)[getIndex(ans_830)]
                        def ans_832 = 小+ans_831
                        小 = ans_832

                        def ans_833 = 小數-位
                        小數 = ans_833

                    }
                    def ans_834 = 正負+整
                    整 = ans_834

                    def ans_835 = ArrayUtils.length(getArray(小))
                    if(ans_835){
                        def ans_836 = 整+"""·"""
                        def ans_837 = ans_836+小
                        return ans_837
                    }
                    return 整
            }
            暗包渾沌 = {
                類__2__包渾沌, 實__2__包渾沌, 挪抬->
                    def 渾沌語 = ''

                    if(類__2__包渾沌=="""物"""){
                        def ans_838 = 渾沌語+抬符
                        渾沌語 = ans_838

                        def ans_839 = 實__2__包渾沌
                        def ans_840 = 挪抬+1

                        def ans_841 = 包物(ans_839,ans_840)
                        def ans_842 = 渾沌語+ans_841
                        渾沌語 = ans_842

                        for(_ans4 in 1..挪抬){
                            def ans_843 = 渾沌語+挪符
                            渾沌語 = ans_843

                        }
                        def ans_844 = 渾沌語+"""也"""
                        def ans_845 = ans_844+抬符
                        渾沌語 = ans_845

                    }else
                    if(類__2__包渾沌=="""列"""){
                        def ans_846 = 渾沌語+抬符
                        渾沌語 = ans_846

                        def ans_847 = 實__2__包渾沌
                        def ans_848 = 挪抬+1

                        def ans_849 = 包列(ans_847,ans_848)
                        def ans_850 = 渾沌語+ans_849
                        渾沌語 = ans_850

                        for(_ans5 in 1..挪抬){
                            def ans_851 = 渾沌語+挪符
                            渾沌語 = ans_851

                        }
                        def ans_852 = 渾沌語+"""也"""
                        def ans_853 = ans_852+抬符
                        渾沌語 = ans_853

                    }else
                    if(類__2__包渾沌=="""言"""){
                        def ans_854 = 渾沌語+引起
                        def ans_855 = ans_854+實__2__包渾沌
                        def ans_856 = ans_855+引迄
                        def ans_857 = ans_856+抬符
                        渾沌語 = ans_857

                    }else
                    if(類__2__包渾沌=="""爻"""){
                        def 爻名 = """陽"""

                        def ans_858 = 實__2__包渾沌
                        if(!ans_858){
                            爻名 = """陰"""

                        }
                        def ans_859 = 渾沌語+引起
                        def ans_860 = ans_859+爻名
                        def ans_861 = ans_860+引迄
                        def ans_862 = ans_861+抬符
                        渾沌語 = ans_862

                    }else
                    if(類__2__包渾沌=="""數"""){
                        def ans_863 = 包數(實__2__包渾沌)
                        def 數名 = ans_863

                        def ans_864 = 渾沌語+引起
                        def ans_865 = ans_864+數名
                        def ans_866 = ans_865+引迄
                        def ans_867 = ans_866+抬符
                        渾沌語 = ans_867

                    }
                    return 渾沌語
            }
            包列 = {
                渾沌列__2__包渾沌, 挪抬->
                    def 渾沌語 = ''

                    for(實__2__包渾沌 in 渾沌列__2__包渾沌){
                        def ans_868 = 識類(實__2__包渾沌)
                        def 類__2__包渾沌 = ans_868

                        for(_ans6 in 1..挪抬){
                            def ans_869 = 渾沌語+挪符
                            渾沌語 = ans_869

                        }
                        def ans_870 = 渾沌語+類__2__包渾沌
                        渾沌語 = ans_870

                        def ans_871 = 暗包渾沌(類__2__包渾沌,實__2__包渾沌,挪抬)
                        def ans_872 = 渾沌語+ans_871
                        渾沌語 = ans_872

                    }
                    return 渾沌語
            }
            包物 = {
                渾沌物__2__包渾沌, 挪抬->
                    def 渾沌語 = ''

                    def ans_873 = 列物之端(渾沌物__2__包渾沌)
                    def 諸端 = ans_873

                    for(端 in 諸端){
                        def ans_874 = 取物(渾沌物__2__包渾沌,端)
                        def 實__2__包渾沌 = ans_874

                        def ans_875 = 識類(實__2__包渾沌)
                        def 類__2__包渾沌 = ans_875

                        for(_ans7 in 1..挪抬){
                            def ans_876 = 渾沌語+挪符
                            渾沌語 = ans_876

                        }
                        def ans_877 = 渾沌語+"""之"""
                        def ans_878 = ans_877+引起
                        def ans_879 = ans_878+端
                        def ans_880 = ans_879+引迄
                        def ans_881 = ans_880+類__2__包渾沌
                        渾沌語 = ans_881

                        def ans_882 = 暗包渾沌(類__2__包渾沌,實__2__包渾沌,挪抬)
                        def ans_883 = 渾沌語+ans_882
                        渾沌語 = ans_883

                    }
                    return 渾沌語
            }
            def ans_884 = 包物(渾沌物__1__包渾沌,1)
            def 餛飩語 = ans_884

            def ans_885 = 抬符+餛飩語
            def ans_886 = """物"""+ans_885
            def ans_887 = ans_886+"""也"""
            return ans_887
    }
    static{
/*

    此經乃改編者以順java之道

    參見 https://github.com/wenyan-lang/wenyan/tree/master/lib


*/
        引號= """「」"""
        ans_744= getArray(引號)[getIndex(1)]
        引起= ans_744
        ans_745= getArray(引號)[getIndex(2)]
        引迄= ans_745
        位名= new JSArray()
        位名.add("""〇""")
        位名.add("""一""")
        位名.add("""二""")
        位名.add("""三""")
        位名.add("""四""")
        位名.add("""五""")
        位名.add("""六""")
        位名.add("""七""")
        位名.add("""八""")
        位名.add("""九""")

    }
}
