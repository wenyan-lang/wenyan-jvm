package cn.wenyan.compiler.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileFormat {

    public List<String> formatEnd(File file){
        try {
            List<String> strings = FileUtils.readLines(file, System.getProperty("file.coding"));
            for(int i = 0;i<strings.size();i++){
                if(!strings.get(i).endsWith("。")){
                    strings.set(i,strings.get(i)+"。");
                }
            }
            return strings;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
