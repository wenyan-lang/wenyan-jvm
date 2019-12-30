# wenyan-lang_jvm
You can run WenYan Programming Language in JVM.

> 关于作者

作者由于为一高中生，所以不能很快实现全部，并且不能非常严谨的实现，不能确保全部
来自javascript版的wenyan脚本通过编译

> 关于项目

语法源: https://github.com/LingDong-/wenyan-lang 

本项目的目标语言是groovy,以实现动态语言，主要是为了实现
文言lang可以调用java库或groovy库，以实现在虚拟机运行。

java版的编译器是按行编译，所以不支持没有标点分割和不分行，若有有意者，可以实现它

> 与javascript版本的区别

1. 语法相对严格一些，由于实现的原理不同所导致。
2. 区别主要体现在标点必须是'。'分割。
> 目前状态

目前还在开发过程

> 目前实现的语法


| wenyan | groovy |
|---|---|
|`吾有一數。曰三。名之曰「甲」。` | `def jia=3` |
|`吾有一言。曰「「噫吁戲」」。名之曰「乙」。`|`def yi = '噫吁戲'`|
|`吾有一爻。曰陰。名之曰「丙」。` | `def bing = false` |
|`吾有三數。曰一。曰三。曰五。名之曰「甲」曰「乙」曰「丙」。` | `def jia = 1,yi=3,bing=5` |
|`吾有一數。曰五。書之`| `def ans=5 println(ans)`|
|`吾有一言。曰「乙」。書之`|`println(yi)`|
![image](images/program.png)
