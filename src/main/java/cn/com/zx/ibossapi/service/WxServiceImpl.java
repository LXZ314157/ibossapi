package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.WxUserInfo;
import cn.com.zx.ibossapi.mapper.iboss.WxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信 服务实现层
 */
@Service
public class WxServiceImpl implements WxService{

    @Autowired
    private WxMapper wxMapper;

    @Override
    public int insertUserInfo(WxUserInfo wxUserInfo) {
        return wxMapper.insertUserInfo(wxUserInfo);
    }

    @Override
    public int selectWxUserSaveCount(WxUserInfo wxUserInfo) {
        return wxMapper.selectWxUserSaveCount(wxUserInfo);
    }

    @Override
    public String getUserCode(String openid) {
        return wxMapper.getUserCode(openid);
    }

    @Override
    public WxUserInfo getWxUserInfo(String openid) {
        return wxMapper.getWxUserInfo(openid);
    }
}
