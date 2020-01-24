package cn.wenyan.compiler.lib

class ArrayUtils {
    static def getIndex(index){
        if(index instanceof Integer)return (Integer)index - 1
        if(index instanceof BigInteger)return (BigInteger)index -1
        return index
    }

    static def getArray(array){
        return array.getClass() == HashMap.Node.class?array.getValue():array
    }

    static JSArray toJSArray(array){
        JSArray js = new JSArray()
        for(v in array){
            js.add(v)
        }
        return js
    }

    static def slice(str){
        if(str.getClass().isArray()){
            def arr = Arrays.asList(str)
            return arr.subList(1,arr.size())
        }
        if(str instanceof List){
            def list = new ArrayList(str)
            return list.subList(1,list.size())
        }
        if(str instanceof JSArray){
            return str.slice(1)
        }
        if(str instanceof String){
            return str.substring(1)
        }
        throw new RuntimeException("No Such Function: slice")
    }

    static def length(str){
        if(str.getClass().isArray())return str.length
        if(str instanceof List) return str.size()
        if(str instanceof String) return str.length()
        if(str instanceof JSArray) return str.size()
        if(str instanceof Map) return str.size()
    }

}
