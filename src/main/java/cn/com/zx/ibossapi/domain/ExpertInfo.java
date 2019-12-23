package cn.com.zx.ibossapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 专家表 pro_expert_info
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpertInfo
{
	/** 主键--自增 */
	private Integer eiId;

	/** 名称 */
	private String eiName;

	/** 编号 */
	private String eiCode;

	/** 个性签名 */
	private String sign;

	/** 微信号 */
	private String wxCode;

	/** 电话 */
	private String mobile;

	/** 头像url*/
	private String headerUrl;

	/** 半身照url */
	private String bodyUrl;

	/** 教育机构图片地址 */
	private String orgImgUrl;

	/** 所属教育机构 */
	private String deId;

	/** 专家介绍 */
	private String introduce;

	/** 状态 0：启用  1：停用 */
	private String status;

	/** 创建人 */
	private String createUser;

	/** 创建时间 */
	private Date createTime;

	/** 更新者 */
	private String updateUser;

	/** 更新时间 */
	private Date updateTime;

	/** 关注状态 */
	private Integer followState;

}
