package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.ExpertCourse;
import com.github.pagehelper.PageInfo;
import org.springframework.data.redis.core.HashOperations;

import java.util.List;

/**
 * 专家线上课程 服务层
 * 
 * @author lvxuezhan
 * @date 2019-04-19
 */
public interface ExpertCourseService
{

    /**
     * 分页查询线上课程列表--不含参数查询
     */
    public PageInfo<ExpertCourse> selectExpertCourseList(String pageIndex, Integer pageSize, String userCode,
                                                         HashOperations hashOperations,String orgTg);

    /**
     * 查看线上课程信息详情
     * @param ecId
     * @return
     */
    public ExpertCourse getExpertCourseInfo(String ecId);

    /**
     * 获取用户收藏专家课程列表
     * @param proIdList
     * @return
     */
    public List<ExpertCourse> selectCollectExperCourseList(List<String> proIdList);

}
