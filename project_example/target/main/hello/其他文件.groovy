package main.hello
import cn.wenyan.compiler.lib.*
import static cn.wenyan.compiler.lib.ArrayUtils.getArray
import static cn.wenyan.compiler.lib.ArrayUtils.getIndex
import static cn.wenyan.compiler.lib.MathUtil.mod

@Defines([	@Define(before = '左移「甲」以「乙」',after = '施「左移」於「甲」。於「乙」'),
	@Define(before = '观物「甲」为非数',after = '施「{x -> x.class == Integer.class}」於「甲」。'),
	@Define(before = '右移「甲」以「乙」',after = '施「右移」於「甲」。於「乙」'),
])
class 其他文件{







static def 左移 = {
 甲,乙->
def ans_34 = {x,y->x<<y}(甲,乙)
def 丙 = ans_34

return 丙
}
static def 右移 = {
 甲,乙->
def ans_35 = {x,y->x>>y}(甲,乙)
def 丙 = ans_35

return 丙
}
static def 補零右移 = {
 甲,乙->
def ans_36 = {x,y->(x%0x100000000)>>y}(甲,乙)
def 丙 = ans_36

return 丙
}
static def 位与 = {
 甲,乙->
def ans_37 = {x,y->x&y}(甲,乙)
def 丙 = ans_37

return 丙
}
static def 位或 = {
 甲,乙->
def ans_38 = {x,y->x|y}(甲,乙)
def 丙 = ans_38

return 丙
}
static def 异或 = {
 甲,乙->
def ans_39 = {x,y->x^y}(甲,乙)
def 丙 = ans_39

return 丙
}
static def 与非 = {
 甲,乙->
def ans_40 = {x,y->~(x&y)}(甲,乙)
def 丙 = ans_40

return 丙
}
static def 位變 = {
 甲->
def ans_41 = {x->~x}(甲)
def 乙 = ans_41

return 乙
}
}