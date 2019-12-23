package cn.com.zx.ibossapi.util;
import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.domain.Token;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.result.ResultCodeMessage;
import cn.com.zx.ibossapi.result.ResultGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lvxuezhan
 * @date 2019/6/15
 **/
public class TokenUtil {

    /**
     * 根据userCode、openid、时间戳和秘钥生成token
     * @param userCode
     * @param openid
     * @param key
     * @return
     */
    public static String generateToken(String userCode,String openid,String key){
        Token token = new Token();
        token.setUserCode(userCode);
        token.setOpenid(openid);
        token.setTimestamp(DateUtil.getTimestamp());
        return AESUtil.encrypt(JSON.toJSONString(token),key);
    }

    public static void main(String[]args){
        System.out.println(generateToken("941C5D72F13A48BFB45169EC69ED1BDC",
                "owCrB59zXYpGEZde44rZocxHmjNw",
                "e10adc3949ba59abbe56e003r5yf883e"));
    }
    /**
     * token校验
     * @param authToken
     * @return
     */
    public static Result verifyToken(String authToken){
        if(StringUtils.isEmpty(authToken)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_EMPTY.getCode(),
                    ResultCodeMessage.TOKEN_EMPTY.getMessage());
        }
        String decrptToken = AESUtil.decrypt(authToken,Constant.TOKEN_SECRETKEY);
        Token token = JSON.parseObject(decrptToken,Token.class);
        if(token == null || StringUtils.isBlank(token.getOpenid()) || StringUtils.isEmpty(token.getUserCode())){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_EMPTY.getCode(),
                    ResultCodeMessage.TOKEN_EMPTY.getMessage());
        }
        return ResultGenerator.genSuccessResult(token);
    }

}
