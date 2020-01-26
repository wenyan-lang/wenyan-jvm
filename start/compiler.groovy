class A{
    static f(a){
        def f = this.&f
        println(f)
    }
}
def f = new A()
println(f.f(1))