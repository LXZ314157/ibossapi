package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.AdviserInfo;
import cn.com.zx.ibossapi.mapper.iboss.AdviserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 查询投资顾问信息 服务实现层
 * @author lvxuezhan
 * @date 2019/6/16
 **/
@Service
public class AdviserInfoServiceImpl implements AdviserInfoService{

    @Autowired
    private AdviserInfoMapper adviserInfoMapper;

    /**
     * 获取用户关注的投资顾问列表
     * @param userCode
     * @return
     */
    @Override
    public List<AdviserInfo> selectFollowAdviserInfoList(String userCode) {
        return adviserInfoMapper.selectFollowAdviserInfoList(userCode);
    }
}
