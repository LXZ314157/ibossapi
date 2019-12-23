package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.util.Date;

/** 点赞统计实体
 * @author lvxuezhan
 * @date 2019/6/15
 **/
@Data
public class SupportCount {

    private Integer stId;

    private Integer sType;

    private Integer sId;

    private Integer sCount;

    private Date createTime;

}
