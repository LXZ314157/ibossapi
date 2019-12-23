package cn.com.zx.ibossapi.domain;

import lombok.Data;

/** 用户举报类
 * @author lvxuezhan
 * @date 2019/12/2
 **/
@Data
public class Accusation {


    /** 举报编号 */
    private String accuNo;

    /** 文章编号 */
    private String atlId;

    /** 操作人的openid */
    private String oprOpenid;

    /** 举报人的昵称 */
    private String oprNikeName;

    /** 被举报人的昵称 */
    private String beOprNikeName;

    /** 评论等级 1：评论 2：回复 */
    private Integer commentLevel;

    /** 一级评论的编号 */
    private String cmtNo1;

    /** 二级回复的编号 */
    private String cmtNo2;

    /** 被举报人的评论 */
    private String comment;

    /** 被举报原因 */
    private String reason;

    public Accusation setAccuNo(String accuNo){
        this.accuNo = accuNo;
        return this;
    }

    public Accusation setAtlId(String atlId){
        this.atlId = atlId;
        return this;
    }

    public Accusation setOprOpenid(String oprOpenid){
        this.oprOpenid = oprOpenid;
        return this;
    }

    public Accusation setCommentLevel(Integer commentLevel){
        this.commentLevel = commentLevel;
        return this;
    }

    public Accusation setCmtNo1(String cmtNo1){
        this.cmtNo1 = cmtNo1;
        return this;
    }

    public Accusation setReason(String reason){
        this.reason = reason;
        return this;
    }

    public Accusation setCmtNo2(String cmtNo2){
        this.cmtNo2 = cmtNo2;
        return this;
    }

    public Accusation setOprNikeName(String oprNikeName){
        this.oprNikeName = oprNikeName;
        return this;
    }

    public Accusation setBeOprNikeName(String beOprNikeName){
        this.beOprNikeName = beOprNikeName;
        return this;
    }

    public Accusation setComment(String comment){
        this.comment = comment;
        return this;
    }



}
