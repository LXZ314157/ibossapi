package cn.com.zx.ibossapi.domain;

import java.util.*;

/**
 * Created by yonghu on 06/06/2017.
 */
public class WechatPay {

    public static String TRADE_TYPE_JSAPI = "JSAPI";

    public static String TRADE_TYPE_APP = "APP";

    private String MCHID ;
    private String appId="";
    private String prepayId;
    private String nonceStr;
    private String timestamp ;
    private String tradeTye;
    private String packages;
    private String paramSign;

    public String getMCHID() {
        return MCHID;
    }

    public void setMCHID(String MCHID) {
        this.MCHID = MCHID;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTradeTye() {
        return tradeTye;
    }

    public void setTradeTye(String tradeTye) {
        this.tradeTye = tradeTye;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }


    public void setParamSign(String paramSign) {
        this.paramSign = paramSign;
    }

    public String getJonsParam() {
        SortedMap<String, String> finalpackage  = getParam();
        StringBuffer sb = new StringBuffer();
        Set es = finalpackage.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append("\"").append(k).append("\" : \"").append(v).append("\",");
            }
        }

        if(TRADE_TYPE_JSAPI.equals(tradeTye)){
            sb.append("\"paySign\" : \"").append(paramSign).append("\"");
        } else{
            sb.append("\"sign\" : \"").append(paramSign).append("\"");
        }

        return sb.toString();

    }
/*

    public String getUrlsParam(){
        SortedMap<String, String> finalpackage = getParam();
        StringBuffer sb = new StringBuffer();
        Set es = finalpackage.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        return sb.toString();
    }
*/

    public SortedMap<String, String> getParam(){
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        if (TRADE_TYPE_JSAPI.equals(tradeTye)) {

            String packages = "prepay_id=" + prepayId;
            finalpackage.put("appId", appId);
            finalpackage.put("timeStamp", timestamp);
            finalpackage.put("nonceStr", nonceStr);
            finalpackage.put("package", packages);
            finalpackage.put("signType", "MD5");
        }else{
            finalpackage.put("appid", appId);
            finalpackage.put("partnerid", MCHID);
            finalpackage.put("prepayid", prepayId);
            finalpackage.put("package", "Sign=WXPay");
            finalpackage.put("noncestr", nonceStr);
            finalpackage.put("timestamp", timestamp);
        }
        return  finalpackage;
    }
}
