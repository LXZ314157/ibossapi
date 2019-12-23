package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.domain.ExpertCourse;
import cn.com.zx.ibossapi.mapper.iboss.CommonMapper;
import cn.com.zx.ibossapi.mapper.iboss.ExpertCourseMapper;
import cn.com.zx.ibossapi.util.ListUtil;
import cn.com.zx.ibossapi.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专家线上课程 服务层实现
 * 
 * @author lvxuezhan
 * @date 2019-04-19
 */
@Service
public class ExpertCourseServiceImpl implements ExpertCourseService
{
	@Autowired
	private ExpertCourseMapper expertCourseMapper;

	@Autowired
	private CommonMapper commonMapper;

    /**
     * 分页查询线上课程列表--不含参数查询
     */
    @Override
    public PageInfo<ExpertCourse> selectExpertCourseList(String pageIndex, Integer pageSize,String userCode,
                                                         HashOperations hashOperations,String orgTg) {
        PageHelper.startPage(Integer.parseInt(pageIndex), pageSize);
        List<ExpertCourse> list = expertCourseMapper.selectExpertCourseList(orgTg);
        if(!ListUtil.isBlank(list)){
            for(ExpertCourse expertCourse : list){
                String supportKey = RedisUtil.getSupportKey(Constant.EXPERTCOURSE,expertCourse.getEcId());
                Object supportObj = hashOperations.get(Constant.USERSUPPORT,supportKey);
                int supportSum = supportObj==null?0:Integer.parseInt(supportObj.toString());
                expertCourse.setSupportSum(supportSum);
            }
        }
        PageInfo<ExpertCourse> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查看线上课程信息详情
     * @param ecId
     * @return
     */
    @Override
    public ExpertCourse getExpertCourseInfo(String ecId) {
        return expertCourseMapper.getExpertCourseInfo(ecId);
    }

    /**
     * 获取用户收藏专家课程列表
     * @param proIdList
     * @return
     */
    @Override
    public List<ExpertCourse> selectCollectExperCourseList(List<String> proIdList) {
        return expertCourseMapper.selectCollectExperCourseList(proIdList);
    }
}
