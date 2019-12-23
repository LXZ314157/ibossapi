package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.util.Date;

/** 投资顾问实体
 * @author lvxuezhan
 * @date 2019/6/16
 **/
@Data
public class AdviserInfo {

    private Integer aiId;

    private String aiName;

    private String aiCode;

    private Integer sex;

    private Integer age;

    private String sign;

    private String wxCode;

    private String mobile;

    private String IDCard;

    private Integer deId;

    private String spclt;

    private String introduce;

    private Integer authSts;

    private Integer status;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}
