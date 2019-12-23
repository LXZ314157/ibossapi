package cn.com.zx.ibossapi.domain;

import lombok.Data;

/**
 * @author lvxuezhan
 * @date 2019/9/1
 **/
@Data
public class UserOpr {

    private Integer id;

    private String userCode;

    private Integer oprType;

    private Integer proType;

    private String proId;

}
