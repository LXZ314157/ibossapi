package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.EducateCourse;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.redis.core.HashOperations;

import java.util.List;

/**
 * 机构线下课程 服务层
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
public interface EducateCourseService
{

    /**
     * 分页查询机构线下课程列表--不含参数查询
     */
    public PageInfo<EducateCourse> selectEducateCourseList(String pageIndex, Integer pageSize, String userCode,
                                                           HashOperations hashOperations,String orgTg);

    /**
     * 查看机构线下课程信息详情
     * @param ecId
     * @return
     */
    public EducateCourse getEducateCourseInfo(String ecId);

    /**
     * 查询用户收藏线下课程列表
     * @param proIdList
     * @return
     */
    public List<EducateCourse> selectCollectEducateCourseList(@Param("proIdList") List<String> proIdList);


}
