package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.ExpertInfo;

import java.util.List;

/**
 * 专家信息 数据层
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
public interface ExpertInfoMapper
{

    /**
     * 分页查询专家信息列表--不含参数查询
     */
    public List<ExpertInfo> selectExpertInfoList();

    /**
     * 查看专家信息详情
     * @param eiId
     * @return
     */
    public ExpertInfo getExpertInfo(String eiId);

    /**
     * 获取用户关注的专家列表
     * @param userCode
     * @return
     */
    public List<ExpertInfo> selectFollowExpertInfoList(String userCode);
}