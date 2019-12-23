package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.AdviserInfo;

import java.util.List;

/**
 * 查询投资顾问信息 服务层
 */
public interface AdviserInfoService {

    /**
     * 获取用户关注的投资顾问列表
     * @param userCode
     * @return
     */
    public List<AdviserInfo> selectFollowAdviserInfoList(String userCode);

}
