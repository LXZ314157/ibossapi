package cn.com.zx.ibossapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信配置参数
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WxConfig {

    /**
     * 公众号唯一标识
     */
    private String appID;

    /**
     * 公众号秘钥
     */
    private String appsecret;

    /**
     * 微信网页授权配置
     */
    private String authConfig;

    /**
     * 微信授权回调地址
     */
    private String backUrl;

    /**
     * 生成token的秘钥
     */
    private String secretKey;



}
