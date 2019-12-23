package cn.com.zx.ibossapi.util;

import java.io.File;
import java.io.IOException;

/**
 * @author lvxuezhan
 * @version 2019-03-17 16:55
 */
public class FileUtil {

    public static void createNewFile(String path){
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
