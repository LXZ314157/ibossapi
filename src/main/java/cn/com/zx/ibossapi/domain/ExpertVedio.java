package cn.com.zx.ibossapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 专家视频表 pro_expert_vedio
 * 
 * @author lvxuezhan
 * @date 2019-04-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpertVedio
{
	/** 主键--自增 */
	private Integer evId;

	/** 标题 */
	private String evTitle;

    /** 机构标签编号 */
    private Integer orgTg;

	/** 编号 */
	private String evCode;

	/** 专家作者 */
	private String eiId;

	/**专家作者编号**/
	private String eiCode;

	/** 视频介绍 */
	private String introduce;

	/** 视频url */
	private String url;

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

    /** 专家视频的作者所在的教育机构 */
    private String deptName;

    /** 显示用户是否点赞过该视频 */
    private Integer isSupport;

    /** 显示视频点赞总数 */
    private Integer supportSum;

    /** 显示视频阅读总数 */
    private Integer readNum;

}
