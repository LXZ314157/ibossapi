package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.domain.EducateCourse;
import cn.com.zx.ibossapi.mapper.iboss.CommonMapper;
import cn.com.zx.ibossapi.mapper.iboss.EducateCourseMapper;
import cn.com.zx.ibossapi.util.ListUtil;
import cn.com.zx.ibossapi.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构线下课程 服务层实现
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
@Service
public class EducateCourseServiceImpl implements EducateCourseService
{
	@Autowired
	private EducateCourseMapper educateCourseMapper;

	@Autowired
	private CommonMapper commonMapper;

    /**
     * 分页查询线机构下课程列表--不含参数查询
     */
    @Override
    public PageInfo<EducateCourse> selectEducateCourseList(String pageIndex, Integer pageSize, String userCode,
                                                           HashOperations hashOperations,String orgTg){
        PageHelper.startPage(Integer.parseInt(pageIndex), pageSize);
        List<EducateCourse> list = educateCourseMapper.selectEducateCourseList(orgTg);
        if(!ListUtil.isBlank(list)){
            for(EducateCourse educateCourse : list){
                String supportKey = RedisUtil.getSupportKey(Constant.EDUCATECOURSE,educateCourse.getEcId());
                Object supportObj = hashOperations.get(Constant.USERSUPPORT,supportKey);
                int supportSum = supportObj==null?0:Integer.parseInt(supportObj.toString());
                educateCourse.setSupportSum(supportSum);
            }
        }
        PageInfo<EducateCourse> pageInfo = new PageInfo<>(list);
        return pageInfo;
	}

    /**
     * 查看机构线下课程信息详情
     * @param ecId
     * @return
     */
    @Override
    public EducateCourse getEducateCourseInfo(String ecId) {
        return educateCourseMapper.getEducateCourseInfo(ecId);
    }

    /**
     * 查询用户收藏线下课程列表
     * @param proIdList
     * @return
     */
    @Override
    public List<EducateCourse> selectCollectEducateCourseList(List<String> proIdList) {
        return educateCourseMapper.selectCollectEducateCourseList(proIdList);
    }

}
