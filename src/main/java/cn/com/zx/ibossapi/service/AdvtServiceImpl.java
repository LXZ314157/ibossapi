package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.Advt;
import cn.com.zx.ibossapi.mapper.iboss.AdvtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告 服务层实现
 * 
 * @author system
 * @date 2019-07-20
 */
@Service
public class AdvtServiceImpl implements AdvtService
{
	@Autowired
	private AdvtMapper advtMapper;

    /**
     * 获取首页广告列表
     * @return
     */
    @Override
    public List<Advt> selectAdvtList() {
        return advtMapper.selectAdvtList();
    }

    /**
     * 获取广告详详情
     * @param adId
     * @return
     */
    @Override
    public Advt getAdvtInfoByAdId(Integer adId) {
        return advtMapper.getAdvtInfoByAdId(adId);
    }
}
