package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.EducateCourse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 机构线下课程 数据层
 * 
 * @author lvxuezhan
 * @date 2019-04-19
 */
@Component
public interface EducateCourseMapper
{
    /**
     * 分页查询机构线下课程列表--不含参数查询
     */
	public List<EducateCourse> selectEducateCourseList(String orgTg);

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
	List<EducateCourse> selectCollectEducateCourseList(@Param("proIdList") List<String> proIdList);

}