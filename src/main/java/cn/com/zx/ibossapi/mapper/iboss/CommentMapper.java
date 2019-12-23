package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.WxUserInfo;
import org.springframework.stereotype.Component;

/**
 * 用户对文章评论
 */
@Component
public interface CommentMapper {

    /**
     * 获取微信用户信息
     * @param openid
     * @return
     */
    WxUserInfo getWxUserInfo(String openid);

}
