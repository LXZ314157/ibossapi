package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.WxUserInfo;

/**
 * 微信 数据层
 */
public interface WxMapper {

    public int insertUserInfo(WxUserInfo wxUserInfo);

    public int selectWxUserSaveCount(WxUserInfo wxUserInfo);

    public String getUserCode(String userCode);

    public WxUserInfo getWxUserInfo(String openid);

}
