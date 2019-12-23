package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 数据统计表 pro_data_statistical
 * 
 * @author system
 * @date 2019-08-29
 */
@Data
public class DataStatistical
{
	private static final long serialVersionUID = 1L;
	
	/** 主键自增 */
	private Integer id;
    /** 产品编号 */
    private Integer proId;

	/** 产品类型 1：文章  2：视频  3：线上课程 4：线下课程 */
	private Integer proType;

	/** 点赞次数 */
	private Integer supportNum;
	/** 收藏次数 */
	private Integer collectNum;
    /** 阅读次数 */
    private Integer readNum;
    /** 转发次数 */
	private Integer transferNum;
    /** 分享次数 */
	private Integer shareNum;
	/** 评论次数 */
	private Integer commentNum;
	/** 加权结果值 */
	private BigDecimal resultNum;

}
