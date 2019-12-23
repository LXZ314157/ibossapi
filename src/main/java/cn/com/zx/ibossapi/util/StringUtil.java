package cn.com.zx.ibossapi.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author lvxuezhan
 */
public class StringUtil {
    private final static String DOT = ".";
    public static final String[] FILTER =
            {"exec", "insert", "delete", "update", "'", "drop", "alter", "truncate", "-", "declare"};

    /**
     * 字符串内容格式化输出，内部使用{0}\{1}\{2}...为参数占位符
     *
     * @param msg  格式化模板
     * @param args 不固定参数
     * @return
     */
    public static String gsFormat(String msg, Object... args) {
        return MessageFormat.format(msg, args);
    }

    /**
     * 用于产生JSON字符串
     *
     * @param obj
     */
    public static String toSimpleString(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 从InputStream中读取String
     *
     * @param inputStream
     * @return
     */
    public static String convertStream2Json(InputStream inputStream) {
        String str = "";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            str = new String(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 获取36位随机字符串
     * @return
     */
    public static String getRandomUUID(){
        return UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
    }
    /**
     * 下划线转驼峰 首字母小写
     */

    public static String underline2CamelLower(String tableName){
        StringBuilder result=new StringBuilder();
        String a[]=tableName.split("_");
        for(String s:a){
            if (!tableName.contains("_")) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
    /**
     * 下划线转驼峰 首字母大写
     */

    public static String underline2CamelUpper(String tableName){
        StringBuilder result=new StringBuilder();
        String a[]=tableName.split("_");
        for(String s:a){
            if (!tableName.contains("_")) {
                result.append(s);
                continue;
            }
            result.append(s.substring(0, 1).toUpperCase());
            result.append(s.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * @param str
     * @return 是否为小数
     */
    public static boolean isDecimal(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    /**
     * 给定一个带后缀的文件名，返回去掉文件名后缀以后的字符串，如果这个字符串带下划线，
     * 则会去掉最后一个下划线及以后的字符。如果这个字符串不带后缀，则返回字符串本身
     *
     * @param fileName
     * @return
     */
    public static String getProductCode(String fileName) {
        String productCode;
        String file;
        if (StringUtils.isBlank(fileName)) {
            return "";
        } else if (fileName.contains(DOT)) {
            file = fileName.substring(0, fileName.lastIndexOf(DOT));
        } else {
            file = fileName;
        }
        String[] a = file.split("_");
        if (a.length != 0) {
            productCode = a[0];
        } else {
            productCode = file;
        }
        return productCode;
    }

    /**
     * 验证sql注入
     *
     * @param str
     * @return true 代表存在非法字符
     */
    public static boolean checkInjection(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        for (String word : FILTER) {
            if (str.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public static String removeUnderline(String str){
        String result = "";
        StringBuilder sb=new StringBuilder();
        if(str.indexOf("_")==-1){
            result = str;
        }else{
            String [] namesArray = str.split("_");
            for(int i=1;i<namesArray.length;i++){
                sb.append(namesArray[i]);
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     * 获取评论编号--以当前时间的方式
     * @return
     */
    public static String getRandomCmtNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String cmtNo = "cmt_"+sdf.format(new Date()).replaceAll("-","").replaceAll(":","").replaceAll(" ","");
        return cmtNo.substring(0,cmtNo.indexOf("."))+cmtNo.substring(cmtNo.indexOf(".")+1,cmtNo.indexOf(".")+4);
    }

    /**
     * 获取举报编号--以当前时间的方式
     * @return
     */
    public static String getRandomAccuNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String cmtNo = "accu_"+sdf.format(new Date()).replaceAll("-","").replaceAll(":","").replaceAll(" ","");
        return cmtNo.substring(0,cmtNo.indexOf("."))+cmtNo.substring(cmtNo.indexOf(".")+1,cmtNo.indexOf(".")+4);
    }

}
