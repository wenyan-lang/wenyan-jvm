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
        T obj = super.get(key);
        if(obj == null)throw new ArrayIndexOutOfBoundsException(key.toString());
        return obj;
    }

    public static void main(String[] args) {
        JSArray<String> jsArray = new JSArray<>();
        jsArray.put(null,"1");
        jsArray.put(null,"2");
        jsArray.put(null,"3");
        System.out.println(jsArray);
        System.out.println(jsArray.remove(0));
        System.out.println(jsArray);
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

}
