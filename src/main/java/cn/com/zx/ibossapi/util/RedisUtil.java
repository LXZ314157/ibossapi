package cn.com.zx.ibossapi.util;

/**
 * @author lvxuezhan
 * @date 2019/7/15
 **/
public class RedisUtil {

    public static String getSupportKey(Integer sType,Integer sId) {
        String suppoertKey = "";
        switch (sType){
            case 1 : suppoertKey = "expertArticle_"+String.valueOf(sId);break;
            case 2 : suppoertKey = "expertVideo_"+String.valueOf(sId);break;
            case 3 : suppoertKey = "expertCourse_"+String.valueOf(sId);break;
            case 4 : suppoertKey = "educateCourse_"+String.valueOf(sId);break;
        }
        return suppoertKey;
    }

}
