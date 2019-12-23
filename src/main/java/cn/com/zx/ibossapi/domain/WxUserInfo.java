package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.util.Date;


/**
 * 微信用户信息
 */
@Data
public class WxUserInfo {

    /** 主键id */
    private Integer id;

    /** 国家 */
    private String country;

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** openid */
    private String openid;

    /** 性别 */
    private String sex;

    /** 昵称 */
    private String nickname;

    /** 头像地址 */
    private String headimgurl;

    /** 语言 */
    private String language;

    /** 创建者 */
    private String createUser;

    /** 创建时间 */
    private Date createTime;
}
