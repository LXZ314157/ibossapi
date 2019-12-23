package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.ExpertArticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 专家文章 数据层
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
public interface ExpertArticleMapper 
{

    /**
     * 分页查询专家文章列表--不含参数查询
     */
    public List<ExpertArticle> selectExpertArticleList(String orgTg);


    /**
     * 分页查询专家文章列表--含参数多条件查询
     */
    public List<ExpertArticle> selectExpertArticleListByParams(Map<String, Object> map);

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
    public List<ExpertArticle> selectCollectExpertArticleList(@Param("proIdList") List<String> proIdList);

    /**
     * 根据文章编号获取文章标题
     * @param eaId
     * @return
     */
    String getEaTitleById(String eaId);


}