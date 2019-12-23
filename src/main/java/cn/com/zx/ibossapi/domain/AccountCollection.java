package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.util.Date;

/** 用户收藏实体 1专家文章;2专家视频;3专家课程;4线下课程
 * @author lvxuezhan
 * @date 2019/6/15
 **/
@Data
public class AccountCollection {

    private Integer acId;

    private String userCode;

    private Integer cId;

    private Integer cType;

    private Date createTime;

}
