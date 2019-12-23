package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.ExpertCourse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 专家线上课程 数据层
 * 
 * @author lvxuezhan
 * @date 2019-04-19
 */
@Component
public interface ExpertCourseMapper 
{
    /**
     * 分页查询线上课程列表--不含参数查询
     */
	public List<ExpertCourse> selectExpertCourseList(String orgTg);

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
	public List<ExpertCourse> selectCollectExperCourseList(@Param("proIdList") List<String> proIdList);
}