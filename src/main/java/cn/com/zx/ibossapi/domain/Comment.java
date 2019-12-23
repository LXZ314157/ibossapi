package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.util.List;

/** 用户评论内容
 * @author lvxuezhan
 * @date 2019/12/1
 **/
@Data
public class Comment {

    /** 评论编号 */
    private String cmtNo;
    /** 用户openid */
    private String openid;
    /** 用户昵称 */
    private String nikeName;
    /** 用户头像地址 */
    private String headimgurl;
    /** 评论日期 */
    private String time;
    /** 用户评论内容 */
    private String content;
    /** 是否被屏蔽 1：是 0：否 */
    private Integer isHidden;
    /** 用户点赞数量 */
    private Integer apprNum;
    /** 用户点踩数量 */
    private Integer oppoNum;
    /** 用户举报数量 */
    private Integer accuNum;
    /** 用户评论数量 */
    private Integer replyNum;
    /** 回复列表 */
    private List<Comment> replyList;

    public Comment setOpenid(String openid) {
        this.openid = openid;
        return this;
    }
    public Comment setCmtNo(String cmtNo) {
        this.cmtNo = cmtNo;
        return this;
    }
    public Comment setNikeName(String nikeName) {
        this.nikeName = nikeName;
        return this;
    }
    public Comment setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
        return this;
    }
    public Comment setTime(String time) {
        this.time = time;
        return this;
    }
    public Comment setContent(String content) {
        this.content = content;
        return this;
    }
    public Comment setApprNum(Integer apprNum) {
        this.apprNum = apprNum;
        return this;
    }
    public Comment setOppoNum(Integer oppoNum) {
        this.oppoNum = oppoNum;
        return this;
    }
    public Comment setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
        return this;
    }
    public Comment setAccuNum(Integer accuNum) {
        this.accuNum = accuNum;
        return this;
    }
    public Comment setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
        return this;
    }
    public Comment setReplyList(List<Comment> replyList) {
        this.replyList = replyList;
        return this;
    }
}
