package cn.com.zx.ibossapi.service.wechatpay;

import cn.com.zx.ibossapi.common.CallbackResult;
import cn.com.zx.ibossapi.common.ChannelPayStatusEnum;
import cn.com.zx.ibossapi.common.Response;
import cn.com.zx.ibossapi.config.WechatPayConfig;
import cn.com.zx.ibossapi.domain.PayLogInfo;
import cn.com.zx.ibossapi.domain.WechatPay;
import cn.com.zx.ibossapi.util.GetWxOrderno;
import cn.com.zx.ibossapi.util.MD5Util;
import cn.com.zx.ibossapi.util.RequestHandler;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by guoyu on 2019/8/18.
 */
public abstract class AbstractWechatChannelService
//        implements ChannelService, ChannelPayService
{


    private static Logger logger = LoggerFactory.getLogger(AbstractWechatChannelService.class);

    private int WECHAT_BANK_ID = 23;

    private volatile WechatPayConfig wechatPayConfig;

    private RequestHandler getRequestHandler() {
//        String channelName = getChannel().getName();

//        wechatPayConfig = getWechatPayConfig(channelName);
        String APPID = wechatPayConfig.getAppID();
        String APPKEY = wechatPayConfig.getAppsecret();
        String MCHID = wechatPayConfig.getMchId();
        String MCHKEY = wechatPayConfig.getMchSecret();
        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(APPID, APPKEY, MCHKEY);
        return reqHandler;
    }

    public abstract String getTitle(PayLogInfo payLogInfo);

//    @Override
//    public String getFormHtml(String paySerial, BigDecimal amount, int installment, String host) throws Exception {
//        logger.info("start create {} wehcat pay", paySerial);
//        PayLogInfo payLogInfo = payLogInfoService.getByLocalSn(paySerial);
//        return getPay(paySerial);
//    }

    private WechatPay submitPay(PayLogInfo payLogInfo, String tradeType, String code, String remoteAddr) {

        RequestHandler reqHandler = getRequestHandler();
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        String opernID = null;
        String APPID = wechatPayConfig.getAppID();

        logger.info("star get open by code {}", code);
        opernID = this.getOpenIdByCode(code);
        packageParams.put("openid", opernID);
        logger.info("openId is {} ", opernID);

        String MCHID = wechatPayConfig.getMchId();

        String createOrderURL = wechatPayConfig.getCreateOrderURL();


        String notifyURL = wechatPayConfig.getPayNotifyURL();

        String sn = payLogInfo.getSn();
        String channelSN = payLogInfo.getChannelSN();
        BigDecimal amount = payLogInfo.getAmount();
        //String out_trade_no = sn;
        String money = amount.toString();
        String finalmoney = amount.multiply(new BigDecimal(100)).toString();
        finalmoney = finalmoney.substring(0, finalmoney.indexOf('.'));
        //随机数
        String nonce_str = MD5Util.byteArrayToHexString(String.valueOf(new Random().nextInt(10000)).getBytes());
        //reqHandler.init(APPID, APPKEY, MCHKEY);
        if (StringUtils.isBlank(channelSN)) {
            packageParams.put("appid", APPID);
            packageParams.put("mch_id", MCHID);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", getTitle(payLogInfo));
            packageParams.put("out_trade_no", sn);
            packageParams.put("total_fee", finalmoney);
            packageParams.put("spbill_create_ip", remoteAddr);
            packageParams.put("notify_url", notifyURL);
            packageParams.put("trade_type", tradeType);


            String sign = reqHandler.createSign(packageParams);


            /*------5.生成需要提交给统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder 的xml数据-------*/
            StringBuilder xmlSb = new StringBuilder();
            xmlSb.append("<xml>")
                    .append("<appid>" + APPID + "</appid>")
                    .append("<body><![CDATA[" + getTitle(payLogInfo) + "]]></body>")
                    .append("<mch_id>" + MCHID + "</mch_id>")
                    .append("<nonce_str>" + nonce_str + "</nonce_str>")
                    .append("<notify_url>" + notifyURL + "</notify_url>")
                    .append("<out_trade_no>" + sn + "</out_trade_no>")
                    .append("<spbill_create_ip>" + remoteAddr + "</spbill_create_ip>")
                    .append("<total_fee>" + finalmoney + "</total_fee>")
                    .append("<trade_type>" + tradeType + "</trade_type>");

            if (StringUtils.isNotBlank(opernID)) {
                xmlSb.append("<openid>" + opernID + "</openid>");
            }

            xmlSb.append("<sign>" + sign + "</sign>")
                    .append("</xml>");

            /*------6.调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder 生产预支付订单----------*/
            String xml = xmlSb.toString();
            logger.info("xml {}", xml);
            channelSN = GetWxOrderno.getPayNo(createOrderURL, xml);
            if (StringUtils.isNotBlank(channelSN)) {

                payLogInfo.setPayTime(new Date());
//                double commissionRatio = bankInfoService.getCommissionRatio(WECHAT_BANK_ID, getChannel(), 1);
//                double v = amount.multiply(BigDecimal.valueOf(commissionRatio)).doubleValue();
//                payLogInfo.setInterest(BigDecimal.valueOf(v));
//                payLogInfo.setCommission(BigDecimal.valueOf(commissionRatio));
                payLogInfo.setTerm(Byte.valueOf("1"));

                payLogInfo.setChannelSN(channelSN);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR_OF_DAY, 2);
                payLogInfo.setExpTime(calendar.getTime());
                payLogInfo.setPayChannel(1);
//                payLogInfoService.update(payLogInfo);

            }
        }

        nonce_str = MD5Util.byteArrayToHexString(String.valueOf(new Random().nextInt(10000)).getBytes());
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        /*
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String packages = "prepay_id=" + channelSN;
        finalpackage.put("appId", APPID);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonce_str);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        String finalsign = reqHandler.createSign(finalpackage);
        */

        WechatPay pay = new WechatPay();
        pay.setMCHID(wechatPayConfig.getMchId());
        pay.setAppId(APPID);
        pay.setNonceStr(nonce_str);
        pay.setPrepayId(channelSN);
        //pay.setPackages(packages);
        pay.setTimestamp(timestamp);
        pay.setTradeTye(tradeType);
        String finalsign = reqHandler.createSign(pay.getParam());
        pay.setParamSign(finalsign);
        return pay;

    }

