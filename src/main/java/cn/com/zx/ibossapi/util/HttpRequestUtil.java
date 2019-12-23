package cn.com.zx.ibossapi.util;

import cn.com.zx.ibossapi.result.Result;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Http请求工具类
 *
 * @author lvxuezhan
 * @version 2017-10-18 15:38.
 */
public class HttpRequestUtil {

    /**
     * 发起get请求获取返回结果
     *
     * @param requestUrl 请求地址
     * @param clazz      返回类型
     * @param <T>
     * @return
     */
    public static <T> T getRequest(String requestUrl, Class<T> clazz) {
        String ret = getRequest(requestUrl);
        return JSON.parseObject(ret, clazz);
    }

    /**
     * 发起get请求获取返回结果
     *
     * @param requestUrl 请求地址
     * @param clazz      返回类型
     * @param <T>
     * @return
     */
    public static <T> List<T> getRequest2List(String requestUrl, Class<T> clazz) {
        String ret = getRequest(requestUrl);
        return JSON.parseArray(ret, clazz);
    }

    /**
     * 发起get请求获取返回结果
     *
     * @param requestUrl 请求地址
     * @return
     */
    public static String getRequest(String requestUrl) {
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            connection.disconnect();
        } catch (Exception ex) {
            LogUtil.getLogger(HttpRequestUtil.class).error(ex.getMessage(), ex);
            return null;
        }
        return sb.toString();
    }

    /**
     * 发起post请求获取返回结果
     *
     * @param requestUrl 请求地址
     * @param params     请求参数(json格式)
     * @param clazz      返回类型
     * @param <T>
     * @return
     */
    public static <T> T postRequest(String requestUrl, String params, Class<T> clazz) {
        String ret = postRequest(requestUrl, params);
        return JSON.parseObject(ret, clazz);
    }

    /**
     * 发起post请求获取返回结果
     *
     * @param requestUrl 请求地址
     * @param params     请求参数(json格式)
     * @return
     */
    public static String postRequest(String requestUrl, String params) {
        byte[] data = params.getBytes();
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-length", String.valueOf(data.length));
            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            printout.write(data);
            printout.flush();
            printout.close();
            inputStream = connection.getInputStream();
            byte[] bis = IOUtils.toByteArray(inputStream);
            String responseString = new String(bis, "UTF-8");
            return responseString;
        } catch (Exception ex) {
            LogUtil.getLogger(HttpRequestUtil.class).error(ex.getMessage(), ex);
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (Exception ex) {
                LogUtil.getLogger(HttpRequestUtil.class).error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        String ipAddress = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        String dot = ".";
        int minIpAddressLength = 15;
        String localhost = "127.0.0.1";
        String localhostV6 = "0:0:0:0:0:0:0:1";

        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();

            if (localhost.equals(ipAddress) || localhostV6.equals(ipAddress)) {
                //根据网卡获取本机配置的IP地址
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    LogUtil.getLogger(HttpRequestUtil.class).error(e.getMessage(), e);
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
        if (null != ipAddress && ipAddress.length() > minIpAddressLength) {
            //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(dot) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        return ipAddress;
    }

    /**
     * 包装返回结果
     *
     * @param response response
     * @param result   result
     */
    public static void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            LogUtil.getLogger(HttpRequestUtil.class).error(ex.getMessage(), ex);
        }
    }
}
