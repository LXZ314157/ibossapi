package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.AccountUser;
import cn.com.zx.ibossapi.domain.Token;
import cn.com.zx.ibossapi.mapper.iboss.AccountUserMapper;
import cn.com.zx.ibossapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查询用户信息 服务层实现
 * @author lvxuezhan
 * @date 2019/6/15
 **/
@Service
public class AccountUserServiceImpl implements AccountUserService {

    @Autowired
    private AccountUserMapper accountUserMapper;

    /**
     * 查询登录用户信息
     * @param token
     * @return
     */
    @Override
    public int selectAccountUser(Token token) {
        return accountUserMapper.selectAccountUser(token);
    }

    /**
     * 用户消费
     * @param userCode
     * @param oprType
     * @param proType
     * @param proId
     * @return
     */
    @Override
    public int saveUserOper(String userCode, Integer oprType, Integer proType,String proId) {
        return accountUserMapper.saveUserOper(userCode,oprType,proType,proId);
    }

    /**
     * 用户关注 1专家;2投资顾问
     * @param userCode
     * @param fId
     * @param fType
     * @return
     */
    @Override
    public int saveFollows(String userCode, Integer fId, Integer fType) {
        return accountUserMapper.saveFollows(userCode,fId,fType);
    }

    /**
     * 查询是否已收藏
     * @param userCode
     * @param oprType
     * @param proType
     * @return
     */
    @Override
    public String selectProIds(String userCode, Integer oprType, Integer proType) {
        return accountUserMapper.selectProIds(userCode,oprType,proType);
    }

    /**
     * 删除用户收藏
     * @param userCode
     * @param oprType
     * @param proType
     * @return
     */
    @Override
    public int deleteCollects(String userCode, Integer oprType, Integer proType) {
        return accountUserMapper.deleteCollects(userCode,oprType,proType);
    }

    /**
     * 查询是否已关注
     * @param userCode
     * @param fId
     * @param fType
     * @return
     */
    @Override
    public int selectFollows(String userCode, Integer fId, Integer fType) {
        return accountUserMapper.selectFollows(userCode,fId,fType);
    }

    /**
     * 创建系统用户
     * @param openid
     * @return
     */
    @Override
    public String insertAccountUser(String openid) {
        AccountUser accountUser = new AccountUser();
        String userCode = StringUtil.getRandomUUID();
        accountUser.setUserCode(userCode);
        accountUser.setOpenid(openid);
        int count = accountUserMapper.insertAccountUser(accountUser);
        String resultUserCode = count>0?userCode:"";
        return resultUserCode;
    }

    /**
     * 取消用户收藏
     * @param userCode
     * @param oprType
     * @param proType
     * @param proId
     * @return
     */
    @Override
    public int updateUserOperProId(String userCode, Integer oprType, Integer proType,String proId) {
        return accountUserMapper.updateUserOperProId(userCode,oprType,proType,proId);
    }

    /**
     * 取消用户关注
     * @param userCode
     * @param fId
     * @param fType
     * @return
     */
    @Override
    public int cancelFollowsResult(String userCode, Integer fId, Integer fType) {
        return accountUserMapper.cancelFollowsResult(userCode,fId,fType);
    }

    /**
     * 记录点赞对象的总点赞次数
     * @param sType 1:文章 2：视频 3：线上课程 4：线下课程
     * @param sId
     * @param sCount
     * @return
     */
    @Override
    public int insertSupportSum(Integer sType, Integer sId, Integer sCount) {
        return accountUserMapper.insertSupportSum(sType,sId,sCount);
    }

    /**
     * 更新点赞总数
     * @param sType 1:文章 2：视频 3：线上课程 4：线下课程
     * @param sId
     * @return
     */
    @Override
    public int updateSupportSum(Integer sType, Integer sId,Integer sCount) {
        return accountUserMapper.updateSupportSum(sType,sId,sCount);
    }

    @Override
    public int selectDataStatistical(Integer proType, Integer proId) {
        return accountUserMapper.selectDataStatistical(proType,proId);
    }

    @Override
    public int saveCollectDataStatistical(Integer proType, Integer proId) {
        return accountUserMapper.saveCollectDataStatistical(proType,proId);
    }

    @Override
    public int saveSupportDataStatistical(Integer proType, Integer proId) {
        return accountUserMapper.saveSupportDataStatistical(proType,proId);
    }

    @Override
    public int saveReadDataStatistical(Integer proType, Integer proId) {
        return accountUserMapper.saveReadDataStatistical(proType,proId);
    }

    @Override
    public int addCollectDataStatistical(Integer proType, Integer proId) {
        return accountUserMapper.addCollectDataStatistical(proType,proId);
    }
    @Override
    public int addSupportDataStatistical(Integer proType, Integer proId) {
        return accountUserMapper.addSupportDataStatistical(proType,proId);
    }
    @Override
    public int addReadDataStatistical(Integer proType, Integer proId) {
        return accountUserMapper.addReadDataStatistical(proType,proId);
    }

    @Override
    public int updateDataStatistical(Integer proType, Integer proId) {
        return accountUserMapper.updateDataStatistical(proType,proId);
    }

}
