package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.domain.ExpertVedio;
import cn.com.zx.ibossapi.mapper.iboss.CommonMapper;
import cn.com.zx.ibossapi.mapper.iboss.ExpertVedioMapper;
import cn.com.zx.ibossapi.util.ListUtil;
import cn.com.zx.ibossapi.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专家文章 服务层实现
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
@Service
public class ExpertVedioServiceImpl implements ExpertVedioService
{
	@Autowired
	private ExpertVedioMapper expertVedioMapper;

	@Autowired
	private CommonMapper commonMapper;

    /**
     * 分页查询专家视频列表--不含参数查询
     */
    @Override
    public PageInfo<ExpertVedio> selectExpertVedioList(String pageIndex, Integer pageSize, String userCode, HashOperations hashOperations,String orgTg){
        PageHelper.startPage(Integer.parseInt(pageIndex), pageSize);
        List<ExpertVedio> list = expertVedioMapper.selectExpertVedioList(orgTg);
        if(!ListUtil.isBlank(list)){
            for(ExpertVedio expertVedio : list){
                String supportKey = RedisUtil.getSupportKey(Constant.EXPERTVIDEO,expertVedio.getEvId());
                Object supportObj = hashOperations.get(Constant.USERSUPPORT,supportKey);
                int supportSum = supportObj==null?0:Integer.parseInt(supportObj.toString());
                expertVedio.setSupportSum(supportSum);
            }
        }
        PageInfo<ExpertVedio> pageInfo = new PageInfo<>(list);
        return pageInfo;
	}

    /**
     * 查看专家视频详情
     * @param evId
     * @return
     */
    @Override
    public ExpertVedio getExpertVedioInfo(String evId) {
        return expertVedioMapper.getExpertVedioInfo(evId);
    }

    /**
     * 查询用户收藏专家视频列表
     * @param proIdList
     * @return
     */
    @Override
    public List<ExpertVedio> selectCollectExperVideoList(List<String> proIdList) {
        return expertVedioMapper.selectCollectExperVideoList(proIdList);
    }

}
