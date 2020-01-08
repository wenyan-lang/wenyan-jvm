import cn.wenyan.compiler.WenYanTools

def compiler = WenYanTools.makeCompiler()
def javaClass = compiler.compileToClass("HelloWorld","吾有一言，曰『问天地好在』，書之。")
javaClass.getDeclaredMethod("run").invoke(javaClass.newInstance())
