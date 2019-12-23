package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.WxUserInfo;

/**
 * 微信 服务层
 */
public interface WxService {

    public int insertUserInfo(WxUserInfo wxUserInfo);

    /**
     * 查询微信用户是否注册过
     * @param wxUserInfo
     * @return
     */
    public int selectWxUserSaveCount(WxUserInfo wxUserInfo);

    /**
     * 获取userCode
     * @param openid
     * @return
     */
    public String getUserCode(String openid);

    /**
     * 获取微信用户信息
     * @param openid
     * @return
     */
    public WxUserInfo getWxUserInfo(String openid);

}
