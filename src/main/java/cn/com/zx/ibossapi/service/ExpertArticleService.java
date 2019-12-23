package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.ExpertArticle;
import com.github.pagehelper.PageInfo;
import org.springframework.data.redis.core.HashOperations;

import java.util.List;

/**
 * 专家文章 服务层
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
public interface ExpertArticleService
{

    /**
     * 分页查询专家文章列表--不含参数查询
     */
    public PageInfo<ExpertArticle> selectExpertArticleList(String pageIndex, Integer pageSize, String userCode, HashOperations hashOperations,String orgTg);


    /**
     * 分页查询专家文章列表--含参数多条件查询
     */
    public PageInfo<ExpertArticle> selectExpertArticleListByParams(String pageIndex, Integer pageSize, String eaTitle);

    /**
     * 查看专家文章详细信息
     * @param eaId
     * @return
     */
    public ExpertArticle getExpertArticleInfo(String eaId);

    /**
     * 获取收藏的专家文章列表
     * @param proIdList
     * @return
     */
    public List<ExpertArticle> selectCollectExpertArticleList(List<String> proIdList);



}
