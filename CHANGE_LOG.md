Wenyan4j's progress will be explained here

> 1.17 10:14

support functional programming

> 1.17 9:54

fixed bug about the macro

fixed bug about the string

> 1.16 11:28

implemented the object

> 1.15 11:54
implemented the Exception/Try-Catch

> 1.15 9:08

implemented the Nested Function Calls

> 1.15 3:55

implemented the macro across files

> 1.15 3:15

Support for macros, macros are being implemented across files
```
或云「「書「甲」焉」」。
蓋謂「「吾有一言。曰「甲」。書之」」。

書「「甲」」焉

```
> 1.14

Current progress
Not yet implemented the `Macro`,` Exception`, `Object`,` Nested Function Calls`

The syntax mentioned in the rest of the readme and the syntax I have found so far have been implemented.

At the same time, the grammar is customized and can be freely translated into other languages.

```groovy
enum Language {

    GROOVY(
            [
                    (Syntax.VAR_DEFINE)           : "def $NAME = $VALUE",
                    (Syntax.FOR_NUMBER)            : "for(_ans$INDEX in 1..$RANGE){",
  ...........

```

And added project managerfor java-based projects, with a preliminary project structure
```java
String project = "/Users/luchangcun/Projects/michel/wenyan-lang_jvm/project_example";
        String makeFile = project+"/MakeFile.txt";
        String out = project+"/target";
        WenYanCompiler compiler = WenYanTools.makeCompiler(Language.GROOVY);
        compiler.compile(
                "-c","@"+makeFile,out,"-sc",project+"/src/","-m","main.主文件"
        );
```
Implemented WenYanShell, and enabled command line interaction via `java -jar wenyan.jar shell`
```
/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=50860:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:/Users/luchangcun/Projects/michel/wenyan-lang_jvm/target/classes:/Users/luchangcun/.m2/repository/com/github/houbb/opencc4j/1.0.2/opencc4j-1.0.2.jar:/Users/luchangcun/.m2/repository/com/github/houbb/paradise-common/1.1.3/paradise-common-1.1.3.jar:/Users/luchangcun/.m2/repository/com/huaban/jieba-analysis/1.0.2/jieba-analysis-1.0.2.jar:/Users/luchangcun/.m2/repository/org/apache/commons/commons-lang3/3.3.1/commons-lang3-3.3.1.jar:/Users/luchangcun/.m2/repository/org/scala-lang/scala-library/2.13.0/scala-library-2.13.0.jar:/Users/luchangcun/.m2/repository/org/scala-lang/scala-compiler/2.13.0/scala-compiler-2.13.0.jar:/Users/luchangcun/.m2/repository/jline/jline/2.14.6/jline-2.14.6.jar:/Users/luchangcun/.m2/repository/org/scala-lang/scala-reflect/2.13.0/scala-reflect-2.13.0.jar:/Users/luchangcun/.m2/repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar:/Users/luchangcun/.m2/repository/org/codehaus/groovy/groovy-all/2.3.11/groovy-all-2.3.11.jar:/Users/luchangcun/.m2/repository/commons-io/commons-io/2.6/commons-io-2.6.jar:/Users/luchangcun/.m2/repository/org/hamcrest/hamcrest-core/1.2/hamcrest-core-1.2.jar:/Users/luchangcun/Projects/michel/wenyan-lang_jvm/lib/pinyin4j-2.5.0.jar:/Users/luchangcun/.m2/repository/junit/junit/4.12/junit-4.12.jar cn.wenyan.compiler.WenYanShell
[55:c.w.c.WenYanShell]20:14:29[INFO	] WenYan Lang JVM Compiler
[55:c.w.c.WenYanShell]20:14:29[INFO	] @CopyRight wy-lang.org || github: https://github.com/LingDong-/wenyan-lang
[55:c.w.c.WenYanShell]20:14:29[INFO	] WenYan 3rd Party Compiler : github: https://github.com/MagicLu550/wenyan-lang_jvm/blob/master/README.md
[55:c.w.c.WenYanShell]20:14:29[INFO	] 文言文语言的语法规则最终由LingDong的wenyan-lang为基本要素
[55:c.w.c.WenYanShell]20:14:30[INFO	] WenYan Lang - Shell: @CopyRight wy-lang.org v 1.0
===> cn.wenyan.compiler.lib.JSArray
===> cn.wenyan.compiler.lib.JSArray, static cn.wenyan.compiler.lib.ArrayUtils.getArray
===> cn.wenyan.compiler.lib.JSArray, static cn.wenyan.compiler.lib.ArrayUtils.getArray, static cn.wenyan.compiler.lib.ArrayUtils.getIndex
>吾有一術。名之曰「曼德博」。欲行是術。必先得二數。曰「寬」。曰「高」。乃行是術曰。
	批曰。「「曼德博集。亦稱曼德布洛特复数集合。复平面上组成分形之点之集合也。」」

	吾有一言。曰「「丁龘蠹臺龜畫龍淼蔑高五三二」」。名之曰「皴法」。
	批曰。「「皴法者。圖畫之法也」」。

	減零以一。名之曰「上」。加零以一。名之曰「下」。
	減零以二。名之曰「左」。加零以二。名之曰「右」。

	減「右」以「左」。除其以「寬」。名之曰「橫步」。
	減「下」以「上」。除其以「高」。名之曰「縱步」。

	有數零。名之曰「戊」。恆為是。若「戊」等於「高」者乃止也。

		乘「縱步」以「戊」。加其以「上」。以名之曰「虛」。

		吾有一言。名之曰「行」。

		有數零。名之曰「戌」。恆為是。若「戌」等於「寬」者乃止也。

			乘「橫步」以「戌」。加其以「左」。名之曰「實」。

			有數「虛」。名之曰「虛虛」。
			有數「實」。名之曰「實實」。

			批曰。「「凡每一像素。皆算令其收斂之最大疊代數」」。

			有數零。名之曰「己」。恆為是。若「己」等於十二者乃止也。
				
				乘「實實」以「實實」。乘「虛虛」以「虛虛」。名之曰「甲」。曰「乙」。
				加「甲」以「乙」。名之曰「丙」。
				若「丙」大於四者乃止也。

				乘「虛虛」以「實實」。乘其以二。加其以「虛」。昔之「虛虛」者。今其是矣。
				減「甲」以「乙」。加其以「實」。昔之「實實」者。今其是矣。

			加一以「己」。昔之「己」者。今其是矣云云。

			批曰。「「既得疊代數。乃以皴法圖之」」。

			減十三以「己」。名之曰「巳」。
			夫「皴法」之「巳」。名之曰「墨」。
			加「行」以「墨」。昔之「行」者。今其是矣。

		加一以「戌」。昔之「戌」者。今其是矣云云。
		
		吾有一言。曰「行」。書之。
	加一以「戊」。昔之「戊」者。今其是矣云云。
是謂「曼德博」之術也。

批曰。「「畫曼德博集合之法。至是盡矣。乃一試之」」。

施「曼德博」於五十九。於二十四。

吾有一術。名之曰「曼德博」。欲行是術。必先得二數。曰「寬」。曰「高」。乃行是術曰。
[55:c.w.c.WenYanShell]20:14:59[INFO	] 
0: 吾有
1: 一術
2: 名之曰「曼德博」
3: 欲行是術
4: 必先得
5: 二數
6: 曰「寬」
7: 曰「高」
8: 乃行是術曰

... 0    批曰。「「曼德博集。亦稱曼德布洛特复数集合。复平面上组成分形之点之集合也。」」
[55:c.w.c.WenYanShell]20:14:59[INFO	] 
0: 批曰
1: 「「曼德博集。亦稱曼德布洛特复数集合。复平面上组成分形之点之集合也。」」


... 1
[55:c.w.c.WenYanShell]20:14:59[INFO	] 

... 2    吾有一言。曰「「丁龘蠹臺龜畫龍淼蔑高五三二」」。名之曰「皴法」。
[55:c.w.c.WenYanShell]20:14:59[INFO	] 
0: 吾有
1: 一言
2: 曰「「丁龘蠹臺龜畫龍淼蔑高五三二」」
3: 名之曰「皴法」

... 3    批曰。「「皴法者。圖畫之法也」」。
[55:c.w.c.WenYanShell]20:14:59[INFO	] 
0: 批曰
1: 「「皴法者。圖畫之法也」」

... 4
[55:c.w.c.WenYanShell]20:14:59[INFO	] 

... 5    減零以一。名之曰「上」。加零以一。名之曰「下」。
[55:c.w.c.WenYanShell]20:14:59[INFO	] 
0: 減零
1: 以一
2: 名之曰「上」
3: 加零
4: 以一
5: 名之曰「下」

... 6    減零以二。名之曰「左」。加零以二。名之曰「右」。
[55:c.w.c.WenYanShell]20:14:59[INFO	] 
0: 減零
1: 以二
2: 名之曰「左」
3: 加零
4: 以二
5: 名之曰「右」

... 7
[55:c.w.c.WenYanShell]20:14:59[INFO	] 

... 8    減「右」以「左」。除其以「寬」。名之曰「橫步」。
[55:c.w.c.WenYanShell]20:14:59[INFO	] 
0: 減「右」
1: 以「左」
2: 除其
3: 以「寬」
4: 名之曰「橫步」

... 9    減「下」以「上」。除其以「高」。名之曰「縱步」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 減「下」
1: 以「上」
2: 除其
3: 以「高」
4: 名之曰「縱步」

... 10
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 11   有數零。名之曰「戊」。恆為是。若「戊」等於「高」者乃止也。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 有數零
1: 名之曰「戊」
2: 恆為是
3: 若「戊」等於「高」者
4: 乃止
5: 也

... 12
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 13           乘「縱步」以「戊」。加其以「上」。以名之曰「虛」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 乘「縱步」
1: 以「戊」
2: 加其
3: 以「上」
4: 以名之曰「虛」

... 14
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 15           吾有一言。名之曰「行」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 吾有
1: 一言
2: 名之曰「行」

... 16
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 17           有數零。名之曰「戌」。恆為是。若「戌」等於「寬」者乃止也。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 有數零
1: 名之曰「戌」
2: 恆為是
3: 若「戌」等於「寬」者
4: 乃止
5: 也

... 18
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 19                   乘「橫步」以「戌」。加其以「左」。名之曰「實」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 乘「橫步」
1: 以「戌」
2: 加其
3: 以「左」
4: 名之曰「實」

... 20
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 21                   有數「虛」。名之曰「虛虛」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 有數「虛」
1: 名之曰「虛虛」

... 22                   有數「實」。名之曰「實實」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 有數「實」
1: 名之曰「實實」

... 23
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 24                   批曰。「「凡每一像素。皆算令其收斂之最大疊代數」」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 批曰
1: 「「凡每一像素。皆算令其收斂之最大疊代數」」

... 25
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 26                   有數零。名之曰「己」。恆為是。若「己」等於十二者乃止也。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 有數零
1: 名之曰「己」
2: 恆為是
3: 若「己」等於十二者
4: 乃止
5: 也

... 27                           
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 28                           乘「實實」以「實實」。乘「虛虛」以「虛虛」。名之曰「甲」。曰「乙」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 乘「實實」
1: 以「實實」
2: 乘「虛虛」
3: 以「虛虛」
4: 名之曰「甲」
5: 曰「乙」

... 29                           加「甲」以「乙」。名之曰「丙」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 加「甲」
1: 以「乙」
2: 名之曰「丙」

... 30                           若「丙」大於四者乃止也。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 若「丙」大於四者
1: 乃止
2: 也

... 31
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 32                           乘「虛虛」以「實實」。乘其以二。加其以「虛」。昔之「虛虛」者。今其是矣。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 乘「虛虛」
1: 以「實實」
2: 乘其
3: 以二
4: 加其
5: 以「虛」
6: 昔之「虛虛」者
7: 今其
8: 是矣

... 33                           減「甲」以「乙」。加其以「實」。昔之「實實」者。今其是矣。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 減「甲」
1: 以「乙」
2: 加其
3: 以「實」
4: 昔之「實實」者
5: 今其
6: 是矣

... 34
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 35                   加一以「己」。昔之「己」者。今其是矣云云。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 加一
1: 以「己」
2: 昔之「己」者
3: 今其
4: 是矣
5: 云云

... 36
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 37                   批曰。「「既得疊代數。乃以皴法圖之」」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 批曰
1: 「「既得疊代數。乃以皴法圖之」」

... 38
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 39                   減十三以「己」。名之曰「巳」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 減十三
1: 以「己」
2: 名之曰「巳」

... 40                   夫「皴法」之「巳」。名之曰「墨」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 夫「皴法」之「巳」
1: 名之曰「墨」

... 41                   加「行」以「墨」。昔之「行」者。今其是矣。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 加「行」
1: 以「墨」
2: 昔之「行」者
3: 今其
4: 是矣

... 42
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 43           加一以「戌」。昔之「戌」者。今其是矣云云。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 加一
1: 以「戌」
2: 昔之「戌」者
3: 今其
4: 是矣
5: 云云

... 44           
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

... 45           吾有一言。曰「行」。書之。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 吾有
1: 一言
2: 曰「行」
3: 書之

... 46   加一以「戊」。昔之「戊」者。今其是矣云云。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 加一
1: 以「戊」
2: 昔之「戊」者
3: 今其
4: 是矣
5: 云云

... 47是謂「曼德博」之術也。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 是謂「曼德博」之術也

===> true
>
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

>批曰。「「畫曼德博集合之法。至是盡矣。乃一試之」」。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 批曰
1: 「「畫曼德博集合之法。至是盡矣。乃一試之」」

===> true
>
[55:c.w.c.WenYanShell]20:15:00[INFO	] 

>施「曼德博」於五十九。於二十四。
[55:c.w.c.WenYanShell]20:15:00[INFO	] 
0: 施「曼德博」
1: 於五十九
2: 於二十四

二二二二三三三三五五五五五五五五五五五五高高高高蔑蔑淼龜龜蠹蔑高高高五五五三三三三三三三三三三三三三三三三三三三二二二
二二二二三三三五五五五五五五五五五五高高高高高蔑蔑淼龍臺丁畫淼蔑高高高五五五五三三三三三三三三三三三三三三三三三二二二
二二二三三五五五五五五五五五五五五高高高高高蔑蔑淼龜丁丁丁丁龜蔑蔑蔑高高五五五五三三三三三三三三三三三三三三三三三二二
二二二三五五五五五五五五五五五高高高高高蔑淼淼淼龍龜丁丁丁丁龜龍淼蔑蔑蔑高五五五五三三三三三三三三三三三三三三三三二二
二二三五五五五五五五五五五五高高高高蔑蔑龍丁蠹龜丁丁丁丁丁丁丁蠹臺龍龍丁蔑高五五五五三三三三三三三三三三三三三三三三二
二二三五五五五五五五五五高高高蔑蔑蔑蔑淼畫丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁淼高五五五五五三三三三三三三三三三三三三三三二
二三五五五五五五五五高高蔑蔑蔑蔑蔑淼淼龍丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁龜淼蔑高五五五五三三三三三三三三三三三三三三三三
二三五五五五五高高高淼臺淼淼淼淼龍龍龍臺丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁臺畫蔑高五五五五五三三三三三三三三三三三三三三三
二五五五高高高高蔑蔑淼丁蠹龜丁臺龜畫龜丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁蔑高五五五五五三三三三三三三三三三三三三三三
二五高高高高高蔑蔑淼龍龜丁丁丁丁丁丁龘丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁蔑高高五五五五三三三三三三三三三三三三三三三
二高高高高蔑蔑蔑淼畫龜丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁龍蔑高高五五五五五三三三三三三三三三三三三三三
二高高高淼淼淼龍畫丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁龘淼蔑高高五五五五五三三三三三三三三三三三三三三
二丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁龘龍淼蔑高高五五五五五三三三三三三三三三三三三三三
二高高高淼淼淼龍畫丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁龘淼蔑高高五五五五五三三三三三三三三三三三三三三
二高高高高蔑蔑蔑淼畫龜丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁龍蔑高高五五五五五三三三三三三三三三三三三三三
二五高高高高高蔑蔑淼龍龜丁丁丁丁丁丁龘丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁蔑高高五五五五三三三三三三三三三三三三三三三
二五五五高高高高蔑蔑淼丁蠹龜丁臺龜畫龜丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁蔑高五五五五五三三三三三三三三三三三三三三三
二三五五五五五高高高淼臺淼淼淼淼龍龍龍臺丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁臺畫蔑高五五五五五三三三三三三三三三三三三三三三
二三五五五五五五五五高高蔑蔑蔑蔑蔑淼淼龍丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁龜淼蔑高五五五五三三三三三三三三三三三三三三三三
二二三五五五五五五五五五高高高蔑蔑蔑蔑淼畫丁丁丁丁丁丁丁丁丁丁丁丁丁丁丁淼高五五五五五三三三三三三三三三三三三三三三二
二二三五五五五五五五五五五五高高高高蔑蔑龍丁蠹龜丁丁丁丁丁丁丁蠹臺龍龍丁蔑高五五五五三三三三三三三三三三三三三三三三二
二二二三五五五五五五五五五五五高高高高高蔑淼淼淼龍龜丁丁丁丁龜龍淼蔑蔑蔑高五五五五三三三三三三三三三三三三三三三三二二
二二二三三五五五五五五五五五五五五高高高高高蔑蔑淼龜丁丁丁丁龜蔑蔑蔑高高五五五五三三三三三三三三三三三三三三三三三二二
二二二二三三三五五五五五五五五五五五高高高高高蔑蔑淼龍臺丁畫淼蔑高高高五五五五三三三三三三三三三三三三三三三三三二二二
===> null
>
[55:c.w.c.WenYanShell]20:15:03[INFO	] 

>
[55:c.w.c.WenYanShell]20:15:03[INFO	] 

>
```
