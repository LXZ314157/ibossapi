package cn.com.zx.ibossapi.domain;

import lombok.Data;

/**
 * @author lvxuezhan
 * @date 2019/6/15
 * 封装token实体
 **/
@Data
public class Token {

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 系统用户账号
     */
    private String UserCode;

    /**
     * 时间戳
     */
    private String timestamp;



}
