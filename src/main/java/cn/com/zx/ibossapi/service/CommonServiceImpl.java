package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.mapper.iboss.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/** 通用服务实现层
 * @author lvxuezhan
 * @date 2019/6/28
 **/
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;

    /**
     * 查询当前用户关注的对象编号 1：专家 2：投资顾问
     * @param userCode
     * @param followType
     * @return
     */
    @Override
    public List<Integer> selectFollowIds(String userCode, Integer followType) {
        return commonMapper.selectFollowIds(userCode,followType);
    }

    @Override
    public List<Map<String, String>> getOrgTgList() {
        return commonMapper.getOrgTgList();
    }

}
