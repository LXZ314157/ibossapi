package cn.com.zx.ibossapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WechatPayConfig {

    /**
     * 公众号唯一标识
     */
    private String appID;

    /**
     * 公众号秘钥
     */
    private String appsecret;

//	private String APP_APPID;
//	private String APPKEY;
	private String mchId;
	private String mchSecret;
//	private String notifyURL;
	private String createOrderURL;
	private String queryOrderURL;
//	private String authorizeUrl;
	private String accessTokenUrl;
//	private String callbackUrl;
	private String payNotifyURL;

}
