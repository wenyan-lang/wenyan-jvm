//导包
import java.util.UUID

//定义变量
def var = 1

def uuid = UUID.randomUUID()

//类

class HelloWorld{
    int i = 0
}
//对象
def h = new HelloWorld()
//数组
def arr = []

//字典

def dic = [hello : "11" ]

arr[0] = 1

dic["hello"] = "12" //修改

dic["abc"] = "13" //放

println(dic) //输出

def helloworld_i = h.i

//闭包，可以理解为函数内定义函数
def name ={
    name ->
        println(name)
}

println(2+2*2)
println(2+2)

