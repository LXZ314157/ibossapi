package cn.com.zx.ibossapi.controller.bizController;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.config.WxConfig;
import cn.com.zx.ibossapi.domain.*;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.result.ResultCodeMessage;
import cn.com.zx.ibossapi.result.ResultGenerator;
import cn.com.zx.ibossapi.service.CommentService;
import cn.com.zx.ibossapi.util.LogUtil;
import cn.com.zx.ibossapi.util.TokenUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户对文章评论
 * @author lvxuezhan
 * @date 2019/11/27
 **/
@RestController
@RequestMapping("/comment")
@Api(tags = "文章评论接口")
@ApiResponses(@ApiResponse(code = 500, message = "服务器内部异常",response = Result.class))
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private WxConfig wxConfig;

    /**
     * 用户评论文章（一级评论）
     * @param request
     * @return
     */
    @PostMapping("/firstLevelComment")
    @ApiOperation(value = "用户一级评论", notes = "用户一级评论")
    @ApiImplicitParams({ @ApiImplicitParam(name = "token", value = "请求头token",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "content", value = "评论内容",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query")})
    public Result firstLevelComment(HttpServletRequest request){
        String authToken  = request.getHeader("token");
        String content  = request.getParameter("content");
        String eaId = request.getParameter("eaId");
        if(StringUtils.isEmpty(authToken) || StringUtils.isEmpty(content) || StringUtils.isEmpty(eaId)){
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try{
            Integer.parseInt(eaId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }else{
            try {
                WxUserInfo wxUserInfo = commentService.getWxUserInfo(token.getOpenid());
                int ret = commentService.saveFirstLevelComment(wxUserInfo,eaId,content);
                if(ret == 1){
                    String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.SUCCESS.getCode(),newToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.getLogger(this.getClass()).error(e.getMessage());
            }
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.USER_COMMENT_EXCEPTION.getMessage());
    }

    /**
     * 用户评论文章（二级回复（回复））
     * @param request
     * @return
     */
    @PostMapping("/secondLevelComment")
    @ApiOperation(value = "用户二级回复", notes = "用户二级回复")
    @ApiImplicitParams({ @ApiImplicitParam(name = "token", value = "请求头token",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "content", value = "评论内容",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cmtNo", value = "评论编号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query")})
    public Result secondLevelComment(HttpServletRequest request){
        String authToken  = request.getHeader("token");
        String content  = request.getParameter("content");
        String cmtNo  = request.getParameter("cmtNo");
        String eaId = request.getParameter("eaId");
        if(StringUtils.isEmpty(authToken) || StringUtils.isEmpty(content)
                || StringUtils.isEmpty(eaId) || StringUtils.isEmpty(cmtNo)){
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try{
            Integer.parseInt(eaId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }
        try {
            WxUserInfo wxUserInfo = commentService.getWxUserInfo(token.getOpenid());
            int ret = commentService.saveSecondLevelComment(wxUserInfo,eaId,content,cmtNo);
            if(ret == 1){
                String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.SUCCESS.getCode(),newToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.USER_REPLY_EXCEPTION.getMessage());
    }

    /**
     * 获取文章评论列表
     * @param request
     * @return
     */
    @GetMapping("/commentList")
    @ApiOperation(value = "获取文章评论列表", notes = "获取文章评论列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query")})
    public Result commentList(HttpServletRequest request){
        String eaId = request.getParameter("eaId");
        if(StringUtils.isEmpty(eaId)){
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try{
            Integer.parseInt(eaId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try {
            Document document = commentService.getCommentListByEaId(eaId);
            if(document ==null){
                return ResultGenerator.genFailResult(ResultCodeMessage.GET_COMMENTS_EXCEPTION.getMessage());
            }else{
                return ResultGenerator.genSuccessResult(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.GET_COMMENTS_EXCEPTION.getMessage());
        }
    }

    /**
     * 获取文章总评论数
     * @param request
     * @return
     */
    @GetMapping("/getCommentNum")
    @ApiOperation(value = "获取文章总评论数", notes = "获取文章总评论数")
    @ApiImplicitParams({@ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query")})
    public Result getCommentNum(HttpServletRequest request){
        String eaId = request.getParameter("eaId");
        if(StringUtils.isEmpty(eaId)){
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try{
            Integer.parseInt(eaId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try {
            int num = commentService.getCommentNum(eaId);
            if(num == -1){
                return ResultGenerator.genFailResult(ResultCodeMessage.GET_COMMENT_NUM_EXCEPTION.getMessage());
            }else{
                return ResultGenerator.genSuccessResult(String.valueOf(num));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.GET_COMMENT_NUM_EXCEPTION.getMessage());
        }
    }


    /**
     * 一级评论点赞
     * @param request
     * @return
     */
    @PostMapping("/firstLevelAppr")
    @ApiOperation(value = "一级评论点赞", notes = "一级评论点赞")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "请求头token",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cmtNo", value = "被点赞的评论编号",required = true, dataType = "String",paramType = "query")})
    public Result firstLevelAppr(HttpServletRequest request){
        String authToken  = request.getHeader("token");
        String cmtNo  = request.getParameter("cmtNo");
        String eaId = request.getParameter("eaId");
        if(StringUtils.isEmpty(authToken) || StringUtils.isEmpty(eaId) || StringUtils.isEmpty(cmtNo)){
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try{
            Integer.parseInt(eaId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }else{
            try {
                WxUserInfo wxUserInfo = commentService.getWxUserInfo(token.getOpenid());
                int ret = commentService.firstLevelAppr(wxUserInfo,eaId,cmtNo);
                if(ret == 2){
                    return ResultGenerator.genFailResult(ResultCodeMessage.APPR_ALREADY.getMessage());
                }
                if(ret == 1){
                    String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.SUCCESS.getCode(),newToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.getLogger(this.getClass()).error(e.getMessage());
            }
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.USER_ARRP_EXCEPTION.getMessage());
    }

    /**
     * 二级回复点赞/点踩
     * @param request
     * @return
     */
    @PostMapping("/secondLevelApprOppo")
    @ApiOperation(value = "二级回复点赞/点踩", notes = "二级回复点赞/点踩")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "请求头token",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cmtNo1", value = "一级评论的编号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cmtNo2", value = "二级回复的编号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "oprType", value = "操作类型 1：点赞 2：点踩",required = true, dataType = "String",paramType = "query")})
    public Result secondLevelApprOppo(HttpServletRequest request){
        String authToken = request.getHeader("token");
        String cmtNo1 = request.getParameter("cmtNo1");
        String cmtNo2 = request.getParameter("cmtNo2");
        String eaId = request.getParameter("eaId");
        String oprType = request.getParameter("oprType");
        if(StringUtils.isEmpty(authToken) || StringUtils.isEmpty(eaId) || StringUtils.isEmpty(cmtNo1) || StringUtils.isEmpty(cmtNo2) || StringUtils.isEmpty(oprType)){
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try{
            Integer.parseInt(eaId);
            if(!oprType.equals("1") && !oprType.equals("2")){
                return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }else{
            try {
                WxUserInfo wxUserInfo = commentService.getWxUserInfo(token.getOpenid());
                int ret = commentService.secondLevelApprOppo(wxUserInfo,eaId,cmtNo1,cmtNo2,Integer.parseInt(oprType));
                if(ret == 2){
                    if(oprType.equals("1")){
                        return ResultGenerator.genFailResult(ResultCodeMessage.APPR_ALREADY.getMessage());
                    }else{
                        return ResultGenerator.genFailResult(ResultCodeMessage.OPPO_ALREADY.getMessage());
                    }
                }
                if(ret == 1){
                    String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.SUCCESS.getCode(),newToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.getLogger(this.getClass()).error(e.getMessage());
            }
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.USER_REPLY_EXCEPTION.getMessage());
    }

    /**
     * 用户举报
     * @param request
     * @return
     */
    @PostMapping("/accusation")
    @ApiOperation(value = "用户举报", notes = "用户举报")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "请求头token",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cmtNo1", value = "一级评论的编号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cmtNo2", value = "二级回复的编号",required = false, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "reason", value = "举报原因",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "commentLevel", value = "评论等级 1：评论 2：回复",required = true, dataType = "String",paramType = "query")})
    public Result accusation(HttpServletRequest request){
        String authToken  = request.getHeader("token");
        String eaId  = request.getParameter("eaId");
        String cmtNo1  = request.getParameter("cmtNo1");
        String cmtNo2  = request.getParameter("cmtNo2");
        String commentLevel = request.getParameter("commentLevel");
        String reason  = request.getParameter("reason");
        if(StringUtils.isEmpty(authToken) || StringUtils.isEmpty(eaId) || StringUtils.isEmpty(cmtNo1) ||
                 StringUtils.isEmpty(reason) || StringUtils.isEmpty(commentLevel)){
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }else{
            if(!commentLevel.equals("1") && !commentLevel.equals("2")){
                return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
            }else{
                if(commentLevel.equals("2") && StringUtils.isEmpty(cmtNo2)){
                    return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
                }
            }
        }
        try{
            Integer.parseInt(eaId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }else{
            try {
                WxUserInfo wxUserInfo = commentService.getWxUserInfo(token.getOpenid());
                int ret = commentService.accusation(wxUserInfo,eaId,cmtNo1,cmtNo2,Integer.parseInt(commentLevel),reason);
                if(ret == 2){
                    return ResultGenerator.genFailResult(ResultCodeMessage.ACCU_ALREADY.getMessage());
                }
                if(ret == 1){
                    String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.SUCCESS.getCode(),newToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.getLogger(this.getClass()).error(e.getMessage());
            }
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.USER_REPLY_EXCEPTION.getMessage());
    }

    /**
     * 收藏用户一级评论
     * @param request
     * @return
     */
    @PostMapping("/collectComment")
    @ApiOperation(value = "收藏用户一级评论", notes = "收藏用户一级评论")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "请求头token",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cmtNo1", value = "一级评论的编号",required = true, dataType = "String",paramType = "query")})
    public Result collectComment(HttpServletRequest request){
        String authToken  = request.getHeader("token");
        String eaId  = request.getParameter("eaId");
        String cmtNo1  = request.getParameter("cmtNo1");
        if(StringUtils.isEmpty(authToken) || StringUtils.isEmpty(eaId) || StringUtils.isEmpty(cmtNo1)) {
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try{
            Integer.parseInt(eaId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }else{
            try {
                WxUserInfo wxUserInfo = commentService.getWxUserInfo(token.getOpenid());
                int ret = commentService.collectComment(wxUserInfo,eaId,cmtNo1);
                if(ret == 1){
                    String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.SUCCESS.getCode(),newToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.getLogger(this.getClass()).error(e.getMessage());
            }
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.COLLECT_COMMENT_EXCEPTION.getMessage());
    }

    /**
     * 用户取消收藏评论
     * @param request
     */
    @DeleteMapping("/removeComment")
    @ApiOperation(value = "用户取消收藏评论", notes = "用户取消收藏评论")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "请求头token",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cmtNo", value = "一级评论的编号",required = true, dataType = "String",paramType = "query")})
    public Result removeComment(HttpServletRequest request){
        String authToken  = request.getHeader("token");
        String cmtNo  = request.getParameter("cmtNo");
        if(StringUtils.isEmpty(authToken) || StringUtils.isEmpty(cmtNo)) {
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }else{
            try {
                WxUserInfo wxUserInfo = commentService.getWxUserInfo(token.getOpenid());
                int ret = commentService.removeComment(wxUserInfo.getOpenid(),cmtNo);
                if(ret == 1){
                    String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.SUCCESS.getCode(),newToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.getLogger(this.getClass()).error(e.getMessage());
            }
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.REMOVE_COMMENT_EXCEPTION.getMessage());
    }

    /**
     * 获取用户收藏的评论列表
     * @param request
     * @return
     */
    @GetMapping("/getCollectComment")
    @ApiOperation(value = "获取用户收藏的评论列表", notes = "获取用户收藏的评论列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "请求头token",defaultValue = "1",required = true,dataType = "String",paramType = "query")})
    public Result getCollectComment(HttpServletRequest request){
        String authToken  = request.getHeader("token");
        if(StringUtils.isEmpty(authToken)) {
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }else{
            try {
                WxUserInfo wxUserInfo = commentService.getWxUserInfo(token.getOpenid());
                List<CollectComment> collectCommentList = commentService.getCollectComment(wxUserInfo.getOpenid());
                return ResultGenerator.genSuccessResult(collectCommentList);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.getLogger(this.getClass()).error(e.getMessage());
            }
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.COLLECT_COMMENT_EXCEPTION.getMessage());
    }

    /**
     * 获取所有表内容
     * @return
     */
    @GetMapping("/allTblList")
    @ApiOperation(value = "获取所有表内容（不用调试）", notes = "获取所有表内容（不用调试）")
    @ApiImplicitParams({@ApiImplicitParam(name = "tblId", value = "表编号 1：评论 2：操作 3：收藏 4：举报",required = true, dataType = "String",paramType = "query")})
    public Result allTblList(@RequestParam(defaultValue = "1") String tblId){
        try {
            if(!tblId.equals("1") && !tblId.equals("2") && !tblId.equals("3") && !tblId.equals("4")){
                return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
            }
            if(tblId.equals("1")){
                return ResultGenerator.genSuccessResult(commentService.getAllComments());
            }else if(tblId.equals("2")){
                return ResultGenerator.genSuccessResult(commentService.getAllOpprs());
            }else if(tblId.equals("3")){
                return ResultGenerator.genSuccessResult(commentService.getAllCollects());
            }else if(tblId.equals("4")){
                return ResultGenerator.genSuccessResult(commentService.getAllAccus());

            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.GET_COMMENTS_EXCEPTION.getMessage());
    }

    /**
     * 获取一级评论和二级回复
     * @param request
     * @return
     */
    @GetMapping("/secondReplyList")
    @ApiOperation(value = "获取一级评论和二级回复", notes = "获取一级评论和二级回复")
    @ApiImplicitParams({@ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cmtNo1", value = "一级评论的编号",required = true, dataType = "String",paramType = "query")})
    public Result secondReplyList(HttpServletRequest request){
        String eaId  = request.getParameter("eaId");
        String cmtNo1  = request.getParameter("cmtNo1");
        if(StringUtils.isEmpty(eaId) || StringUtils.isEmpty(cmtNo1)) {
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try{
            Integer.parseInt(eaId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
        }
        try {
            Comment comment = commentService.secondReplyList(eaId,cmtNo1);
            if(comment == null){
                return ResultGenerator.genFailResult(ResultCodeMessage.GET_REPLYLIST_EXCEPTION.getMessage());
            }else{
                return ResultGenerator.genSuccessResult(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ResultGenerator.genFailResult(ResultCodeMessage.GET_REPLYLIST_EXCEPTION.getMessage());
    }


}
