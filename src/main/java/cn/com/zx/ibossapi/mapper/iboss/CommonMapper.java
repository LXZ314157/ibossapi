package cn.com.zx.ibossapi.mapper.iboss;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 通用服务数据层
 */
@Component
public interface CommonMapper {

    /**
     * 查询当前用户关注的对象编号 1：专家 2：投资顾问
     * @param userCode
     * @param followType
     * @return
     */
    public List<Integer> selectFollowIds(@Param("userCode") String userCode,@Param("followType") Integer followType);


    /**
     * 获取首页机构标签列表
     */
    public List<Map<String,String>> getOrgTgList();

}
