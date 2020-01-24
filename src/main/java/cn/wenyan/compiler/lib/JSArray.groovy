package cn.wenyan.compiler.lib

import java.util.Map.Entry

class JSArray<V> extends HashMap<Object,V> {

    private Integer index = 0

    @Override
    def put(Object key,value) {

        if(key == null){
            return super.put((index++) ,value)
        }else{
            return super.put(key, value)
        }
    }

    def add(V value){
        return put(null,value)
    }

    @Override
    def get(Object key) {
        def obj = super.get(key)
        if(obj == null)throw new ArrayIndexOutOfBoundsException(key.toString())
        return obj
    }

    JSArray<V> slice(int index){
        if(index > this.size())return new JSArray<>()
        JSArray jsArray = (JSArray) this.clone()
        for(int i = 0;i<index;i++){
            jsArray.remove(i)
        }
        return jsArray
    }
    @Override
    def remove(Object key1) {
        String key = key1.toString()
        if(key.matches("(-|)[0-9]+")){
            Integer k = Integer.parseInt(key)
            if(k >= 0){
                def obj = super.remove(key1)
                List<Entry<Object,V>> set = new ArrayList<>(this.entrySet())
                for(int i = 0;i<set.size();i++){
                    Object bKey = set.get(i).getKey()
                    if(bKey.toString().matches("[0-9]+")){
                        Integer b = Integer.parseInt(bKey.toString())
                        if(b >= k){
                            put(b-1,super.remove(b))
                        }
                    }
                }
                return obj
            }
            return super.remove(k)
        }
        return super.remove(key)
    }

    @Override
    String toString() {
        StringBuilder builder = new StringBuilder()
        List<Entry<Object,V>> list = new ArrayList<>(entrySet())
        for(int i = 0;i<list.size();i++){
            Entry<Object,V> entry = list.get(i)
            if(entry.getKey().toString().matches("[0-9]+")){
                builder.append(entry.getValue()).append(",")
            }else{
                builder.append(entry.getKey()).append(":").append(entry.getValue()).append(",")
            }
        }
        int index = builder.lastIndexOf(",")
        if(index!=-1) {
            return builder.substring(0, index)
        }else{
            return builder.toString()
        }
    }

    List<Object> keys(){
        return new ArrayList<>(this.keySet())
    }
}
