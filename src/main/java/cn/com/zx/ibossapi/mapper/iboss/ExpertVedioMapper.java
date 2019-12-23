package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.ExpertVedio;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专家视频 数据层
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
public interface ExpertVedioMapper
{

    /**
     * 分页查询专家视频列表--不含参数查询
     */
    public List<ExpertVedio> selectExpertVedioList(String orgTg);

    /**
     * 查看专家视频详情
     * @param evId
     * @return
     */
    public ExpertVedio getExpertVedioInfo(String evId);

    /**
     * 查询用户收藏专家视频列表
     * @param proIdList
     * @return
     */
    public List<ExpertVedio> selectCollectExperVideoList(@Param("proIdList") List<String> proIdList);

}