//    @Override
    public String pay(Map<String, Object> params) {
//        String channelName = getChannel().getName();
//        wechatPayConfig config = getwechatPayConfig(channelName);

        String code = (String) params.get("code");
        String sn = (String) params.get("sn");
        String remoteAddr = (String) params.get("remoteAddr");
        String tradeType;
        if (null != params.get("tradeType")) {
            tradeType = (String) params.get("tradeType");
        } else {
            tradeType = WechatPay.TRADE_TYPE_JSAPI;
        }
//        PayLogInfo payLogInfo = payLogInfoService.getByLocalSn(sn);
        PayLogInfo payLogInfo = new PayLogInfo();

        WechatPay pay = submitPay(payLogInfo, tradeType, code, remoteAddr);

        StringBuilder sb = new StringBuilder();
        sb.append("        <html>")
                .append("        <head>")
                .append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />")
                .append("        </head>")
                .append("        <body onload=\"javascript:pay();\">")
                .append("        <script type=\"text/javascript\">")
                .append("                function pay(){")
                .append("            if (typeof WeixinJSBridge == \"undefined\"){")
                .append("                if( document.addEventListener ){")
                .append("                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);")
                .append("                }else if (document.attachEvent){")
                .append("                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady);")
                .append("                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);")
                .append("                }")
                .append("            }else{")
                .append("                onBridgeReady();")
                .append("            }")
                .append("        }")
                .append("        function onBridgeReady(){")
                .append("            WeixinJSBridge.invoke(")
                .append("                    'getBrandWCPayRequest', {")
                .append(pay.getJonsParam())
                .append("            },function(res){")
                .append("                if(res.err_msg == \"get_brand_wcpay_request:ok\"){")
                .append("                }else if(res.err_msg == \"get_brand_wcpay_request:cancel\"){")
                .append("                }else{")
                .append("                }")
                .append("                window.location.href =  \"/").append("wechatpay").append("?sn=").append(sn).append("\";")
                .append("            });")
                .append("        }")
                .append("        </script>")
                .append("        </body>")
                .append("        </html>");
        return sb.toString();

    }


