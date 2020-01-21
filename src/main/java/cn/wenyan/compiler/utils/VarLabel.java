package cn.wenyan.compiler.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VarLabel {


    private String name;

    private Map<String,String> alis;

    private Map<String,String> lastName;

    public VarLabel() {
        this.alis = new HashMap<>();
        this.lastName = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void addAlis(String funcName,int index,String ali) {
        alis.put(funcName+"@"+index,ali);
    }

    public String getAlis(String funcName,int ind,boolean defineArg){
        String index = funcName+"@"+ind;
        if(defineArg){
            if(!alis.containsKey(index)){
                String n = name+"__"+ind+"__"+funcName;
                alis.put(index,n);
                lastName.put(index,n);
            }
        }else{
            if(!alis.containsKey(index)){
                String ln = lastName.get(index);
                alis.put(index,ln==null?name:ln);
                lastName.put(index,name);
            }
        }
        return alis.get(index);
    }

    private String getIndex(int ind){
        ind = ind-1;
        Set<String> set = alis.keySet();
        for(String  s : set){
            if(ind == Integer.parseInt(s.split("@")[1])){
                return s;
            }
        }
        return null;
    }
}
