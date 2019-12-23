package cn.com.zx.ibossapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author lvxuezhan
 */
public class LogUtil {


    /**
     * 获取logger
     *
     * @param clazz 当前类
     * @return
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}