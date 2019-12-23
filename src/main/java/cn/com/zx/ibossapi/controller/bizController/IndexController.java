package cn.com.zx.ibossapi.controller.bizController;

import cn.com.zx.ibossapi.config.WxConfig;
import cn.com.zx.ibossapi.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 配置微信网页授权（授权证书配置）
 */
@RestController
@RequestMapping({"/"})
@Api(tags = "微信网页授权")
@ApiResponses(@ApiResponse(code = 500, message = "服务器内部异常",response = Result.class))
public class IndexController {

    @Autowired
    private WxConfig wxConfig;

    @GetMapping("/MP_verify_GdVOOLD1CnYNBuKu.txt")
    private String returnConfigFile(HttpServletResponse response) {
        return wxConfig.getAuthConfig();
    }

}
