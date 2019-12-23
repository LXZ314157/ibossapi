package cn.com.zx.ibossapi.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author lvxuezhan
 * @date 2019/6/15
 * 系统用户信息
 **/
@Data
public class AccountUser {

    private int userId;

    private String userCode;

    private String openid;

    private String username;

    private String password;

    private String mobile;

    private int status;

    private int oType;

    private int oId;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}
