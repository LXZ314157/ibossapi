package cn.com.zx.ibossapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专家线上课程表 pro_expert_course
 * 
 * @author lvxuezhan
 * @date 2019-04-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpertCourse
{
	private static final long serialVersionUID = 1L;

	/** 主键--自增 */
	private Integer ecId;

	/** 标题 */
	private String ecTitle;

    /** 机构标签编号 */
    private Integer orgTg;

	/** 编号 */
	private String ecCode;

	/** 专家作者 */
	private String eiId;

	/**专家作者编号**/
	private String eiCode;

	/**线上课程视频url**/
	private String url;

	/**线上课程视频封面图片url**/
	private String imgUrl;

	/** 介绍 */
	private String introduce;

	/** 专家介绍 */
	private String expertIntroduce;

	/** 价格 */
	private BigDecimal price;

	/** 审核状态 0通过；1待审核；2不通过 */
	private String status;

	/** 上架状态  0已上架；1未上架 */
	private String onLineStatus;

	/** 上架时间 */
	private Date onLineTime;

	/** 创建人 */
	private String createUser;

	/** 创建时间 */
	private Date createTime;

	/** 更新者 */
	private String updateUser;

	/** 更新时间 */
	private Date updateTime;

    /** 收藏状态 */
    private Integer collectState;

    /** 线上课程专家所属教育机构 */
    private String deptName;

    /** 显示用户是否点赞过该线上课程 */
    private Integer isSupport;

    /** 显示线上课程点赞总数 */
    private Integer supportSum;

}
