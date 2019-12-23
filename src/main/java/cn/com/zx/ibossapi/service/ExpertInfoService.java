package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.ExpertInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 专家信息 服务层
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
public interface ExpertInfoService
{

    /**
     * 分页查询专家信息列表--不含参数查询
     */
    public PageInfo<ExpertInfo> selectExpertInfoList(String pageIndex, Integer pageSize);


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
    List<ExpertInfo> selectFollowExpertInfoList(String userCode);


}
