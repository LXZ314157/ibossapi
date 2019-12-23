package cn.com.zx.ibossapi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 专家文章表 pro_expert_article
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ExpertArticle
{
	/** 主键-自增 */
    @ApiModelProperty(value = "文章标号")
	private Integer eaId;

	/** 标题 */
    @ApiModelProperty(value = "标题")
	private String eaTitle;

    /** 机构标签编号 */
    @ApiModelProperty(value = "机构标签编号")
    private Integer orgTg;

	/** 编号 */
    @ApiModelProperty(value = "编号")
	private String eaCode;

	/** 专家作者 */
    @ApiModelProperty(value = "专家作者")
	private String eiId;

	/**专家作者编号**/
    @ApiModelProperty(value = "专家作者编号")
	private String eiCode;

	/**专家个性签名**/
    @ApiModelProperty(value = "专家个性签名")
	private String sign;

	/**专家头像**/
    @ApiModelProperty(value = "专家头像")
	private String headerUrl;

	/** 正文 */
    @ApiModelProperty(value = "正文")
	private String content;

	/** 配图url */
    @ApiModelProperty(value = "配图url")
	private String url;

	/** 审核状态  0通过；1待审核；2不通过 */
    @ApiModelProperty(value = "审核状态")
	private String status;

	/** 上架状态 0已上架；1未上架 */
    @ApiModelProperty(value = "上架状态")
	private String onLineStatus;

	/** 上架时间 */
    @ApiModelProperty(value = "上架时间")
	private Date onLineTime;

	/** 创建人 */
    @ApiModelProperty(value = "创建人")
	private String createUser;

	/** 创建时间 */
    @ApiModelProperty(value = "创建时间")
	private Date createTime;

	/** 更新者 */
    @ApiModelProperty(value = "更新者")
	private String updateUser;

	/** 更新时间 */
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;

    /** 收藏状态 */
    @ApiModelProperty(value = "收藏状态")
	private Integer collectState;

    /** 专家文章的作者所在的教育机构 */
    @ApiModelProperty(value = "专家文章的作者所在的教育机构")
	private String deptName;

    /** 显示用户是否点赞过该文章 */
    @ApiModelProperty(value = "显示用户是否点赞过该文章")
	private Integer isSupport;

    /** 显示文章点赞总数 */
    @ApiModelProperty(value = "显示文章点赞总数")
	private Integer supportSum;

    /** 显示文章阅读总数 */
    @ApiModelProperty(value = "显示文章阅读总数")
	private Integer readNum;

}
