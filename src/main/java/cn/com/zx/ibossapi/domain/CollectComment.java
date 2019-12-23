package cn.com.zx.ibossapi.domain;

import lombok.Data;

/**
 * 收藏用户评论
 * @author lvxuezhan
 * @date 2019/12/4
 **/
@Data
public class CollectComment {

    /** 文章编号 */
    private String eaId;

    /** 文章标题 */
    private String eaTitle;

    /** 评论编号 */
    private String cmtNo;

    /** 收藏者微信openid */
    private String oprOpenid;

    /** 收藏的评论对象 */
    private Comment comment;

    public CollectComment setEaId(String eaId) {
        this.eaId = eaId;
        return this;
    }

    public CollectComment setEaTitle(String eaTitle) {
        this.eaTitle = eaTitle;
        return this;
    }

    public CollectComment setCmtNo(String cmtNo) {
        this.cmtNo = cmtNo;
        return this;
    }

    public CollectComment setOprOpenid(String oprOpenid) {
        this.oprOpenid = oprOpenid;
        return this;
    }
    public CollectComment setComment(Comment comment) {
        this.comment = comment;
        return this;
    }


}
