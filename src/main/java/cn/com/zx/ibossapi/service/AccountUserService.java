package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.Token;

/**
 * 查询用户信息 服务层
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
public interface AccountUserService
{

    /**
     * 查询登录用户信息
     * @param token
     * @return
     */
    public int selectAccountUser (Token token);


    /**
     * 用户消费
     * @param userCode
     * @param oprType
     * @param proType
     * @param proId
     * @return
     */
    public int saveUserOper(String userCode,Integer oprType,Integer proType,String proId);


    /**
     * 用户关注 1专家;2投资顾问
     * @param userCode
     * @param fId
     * @param fType
     * @return
     */
    public int saveFollows(String userCode,Integer fId,Integer fType);


    /**
     * 查询是否已收藏
     * @param userCode
     * @param oprType
     * @param proType
     * @return
     */
    public String selectProIds(String userCode,Integer oprType,Integer proType);

    /**
     * 删除用户收藏
     * @param userCode
     * @param oprType
     * @param proType
     * @return
     */
    int deleteCollects(String userCode,Integer oprType,Integer proType);

    /**
     * 查询是否已关注
     * @param userCode
     * @param fId
     * @param fType
     * @return
     */
    public int selectFollows(String userCode,Integer fId,Integer fType);


    /**
     * 创建系统账户
     * @param openid
     * @return
     */
    public String insertAccountUser(String openid);


    /**
     * 取消用户收藏
     * @param userCode
     * @param oprType
     * @param proType
     * @param proId
     * @return
     */
    public int updateUserOperProId(String userCode,Integer oprType,Integer proType,String proId);


    /**
     * 取消用户关注
     * @param userCode
     * @param fId
     * @param fType
     * @return
     */
    public int cancelFollowsResult(String userCode,Integer fId,Integer fType);

    /**
     * 记录点赞对象的总点赞次数
     * @param sType 1:文章 2：视频 3：线上课程 4：线下课程
     * @param sId
     * @param sCount
     * @return
     */
    public int insertSupportSum(Integer sType,Integer sId,Integer sCount);

    /**
     * 更新点赞总数
     * @param sType 1:文章 2：视频 3：线上课程 4：线下课程
     * @param sId
     * @return
     */
    public int updateSupportSum(Integer sType,Integer sId,Integer sCount);


    /**
     * 查询产品统计
     * @param proType
     * @param proId
     * @return
     */
    int selectDataStatistical(Integer proType,Integer proId);

    /**
     * 新增产品统计--收藏记录
     * @param proType
     * @param proId
     * @return
     */
    int saveCollectDataStatistical(Integer proType,Integer proId);

    /**
     * 新增产品统计--点赞记录
     * @param proType
     * @param proId
     * @return
     */
    int saveSupportDataStatistical(Integer proType,Integer proId);

    /**
     * 新增产品统计--阅读记录
     * @param proType
     * @param proId
     * @return
     */
    int saveReadDataStatistical(Integer proType,Integer proId);

    /**
     * 更新产品统计--减1
     * @param proType
     * @param proId
     * @return
     */
    int updateDataStatistical(Integer proType,Integer proId);

    /**
     * 更新产品统计--加1
     * @param proType
     * @param proId
     * @return
     */
    int addCollectDataStatistical(Integer proType,Integer proId);

    /**
     * 更新产品统计--加1
     * @param proType
     * @param proId
     * @return
     */
    int addSupportDataStatistical(Integer proType,Integer proId);

    /**
     * 更新产品统计--加1
     * @param proType
     * @param proId
     * @return
     */
    int addReadDataStatistical(Integer proType,Integer proId);


}