//    @Override
//    public Response<Boolean> validatePayNotifyResult(String sn) {
//        Response<Boolean> response = new Response<Boolean>();
//        response.setData(false);
//        PayLogInfo payLogInfo = payLogInfoService.getByLocalSn(sn);
//        if (payLogInfo == null) {
//            //认为支付失败
//            response.setCode(-100016);
//            return response;
//        } else if (payLogInfo.getStatus() == 2) {
//            response.setData(true);
//        } else {
//            boolean flag = checkPay(payLogInfo);
//            if (flag) {
//                response.setData(true);
//                //callbackController.paySuccess(payLogInfo.getPayLogId(), PayChannelEnum.WECHATPAY);
//                payLogInfoService.paySuccessBySn(payLogInfo.getSn(), PayChannelEnum.WECHATPAY, payLogInfo.getChannelSN(), payLogInfo.getAmount());
//            } else {
//                //callbackController.payFail(payLogInfo.getPayLogId(), PayChannelEnum.WECHATPAY);
//                payLogInfoService.payFailBySn(payLogInfo.getSn(), PayChannelEnum.WECHATPAY, payLogInfo.getChannelSN(), payLogInfo.getAmount());
//                response.setCode(-100008);
//            }
//            //支付成功
//        }
//        return response;
//    }

//    @Override
    public Response<CallbackResult> getPayNotifyResult(String sn) {
//        PayLogInfo payLogInfo = payLogInfoService.getAllByLocalSn(sn);
        PayLogInfo payLogInfo = new PayLogInfo();

        CallbackResult callbackResult = new CallbackResult();
        Response<CallbackResult> resp = new Response<>();
        resp.setData(callbackResult);

        callbackResult.setSn(sn);
        callbackResult.setAmount(payLogInfo.getAmount());
        callbackResult.setChannelSN(payLogInfo.getChannelSN());

        boolean flag = checkPay(payLogInfo);
        if (flag) {
            callbackResult.setStatus(ChannelPayStatusEnum.SUCCESS);
            resp.setCode(0);
        } else {
            callbackResult.setStatus(ChannelPayStatusEnum.FAILED);
            resp.setCode(-100008);
        }

        return resp;
    }


    public boolean checkPay(PayLogInfo payLogInfo) {
//        String channelName = getChannel().getName();
//        WechatConfig config = getWechatConfig(channelName);

        String APPID = wechatPayConfig.getAppID();
        String APPKEY = wechatPayConfig.getAppsecret();
        String MCHID = wechatPayConfig.getMchId();
        String MCHKEY = wechatPayConfig.getMchSecret();
        String queryOrderURL = wechatPayConfig.getQueryOrderURL();


        //交易类型 ：jsapi代表微信公众号支付
        //String trade_type = "JSAPI";
        String out_trade_no = payLogInfo.getSn();
        String nonce_str = MD5Util.byteArrayToHexString(String.valueOf(new Random().nextInt(10000)).getBytes());

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", APPID);
        packageParams.put("mch_id", MCHID);
        packageParams.put("nonce_str", nonce_str);

        packageParams.put("out_trade_no", out_trade_no);

        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(APPID, APPKEY, MCHKEY);
        String sign = reqHandler.createSign(packageParams);


        String xml = "<xml>" +
                "<appid>" + APPID + "</appid>" +
                "<mch_id>" + MCHID + "</mch_id>" +
                "<nonce_str>" + nonce_str + "</nonce_str>" +
                "<out_trade_no>" + out_trade_no + "</out_trade_no>" +
                "<sign>" + sign + "</sign>" +
                "</xml>";

        logger.info("xml {}", xml);
        return GetWxOrderno.queryOrder(queryOrderURL, xml);

    }

