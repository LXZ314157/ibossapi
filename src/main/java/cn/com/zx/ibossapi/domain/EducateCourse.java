package cn.com.zx.ibossapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 机构线下课程表 pro_educate_course
 * 
 * @author lvxuezhan
 * @date 2019-04-19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducateCourse
{
	/** 主键--自增 */
	private Integer ecId;

	/** 标题 */
	private String ecTitle;

    /** 机构标签编号 */
    private Integer orgTg;

	/** 编号 */
	private String ecCode;

	/** 所属教育机构 */
	private String deId;

	/** 课程详情 */
	private String introduce;

	/** 图片url */
	private String url;

	/** 价格 */
	private BigDecimal price;

	/** 审核状态 0通过；1待审核；2不通过 */
	private String status;

	/** 上架状态 0已上架；1未上架 */
	private String onLineStatus;

	/** 上架时间 */
	private Date onLineTime;

	/** 下架时间 */
	private Date offLineTime;

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

    /** 显示用户是否点赞过该线下课程 */
    private Integer isSupport;

    /** 显示线下课程点赞总数 */
    private Integer supportSum;
}
