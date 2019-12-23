package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.Advt;

import java.util.List;

/**
 * 广告 数据层
 * 
 * @author system
 * @date 2019-07-20
 */
public interface AdvtMapper 
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