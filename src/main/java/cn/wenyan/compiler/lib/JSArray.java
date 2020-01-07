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

    @Override
    public T get(Object key) {
        return super.get(key);
    }

    @Override
    public T remove(Object key1) {
        String key = key1.toString();
        if(key.matches("-[0-9]+")){
            Integer k = Integer.parseInt(key);
            if(k >= 0){
                T obj = super.remove(key);
                Set<Entry<Object,T>> set = this.entrySet();
                for(Entry<Object,T> e : set){
                    Object bKey = e.getKey();
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
}