//    @Override
//    public CallbackResult serverNotify(String result) {
//        CallbackResult callbackResult = new CallbackResult();
//        Map<String, String> resultMap = parseXmlToList(result);
//        String result_code = (String) resultMap.get("result_code");
//        String is_subscribe = (String) resultMap.get("is_subscribe");
//        String transaction_id = (String) resultMap.get("transaction_id");
//        String sign = (String) resultMap.get("sign");
//        String time_end = (String) resultMap.get("time_end");
//        String bank_type = (String) resultMap.get("bank_type");
//
//        //参数验签
//        RequestHandler requestHandler = getRequestHandler();
//        resultMap.remove("sign");
//        SortedMap<String, String> params = new TreeMap<String,String>();
//        params.putAll(resultMap);
//        String checkSign = requestHandler.createSign(params);
//        String out_trade_no = (String) resultMap.get("out_trade_no");
//        String return_code = (String) resultMap.get("return_code");
//
//        logger.info("out_trade_no {} return_code {}", out_trade_no, return_code);
//        callbackResult.setSn(out_trade_no);
//        callbackResult.setChannelStatus(result_code);
//        callbackResult.setStatus(ChannelPayStatusEnum.FAILED);
//        if( sign!=null && checkSign!=null && checkSign.equals(sign)){
//            if (return_code.equals("SUCCESS")) {
//                callbackResult.setStatus(ChannelPayStatusEnum.SUCCESS);
//            }
//        }
//        return callbackResult;
//    }


    private String getOpenIdByCode(String code) {
//        String channelName = getChannel().getName();
//        WechatPayConfig config = getwechatPayConfig(channelName);
        String accessTokenUrl = wechatPayConfig.getAccessTokenUrl();
        String APPID = wechatPayConfig.getAppID();
        String APPKEY = wechatPayConfig.getAppsecret();
        accessTokenUrl += "?appid=" + APPID + "&secret=" + APPKEY + "&code=" + code + "&grant_type=authorization_code";
        logger.info("start get openId by url {}", accessTokenUrl);
        String openId = GetWxOrderno.getOpenIdByCode(accessTokenUrl);

        return openId;
    }

    private Map parseXmlToList(String xml) {
        logger.info("start parseXml : {}", xml);
        Map retMap = new HashMap();
        try {
            StringReader read = new StringReader(xml);
            InputSource source = new InputSource(read);
            SAXBuilder sb = new SAXBuilder();
            sb.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
            sb.setFeature("http://xml.org/sax/features/external-general-entities",false);
            sb.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
            Document doc = (Document) sb.build(source);
            Element root = doc.getRootElement();
            List<Element> es = root.getChildren();
            if (es != null && es.size() != 0) {
                for (Element element : es) {
                    retMap.put(element.getName(), element.getValue());
                }
            }
        } catch (Exception e) {
            logger.warn("Got excepton where parseXML", e);
        }
        return retMap;
    }

