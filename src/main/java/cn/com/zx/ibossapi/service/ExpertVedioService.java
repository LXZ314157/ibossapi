package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.ExpertVedio;
import com.github.pagehelper.PageInfo;
import org.springframework.data.redis.core.HashOperations;

import java.util.List;

/**
 * 专家视频 服务层
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
public interface ExpertVedioService
{

    /**
     * 分页查询专家视频列表--不含参数查询
     */
    public PageInfo<ExpertVedio> selectExpertVedioList(String pageIndex, Integer pageSize,String userCode,HashOperations hashOperations,String orgTg);

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
    public List<ExpertVedio> selectCollectExperVideoList(List<String> proIdList);


}
