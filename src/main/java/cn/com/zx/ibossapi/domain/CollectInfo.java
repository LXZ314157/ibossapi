package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.math.BigDecimal;

/** 收藏类实体（专家文章、专家视频、线上课程、线下课程）
 * @author lvxuezhan
 * @date 2019/7/7
 **/
@Data
public class CollectInfo {

    /** 收藏类型 1：专家文章 2：专家视频 3：线上课程 4：线下课程 */
    private Integer collectType;

    /** 收藏Id */
    private Integer collectId;

    /** 标题 */
    private String title;

    /** 作者 */
    private String eiId;

    /** 机构名称 */
    private String deptName;

    /** 图片或者视频地址 */
    private String url;

    /** 线上课程价格 */
    private BigDecimal price;

}
