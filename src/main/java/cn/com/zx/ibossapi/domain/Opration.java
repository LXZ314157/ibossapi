package cn.com.zx.ibossapi.domain;

import lombok.Data;

/**
 * 用户操作：点赞、点踩
 * @author lvxuezhan
 * @date 2019/12/2
 **/
@Data
public class Opration {

    /** 文章编号 */
    private String atlId;

    /** 操作人的openid */
    private String oprOpenid;

    /** 评论编号 */
    private String cmtNo;

    /** 评论等级 1：评论 2：回复 */
    private Integer commentLevel;

    /** 操作行为 1：点赞 2：点踩 */
    private Integer oprType;

    public Opration setAtlId(String atlId) {
        this.atlId = atlId;
        return this;
    }
    public Opration setOprOpenid(String oprOpenid) {
        this.oprOpenid = oprOpenid;
        return this;
    }
    public Opration setCmtNo(String cmtNo) {
        this.cmtNo = cmtNo;
        return this;
    }
    public Opration setOprType(Integer oprType) {
        this.oprType = oprType;
        return this;
    }
    public Opration setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
        return this;
    }

}
