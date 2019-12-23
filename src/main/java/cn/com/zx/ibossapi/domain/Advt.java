package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 广告表 pro_advt
 * 
 * @author system
 * @date 2019-07-20
 */
@Data
public class Advt
{
	private static final long serialVersionUID = 1L;
	
	/** 自增id */
	private Integer adId;
	/** 标题 */
	private String title;
	/** 内容 */
	private String content;
	/** 图片地址 */
	private String imgUrl;
	/** 机构标签 1股权激励2市场营销3企业财税4人力资源5企业融资 */
	private String orgTg;
	/** 教育机构 */
	private String orgId;
	/** 价格 */
	private BigDecimal price;
	/** 生效时间 */
	private Date startTime;
	/** 失效时间 */
	private Date endTime;
	/** 上架状态 0：已上架 1：已下架 */
	private String onLineStatus;
	/** 创建者 */
	private String createUser;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateUser;
	/** 更新时间 */
	private Date updateTime;

}
