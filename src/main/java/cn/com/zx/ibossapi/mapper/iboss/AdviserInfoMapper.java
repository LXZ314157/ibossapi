package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.AdviserInfo;

import java.util.List;

/**
 * 投资顾问 数据层
 */
public interface AdviserInfoMapper {

    /**
     * 获取用户关注的投资顾问列表
     * @param userCode
     * @return
     */
    public List<AdviserInfo> selectFollowAdviserInfoList(String userCode);

}
