package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.domain.*;

import java.util.List;

public interface CommentService {

    /**
     * 获取微信用户信息
     * @param openid
     * @return
     */
    WxUserInfo getWxUserInfo(String openid);

    /**
     * 保存用户一级评论
     * @param wxUserInfo
     * @return
     */
    int saveFirstLevelComment(WxUserInfo wxUserInfo,String eaId,String content);

    /**
     * 保存用户二级回复
     * @param wxUserInfo
     * @param eaId
     * @param content
     * @param cmtNo
     * @return
     */
    int saveSecondLevelComment(WxUserInfo wxUserInfo,String eaId,String content,String cmtNo);

    /**
     * 根据文章编号获取文章评论列表
     * @param eaId
     * @return
     */
    Document getCommentListByEaId(String eaId);

    /**
     * 获取文章评论总数
     * @param eaId
     * @return
     */
    int getCommentNum(String eaId);

    /**
     * 给一级评论点赞
     * @param wxUserInfo
     * @param eaId
     * @param cmtNo
     * @return
     */
    int firstLevelAppr(WxUserInfo wxUserInfo,String eaId,String cmtNo);

    /**
     * 二级回复点赞/点踩
     * @param wxUserInfo
     * @param eaId
     * @param cmtNo1
     * @param cmtNo2
     * @param oprType
     * @return
     */
    int secondLevelApprOppo(WxUserInfo wxUserInfo,String eaId,String cmtNo1,String cmtNo2,Integer oprType);

    /**
     * 用户举报
     * @param wxUserInfo
     * @param eaId
     * @param cmtNo1
     * @param cmtNo2
     * @param commentLevel
     * @param reason
     * @return
     */
    int accusation(WxUserInfo wxUserInfo,String eaId,String cmtNo1,String cmtNo2,int commentLevel,String reason);


    /**
     * 收藏用户一级评论
     * @param wxUserInfo
     * @param eaId
     * @param cmtNo1
     * @return
     */
    int collectComment(WxUserInfo wxUserInfo,String eaId,String cmtNo1);

    /**
     * 取消用户收藏评论
     * @param openid
     * @param cmtNo
     * @return
     */
    int removeComment(String openid,String cmtNo);

    /**
     * 获取用户收藏的评论列表
     * @param openid
     * @return
     */
    List<CollectComment> getCollectComment(String openid);

    /**
     * 获取所有文章评论列表
     * @return
     */
    List<Document> getAllComments();

    /**
     * 获取所有操作（点赞、点踩）列表
     * @return
     */
    List<Opration> getAllOpprs();

    /**
     * 获取所有收藏列表
     * @return
     */
    List<CollectComment> getAllCollects();

    /**
     * 获取所有举报列表
     * @return
     */
    List<Accusation> getAllAccus();

    /**
     * 根据文章编号和一级评论编号获取二级回复列表
     * @param eaId
     * @param cmtNo1
     * @return
     */
    Comment secondReplyList(String eaId,String cmtNo1);

}
