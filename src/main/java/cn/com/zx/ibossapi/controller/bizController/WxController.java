package cn.com.zx.ibossapi.controller.bizController;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.config.WxConfig;
import cn.com.zx.ibossapi.domain.Token;
import cn.com.zx.ibossapi.domain.WxUserInfo;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.result.ResultCodeMessage;
import cn.com.zx.ibossapi.result.ResultGenerator;
import cn.com.zx.ibossapi.service.AccountUserService;
import cn.com.zx.ibossapi.service.WxService;
import cn.com.zx.ibossapi.util.HttpUtil;
import cn.com.zx.ibossapi.util.LogUtil;
import cn.com.zx.ibossapi.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/wx")
@Api(tags = "微信管理接口")
@ApiResponses(@ApiResponse(code = 500, message = "服务器内部异常",response = Result.class))
public class WxController {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private WxService wxService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private AccountUserService accountUserService;


    /**
     * 微信扫码接口
     * @param response
     * @throws IOException
     */
    @GetMapping("/code")
    @ApiOperation(value = "微信扫码接口", notes = "微信扫码接口")
    public void getCode(HttpServletResponse response) throws IOException {
        String backUrl = wxConfig.getBackUrl() + "/wx/callBack";

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
                + "appid=" + wxConfig.getAppID()
                + "&redirect_uri=" + URLEncoder.encode(backUrl)
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";
        response.sendRedirect(url);
    }

    /**
     * 微信扫码回调接口
     * @param request
     * @return
     */
    @Transactional
    @GetMapping("/callBack")
    @ApiOperation(value = "微信扫码回调接口", notes = "微信扫码回调接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "微信扫码返回编号",required = true, dataType = "String",paramType = "query")})
    public Result getAccessToken(HttpServletRequest request) {
        Result genResult = null;
        String code = request.getParameter("code");
        if(code == null){
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid=" + wxConfig.getAppID()
                + "&secret=" + wxConfig.getAppsecret()
                + "&code=" + code
                + "&grant_type=authorization_code";
        try {
            String result = HttpUtil.sendGet(url);

            JSONObject object = JSON.parseObject(result);
            String openid = object.getString("openid");
            String token = object.getString("access_token");

            String infoUrl = "https://api.weixin.qq.com/sns/userinfo?"
                    + "access_token=" + token
                    + "&openid=" + openid
                    + "&lang=zh_CN";
            String userInfo = HttpUtil.sendGet(infoUrl);
            WxUserInfo wxUserInfo = JSON.parseObject(userInfo, WxUserInfo.class);
            int wxUserSaveCount = wxService.selectWxUserSaveCount(wxUserInfo);
            if(wxUserSaveCount==0){
                int wxUserCount = wxService.insertUserInfo(wxUserInfo);
                if(wxUserCount>0){
                    String userCode = accountUserService.insertAccountUser(openid);
                    if(!userCode.equals("")){
                        String newToken = TokenUtil.generateToken(userCode,openid,wxConfig.getSecretKey());
                        redisTemplate.opsForHash().put(Constant.USERAUTH, openid, userCode);
                        genResult = ResultGenerator.genSuccessResult(newToken);
                    }else{
                        genResult =ResultGenerator.genCodeMsgResult(ResultCodeMessage.SAVEACCOUNTUSER_FAIL.getCode(),ResultCodeMessage.SAVEACCOUNTUSER_FAIL.getMessage());
                    }
                }else{
                    genResult =ResultGenerator.genCodeMsgResult(ResultCodeMessage.SAVEWXUSER_FAIL.getCode(),ResultCodeMessage.SAVEWXUSER_FAIL.getMessage());
                }
            }else{
                String userCode = wxService.getUserCode(openid);
                String newToken = TokenUtil.generateToken(userCode,openid,wxConfig.getSecretKey());
                redisTemplate.opsForHash().put(Constant.USERAUTH, openid, userCode);
                genResult = ResultGenerator.genSuccessResult(newToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
        return genResult;
    }

    /**
     * 获取微信用户信息
     * @param request
     * @return
     */
    @GetMapping("/getWxUserInfo")
    @ApiOperation(value = "获取微信用户信息接口", notes = "获取微信用户信息接口")
    @ApiImplicitParams({ @ApiImplicitParam(name = "token", value = "请求头token",required = true,dataType = "String",paramType = "query")})
    public Result getWxUserInfo(HttpServletRequest request){
        String authToken  = request.getHeader("token");
        Token token = null;
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        token = (Token) result.getData();
        try{
            WxUserInfo wxUserInfo = wxService.getWxUserInfo(token.getOpenid());
            if(wxUserInfo!=null){
                return ResultGenerator.genSuccessResult(wxUserInfo);
            }else{
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.WXUSERINFO_EMPTY.getCode(),ResultCodeMessage.WXUSERINFO_EMPTY.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }



}
