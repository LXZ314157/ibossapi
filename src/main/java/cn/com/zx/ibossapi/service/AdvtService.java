package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.Advt;

import java.util.List;

/**
 * 广告 服务层
 * 
 * @author system
 * @date 2019-07-20
 */
public interface AdvtService
{

    /**
     * 获取首页广告列表
     * @return
     */
    public List<Advt> selectAdvtList();

    /**
     * 获取广告详详情
     * @param adId
     * @return
     */
    public Advt getAdvtInfoByAdId(Integer adId);
}
