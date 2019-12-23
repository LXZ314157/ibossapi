package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.util.Date;

/** 用户关注实体： 1专家;2投资顾问
 * @author lvxuezhan
 * @date 2019/6/15
 **/
@Data
public class AccountFollow {

    private int afId;

    private String userCode;

    private Integer fId;

    private Integer fType;

    private Date createTime;

}
