package cn.com.zx.ibossapi.result;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author lvxuezhan
 * 统一API响应结果封装
 */

@ApiModel
public class Result{

    @ApiModelProperty(value = "返回状态码")
    private int code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回对象实体")
    private Object data;

    public Result setCode(ResultCodeMessage resultCodeMessage) {
        this.code = resultCodeMessage.getCode();
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}