# wenyan-lang_jvm
You can run WenYan Programming Language in JVM.

语法源: https://github.com/LingDong-/wenyan-lang 

本项目的目标语言是groovy,以实现动态语言，主要是为了实现
文言lang可以调用java库或groovy库，以实现在虚拟机运行。
目前还在开发过程


| wenyan | groovy |
|---|---|
|`吾有一數。曰三。名之曰「甲」。` | `def jia=3` |
|`吾有一言。曰「「噫吁戲」」。名之曰「乙」。`|`def yi = '噫吁戲'`|
|`吾有一爻。曰陰。名之曰「丙」。` | `def bing = false` |
|`吾有三數。曰一。曰三。曰五。名之曰「甲」曰「乙」曰「丙」。` | `def jia = 1,yi=3,bing=5` |
|`吾有一數。曰五。書之`| `def ans=5 println(ans)`|
|`吾有一言。曰「乙」。`|`println(yi)`|
![image](images/program.png)
