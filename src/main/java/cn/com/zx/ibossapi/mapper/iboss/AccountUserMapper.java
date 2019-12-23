package cn.com.zx.ibossapi.mapper.iboss;

import cn.com.zx.ibossapi.domain.AccountUser;
import cn.com.zx.ibossapi.domain.Token;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 查询登录用户信息 数据层AccountUserMapper
 * @author lvxuezhan
 * @date 2019/6/15
 **/
public interface AccountUserMapper {

    /**
     * 查询登录用户信息
     * @param token
     * @return
     */
    public int selectAccountUser(Token token);

    /**
     * 用户消费
     * @param userCode
     * @param oprType
     * @param proType
     * @param proId
     * @return
     */
    public int saveUserOper(@Param("userCode") String userCode,@Param("oprType") Integer oprType,
                            @Param("proType") Integer proType,@Param("proId") String proId);


    /**
     * 用户关注 1专家;2投资顾问
     * @param userCode
     * @param fId
     * @param fType
     * @return
     */
    public int saveFollows(@Param("userCode") String userCode,@Param("fId") Integer fId,@Param("fType") Integer fType);


    /**
     * 查询是否已收藏
     * @param userCode
     * @param oprType
     * @param proType
     * @return
     */
    public String selectProIds(@Param("userCode") String userCode,@Param("oprType") Integer oprType,@Param("proType") Integer proType);


    /**
     * 查询是否已关注
     * @param userCode
     * @param fId
     * @param fType
     * @return
     */
    public int selectFollows(@Param("userCode") String userCode,@Param("fId") Integer fId,@Param("fType") Integer fType);

    /**
     * 创建系统用户
     * @param accountUser
     * @return
     */
    public int insertAccountUser(AccountUser accountUser);


    /**
     * 取消用户收藏
     * @param userCode
     * @param oprType
     * @param proType
     * @param proId
     * @return
     */
    public int updateUserOperProId(@Param("userCode") String userCode,@Param("oprType") Integer oprType,
                                    @Param("proType") Integer proType,@Param("proId") String proId);

    /**
     * 取消用户关注
     * @param userCode
     * @param fId
     * @param fType
     * @return
     */
    public int cancelFollowsResult(@Param("userCode") String userCode,@Param("fId") Integer fId,@Param("fType") Integer fType);

    /**
     * 记录点赞对象的总点赞次数
     * @param sType 1:文章 2：视频 3：线上课程 4：线下课程
     * @param sId
     * @param sCount
     * @return
     */
    public int insertSupportSum(@Param("sType") Integer sType,@Param("sId") Integer sId,@Param("sCount") Integer sCount);

    /**
     * 查询是否保存过点赞总数
     * @param sType 1:文章 2：视频 3：线上课程 4：线下课程
     * @param sId
     * @return
     */
    public int updateSupportSum(@Param("sType") Integer sType,@Param("sId") Integer sId,@Param("sCount") Integer sCount);

    /**
     * 新增产品统计--收藏
     * @param proType
     * @param proId
     * @return
     */
    int saveCollectDataStatistical(@Param("proType") Integer proType,@Param("proId") Integer proId);
    /**
     * 新增产品统计--点赞
     * @param proType
     * @param proId
     * @return
     */

    int saveSupportDataStatistical(@Param("proType") Integer proType,@Param("proId") Integer proId);
    /**
     * 新增产品统计--阅读
     * @param proType
     * @param proId
     * @return
     */

    int saveReadDataStatistical(@Param("proType") Integer proType,@Param("proId") Integer proId);

    /**
     * 更新产品统计--减少1
     * @param proType
     * @param proId
     * @return
     */
    int updateDataStatistical(@Param("proType") Integer proType,@Param("proId") Integer proId);

    /**
     * 更新产品统计--收藏增加1
     * @param proType
     * @param proId
     * @return
     */
    int addCollectDataStatistical(@Param("proType") Integer proType,@Param("proId") Integer proId);

    /**
     * 更新产品统计--点赞增加1
     * @param proType
     * @param proId
     * @return
     */
    int addSupportDataStatistical(@Param("proType") Integer proType,@Param("proId") Integer proId);

    /**
     * 更新产品统计--阅读增加1
     * @param proType
     * @param proId
     * @return
     */
    int addReadDataStatistical(@Param("proType") Integer proType,@Param("proId") Integer proId);


    /**
     * 查询产品统计
     * @param proType
     * @param proId
     * @return
     */
    int selectDataStatistical(@Param("proType") Integer proType,@Param("proId") Integer proId);

    /**
     * 删除用户收藏
     * @param userCode
     * @param oprType
     * @param proType
     * @return
     */
    int deleteCollects(@Param("userCode") String userCode,@Param("oprType") Integer oprType,@Param("proType") Integer proType);


    /**
     * 查询产品编号和阅读次数
     * @return
     */
    List<Map<String,Integer>> getProIdAndReadNum();

}
