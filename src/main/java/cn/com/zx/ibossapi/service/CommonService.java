package cn.com.zx.ibossapi.service;

import java.util.List;
import java.util.Map;

/**
 * 通用服务，服务层
 */
public interface CommonService {

    /**
     * 查询当前用户关注的对象编号 1：专家 2：投资顾问
     * @param userCode
     * @param followType
     * @return
     */
    public List<Integer> selectFollowIds(String userCode,Integer followType);

    /**
     * 获取首页机构标签列表
     */
    public List<Map<String,String>> getOrgTgList();


}