//    private wechatPayConfig getwechatPayConfig(String appName) {
//        if (null == wechatPayConfig) {
//            wechatPayConfig = new wechatPayConfig();
//            List<DictionaryData> hehuiList = dictionaryDataService.getDictionaryDataListByKey("WECHATPAY_" + appName);
//            List<DictionaryData> wechatList = dictionaryDataService.getDictionaryDataListByKey("WECHATPAY");
//            for (DictionaryData data : wechatList) {
//                if ("NOTIFY_URL".equals(data.getValue())) {
//                    wechatPayConfig.setNotifyURL(data.getDesc());
//                } else if ("CREATEORDER_URL".equals(data.getValue())) {
//                    wechatPayConfig.setCreateOrderURL(data.getDesc());
//                } else if ("QUERYORDER_URL".equals(data.getValue())) {
//                wechatPayConfig.setQueryOrderURL(data.getDesc());
//                } else if ("AUTHORIZE_URL".equals(data.getValue())) {
//                wechatPayConfig.setAuthorizeUrl(data.getDesc());
//                } else if ("ACCESS_TOKEN_URL".equals(data.getValue())) {
//                wechatPayConfig.setAccessTokenUrl(data.getDesc());
//                } else if ("CALLBACK_URL".equals(data.getValue())) {
//                wechatPayConfig.setCallbackUrl(data.getDesc());
//                }
//            }
//            for (DictionaryData data : hehuiList) {
//                if ("APP_APPID".equals(data.getValue())) {
//                    wechatPayConfig.setAPP_APPID(data.getDesc());
//                } else if ("APPID".equals(data.getValue())) {
//                    wechatPayConfig.setAPPID(data.getDesc());
//                } else if ("APPKEY".equals(data.getValue())) {
//                    wechatPayConfig.setAPPKEY(data.getDesc());
//                } else if ("MCHID".equals(data.getValue())) {
//                    wechatPayConfig.setMCHID(data.getDesc());
//                } else if ("MCHKEY".equals(data.getValue())) {
//                    wechatPayConfig.setMCHKEY(data.getDesc());
//                } else if ("PAY_NOTIFY_URL".equals(data.getValue())) {
//                    wechatPayConfig.setPayNotifyURL(data.getDesc());
//                }
//            }
//        }
//
//
//        return wechatPayConfig;
//    }



//    private String getPay(String sn) {
//        String channelName = getChannel().getName();
//        wechatPayConfig config = getwechatPayConfig(channelName);
//        String payUrlStr = wechatPayConfig.getAuthorizeUrl();
//
//
//        String callbackUrl = wechatPayConfig.getCallbackUrl();
//        callbackUrl += "?payChannel=" + channelName + "&sn=" + sn;
//        callbackUrl = java.net.URLEncoder.encode(callbackUrl);
//        payUrlStr += "?appid=" + config.getAPPID() + "&redirect_uri=" + callbackUrl + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect ";
//
//        Map<String, String> params = new HashedMap();
//        logger.info("getConsumeFormHtml : {}", JSON.toJSONString(params));
//        return HtmlUtil.createHtml(payUrlStr, null, params);
//    }

//
//    public boolean checkPay(PayLogInfo payLogInfo) {
//        String channelName = getChannel().getName();
//        wechatPayConfig config = getwechatPayConfig(channelName);
//
//        String APPID = config.getAPPID();
//        String APPKEY = config.getAPPKEY();
//        String MCHKEY = config.getMCHKEY();
//        String MCHID = config.getMCHID();
//        String queryOrderURL = config.getQueryOrderURL();
//
//
//        //交易类型 ：jsapi代表微信公众号支付
//        //String trade_type = "JSAPI";
//        String out_trade_no = payLogInfo.getSn();
//        String nonce_str = MD5Util.byteArrayToHexString(String.valueOf(new Random().nextInt(10000)).getBytes());
//
//        SortedMap<String, String> packageParams = new TreeMap<String, String>();
//        packageParams.put("appid", APPID);
//        packageParams.put("mch_id", MCHID);
//        packageParams.put("nonce_str", nonce_str);
//
//        packageParams.put("out_trade_no", out_trade_no);
//
//        RequestHandler reqHandler = new RequestHandler(null, null);
//        reqHandler.init(APPID, APPKEY, MCHKEY);
//        String sign = reqHandler.createSign(packageParams);
//
//
//        String xml = "<xml>" +
//                "<appid>" + APPID + "</appid>" +
//                "<mch_id>" + MCHID + "</mch_id>" +
//                "<nonce_str>" + nonce_str + "</nonce_str>" +
//                "<out_trade_no>" + out_trade_no + "</out_trade_no>" +
//                "<sign>" + sign + "</sign>" +
//                "</xml>";
//
//        logger.info("xml {}", xml);
//        return GetWxOrderno.queryOrder(queryOrderURL, xml);
//
//    }

}
