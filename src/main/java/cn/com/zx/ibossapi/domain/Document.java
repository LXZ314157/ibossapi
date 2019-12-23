package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.util.List;

/** 用户评论总文档
 * @author lvxuezhan
 * @date 2019/12/1
 **/
@Data
public class Document {

    /** 文章编号 */
    private String atlId;

    /** 文章总评论/回复数 */
    private Integer total;

    /** 评论内容（集合） */
    private List<Comment> comments;

}
