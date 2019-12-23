package cn.com.zx.ibossapi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GetWxOrderno {
    private static Logger logger = LoggerFactory.getLogger(GetWxOrderno.class);


    public static DefaultHttpClient httpclient;

    static {
        ClientConnectionManager cm = new ThreadSafeClientConnManager();
        httpclient = new DefaultHttpClient(cm);
        httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient);
    }





    public static  String getOpenIdByCode(String url){
        HttpGet httpGet = HttpClientConnectionManager.getGetMethod(url);
        String openId = "";
        try {

            HttpResponse response = httpclient.execute(httpGet);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.info("url {} result {}", url, jsonStr);
            JSONObject jobj = JSON.parseObject(jsonStr);
            openId =  jobj.getString("openid");
        } catch (Exception e) {
            logger.warn("Got exception for url: {}", url, e);
        }
        return openId;

    }


    public static String getPayNo(String url, String xmlParam) {
        HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        String prepay_id = "";
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.info("url {} result {}", url, jsonStr);
            Map<String, String> map = doXMLParse(jsonStr);
            String returnCode =  map.get("return_code");
            if( returnCode.equals("SUCCESS")){
                String resultCode = map.get("result_code");
                if("SUCCESS".equals(resultCode)){
                    prepay_id =  map.get("prepay_id");
                }
            }
        } catch (Exception e) {
            logger.warn("Got exception for url: {}", url, e);
        }
        return prepay_id;
    }



    public static boolean queryOrder(String url, String xmlParam) {
        HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.info("url {} result {}", url, jsonStr);
            Map<String, String> map = doXMLParse(jsonStr);
            String returnCode =  map.get("return_code");
            if( returnCode.equals("SUCCESS")){
                String resultCode = map.get("result_code");
                if("SUCCESS".equals(resultCode)){
                    String tradeState = map.get("trade_state");
                    if("SUCCESS".equals(tradeState)){
                        return  true;
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("Got exception for url: {}", url, e);
        }
        return false;
    }

    /**
     * description:获取扫码支付连接
     *
     * @param url
     * @param xmlParam
     * @return
     * @author ex_yangxiaoyi
     * @see
     */
    public static String getCodeUrl(String url, String xmlParam) {
        HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        String code_url = "";
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (jsonStr.indexOf("FAIL") != -1) {
                return code_url;
            }
            Map map = doXMLParse(jsonStr);
            code_url = (String) map.get("code_url");
        } catch (Exception e) {
            logger.warn("Got exception for url: {}", url, e);
        }
        return code_url;
    }


    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Map<String, String> doXMLParse(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

}