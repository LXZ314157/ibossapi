package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.ExpertInfo;
import cn.com.zx.ibossapi.mapper.iboss.ExpertInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专家信息 服务层实现
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
@Service
public class ExpertInfoServiceImpl implements ExpertInfoService
{
	@Autowired
	private ExpertInfoMapper expertInfoMapper;

    /**
     * 分页查询专家信息列表--不含参数查询
     */
    @Override
    public PageInfo<ExpertInfo> selectExpertInfoList(String pageIndex, Integer pageSize){
        PageHelper.startPage(Integer.parseInt(pageIndex), pageSize);
        List<ExpertInfo> list = expertInfoMapper.selectExpertInfoList();
        PageInfo<ExpertInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
	}

    /**
     * 查看专家信息详情
     * @param eiId
     * @return
     */
    @Override
    public ExpertInfo getExpertInfo(String eiId) {
        return expertInfoMapper.getExpertInfo(eiId);
    }

    /**
     * 获取用户关注的专家列表
     * @param userCode
     * @return
     */
    @Override
    public List<ExpertInfo> selectFollowExpertInfoList(String userCode) {
        return expertInfoMapper.selectFollowExpertInfoList(userCode);
    }

}
