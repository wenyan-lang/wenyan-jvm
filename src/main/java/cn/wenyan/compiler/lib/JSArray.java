package cn.wenyan.compiler.lib;


import java.util.*;

public class JSArray<T> extends HashMap<Object,T> {

    private int index = 0;


    @Override
    public T put(Object key, T value) {

        if(key == null){
            return super.put((index++) ,value);
        }else{
            return super.put(key, value);
        }
    }

    public T add(T value){
        return put(null,value);
    }

    @Override
    public T get(Object key) {
        T obj = super.get(key);
        if(obj == null)throw new ArrayIndexOutOfBoundsException(key.toString());
        return obj;
    }

    public JSArray<T> slice(int index){
        if(index > this.size())return new JSArray<>();
        JSArray jsArray = (JSArray) this.clone();
        for(int i = 0;i<index;i++){
            jsArray.remove(i);
        }
        return jsArray;
    }
    @Override
    public T remove(Object key1) {
        String key = key1.toString();
        if(key.matches("(-|)[0-9]+")){
            Integer k = Integer.parseInt(key);
            if(k >= 0){
                T obj = super.remove(key1);
                List<Entry<Object,T>> set = new ArrayList<>(this.entrySet());
                for(int i = 0;i<set.size();i++){
                    Object bKey = set.get(i).getKey();
                    if(bKey.toString().matches("[0-9]+")){
                        Integer b = Integer.parseInt(bKey.toString());
                        if(b >= k){
                            put(b-1+"",super.remove(b));
                        }
                    }
                }
                return obj;
            }
            return super.remove(k);
        }
        return super.remove(key);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        List<Entry<Object,T>> list = new ArrayList<>(entrySet());
        for(int i = 0;i<list.size();i++){
            Entry<Object,T> entry = list.get(i);
            if(entry.getKey().toString().matches("[0-9]+")){
                builder.append(entry.getValue()).append(",");
            }else{
                builder.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            }
        }
        int index = builder.lastIndexOf(",");
        if(index!=-1) {
            return builder.substring(0, index) + "]";
        }else{
            return builder.append("]").toString();
        }
    }
}
