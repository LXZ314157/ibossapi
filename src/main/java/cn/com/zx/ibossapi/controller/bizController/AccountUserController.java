package cn.com.zx.ibossapi.controller.bizController;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.config.WxConfig;
import cn.com.zx.ibossapi.domain.*;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.result.ResultCodeMessage;
import cn.com.zx.ibossapi.result.ResultGenerator;
import cn.com.zx.ibossapi.service.*;
import cn.com.zx.ibossapi.util.*;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/** 用户收藏和关注
 * @author lvxuezhan
 * @date 2019/6/15
 **/
@RestController
@RequestMapping("/account")
@Api(tags = "用户管理接口")
@ApiResponses(@ApiResponse(code = 500, message = "服务器内部异常",response = Result.class))
public class AccountUserController {

    @Autowired
    private AccountUserService accountUserService;

    @Autowired
    private ExpertArticleService expertArticleService;

    @Autowired
    private ExpertVedioService expertVedioService;

    @Autowired
    private ExpertCourseService expertCourseService;

    @Autowired
    private EducateCourseService educateCourseService;

    @Autowired
    private ExpertInfoService expertInfoService;

    @Autowired
    private AdviserInfoService adviserInfoService;

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 用户收藏或取消收藏
     * @param request
     * @param cId 收藏对象Id
     * @param cType 收藏类型 1专家文章;2专家视频;3专家课程;4线下课程
     * @return
     */
    @ApiOperation(value = "用户收藏或取消收藏", notes = "用户收藏或取消收藏")
    @ApiImplicitParams({ @ApiImplicitParam(name = "token", value = "请求头token",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "当前页数",defaultValue = "1",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "orgTg", value = "机构标签", defaultValue = "1", required = false, dataType = "String",paramType = "query")})
    @PostMapping("/collectOrCancel/{cId}")
    public Result collect(HttpServletRequest request, @PathVariable Integer cId,@RequestParam Integer cType){
        String authToken  = request.getHeader("token");
        Result result = TokenUtil.verifyToken(authToken);
        int oprType = Constant.USERCOLLECTTYPE;
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),
                    ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }else{
            return collectOrCancelResult(cId,oprType,cType,token);
        }
    }


    /**
     * 获取用户收藏列表 1专家文章;2专家视频;3专家课程;4线下课程
     * @param request
     * @param cType
     * @return
     */
    @GetMapping("/collectList")
    public Result collectList(HttpServletRequest request,@RequestParam Integer cType){
        Result genResult = null;
        String authToken  = request.getHeader("token");
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        try{
            if(!cType.equals(Constant.EXPERTARTICLE) && !cType.equals(Constant.EXPERTVIDEO)
                    &&!cType.equals(Constant.EXPERTCOURSE) &&!cType.equals(Constant.EDUCATECOURSE)){
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLETTYPE_ERROR.
                        getCode(),ResultCodeMessage.COLLETTYPE_ERROR.getMessage());
            }
            String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
            if(token.getUserCode().equals(userCode)){
                if(cType.equals(Constant.EXPERTARTICLE)){
                    String proIds = accountUserService.selectProIds(token.getUserCode(),Constant.USERCOLLECTTYPE,Constant.EXPERTARTICLE);
                    if(StringUtils.isEmpty(proIds)){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECTLIST_EMPTY.getCode(),
                                ResultCodeMessage.COLLECTLIST_EMPTY.getMessage());
                    }
                    List<String> proIdList = new ArrayList<>();
                    proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(proIds).split(",")));
                    List<ExpertArticle> expertArticleList = expertArticleService.selectCollectExpertArticleList(proIdList);
                    if(expertArticleList.isEmpty()){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECTLIST_EMPTY.getCode(),
                                ResultCodeMessage.COLLECTLIST_EMPTY.getMessage());
                    }else{
                        String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                        genResult = ResultGenerator.genSuccessMsgDataResult(newToken,expertArticleList);
                    }
                }
                if(cType.equals(Constant.EXPERTVIDEO)){
                    String proIds = accountUserService.selectProIds(token.getUserCode(),Constant.USERCOLLECTTYPE,Constant.EXPERTVIDEO);
                    if(StringUtils.isEmpty(proIds)){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECTLIST_EMPTY.getCode(),
                                ResultCodeMessage.COLLECTLIST_EMPTY.getMessage());
                    }
                    List<String> proIdList = new ArrayList<>();
                    proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(proIds).split(",")));
                    List<ExpertVedio> expertVedioList = expertVedioService.selectCollectExperVideoList(proIdList);
                    if(expertVedioList.isEmpty()){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECTLIST_EMPTY.getCode(),
                                ResultCodeMessage.COLLECTLIST_EMPTY.getMessage());
                    }else{
                        String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                        genResult = ResultGenerator.genSuccessMsgDataResult(newToken,expertVedioList);
                    }
                }
                if(cType.equals(Constant.EXPERTCOURSE)){
                    String proIds = accountUserService.selectProIds(token.getUserCode(),Constant.USERCOLLECTTYPE,Constant.EXPERTCOURSE);
                    if(StringUtils.isEmpty(proIds)){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECTLIST_EMPTY.getCode(),
                                ResultCodeMessage.COLLECTLIST_EMPTY.getMessage());
                    }
                    List<String> proIdList = new ArrayList<>();
                    proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(proIds).split(",")));
                    List<ExpertCourse> expertCourseList = expertCourseService.selectCollectExperCourseList(proIdList);
                    if(expertCourseList.isEmpty()){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECTLIST_EMPTY.getCode(),
                                ResultCodeMessage.COLLECTLIST_EMPTY.getMessage());
                    }else{
                        String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                        genResult = ResultGenerator.genSuccessMsgDataResult(newToken,expertCourseList);
                    }
                }
                if(cType.equals(Constant.EDUCATECOURSE)){
                    String proIds = accountUserService.selectProIds(token.getUserCode(),Constant.USERCOLLECTTYPE,Constant.EDUCATECOURSE);
                    if(StringUtils.isEmpty(proIds)){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECTLIST_EMPTY.getCode(),
                                ResultCodeMessage.COLLECTLIST_EMPTY.getMessage());
                    }
                    List<String> proIdList = new ArrayList<>();
                    proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(proIds).split(",")));

                    List<EducateCourse> educateCourseList = educateCourseService.selectCollectEducateCourseList(proIdList);
                    if(educateCourseList.isEmpty()){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECTLIST_EMPTY.getCode(),
                                ResultCodeMessage.COLLECTLIST_EMPTY.getMessage());
                    }else{
                        String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                        genResult = ResultGenerator.genSuccessMsgDataResult(newToken,educateCourseList);
                    }
                }
            }else{
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),
                        ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
        return genResult;
    }


    /**
     * 用户关注或取消关注 1专家;2投资顾问
     * @param request
     * @param fId 被关注对象Id
     * @param fType 关注类型 1专家;2投资顾问
     * @return
     */
    @PostMapping("/followOrCancel/{fId}")
    public Result follow(HttpServletRequest request, @PathVariable Integer fId,@RequestParam Integer fType){
        String authToken  = request.getHeader("token");
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
        if(!token.getUserCode().equals(userCode)){
            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),
                    ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
        }else{
            return followOrCancelResult(fId,fType,token);
        }

    }

    /**
     * 获取用户关注列表 1专家;2投资顾问
     * @param request
     * @param fType
     * @return
     */
    @GetMapping("/followList")
    public Result followList(HttpServletRequest request,@RequestParam Integer fType){
        Result genResult = null;
        String authToken  = request.getHeader("token");
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        Token token = (Token) result.getData();
        try{
            if(!fType.equals(Constant.EXPERT) && !fType.equals(Constant.INVESTMENTADVISOR)){
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.FOLLOWTYPE_ERROR.getCode(),
                        ResultCodeMessage.FOLLOWTYPE_ERROR.getMessage());
            }
            String userCode = redisTemplate.opsForHash().get(Constant.USERAUTH,token.getOpenid()).toString();
            if(token.getUserCode().equals(userCode)){
                if(fType.equals(Constant.EXPERT)){
                    List<ExpertInfo> expertInfoList = expertInfoService.selectFollowExpertInfoList(token.getUserCode());
                    if(expertInfoList.isEmpty()){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.FOLLOWLIST_EMPTY.getCode(),
                                ResultCodeMessage.FOLLOWLIST_EMPTY.getMessage());
                    }else{
                        String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                        genResult = ResultGenerator.genSuccessMsgDataResult(newToken,expertInfoList);
                    }
                }
                if(fType.equals(Constant.INVESTMENTADVISOR)){
                    List<AdviserInfo> adviserInfoList = adviserInfoService.selectFollowAdviserInfoList(token.getUserCode());
                    if(adviserInfoList.isEmpty()){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.FOLLOWLIST_EMPTY.getCode(),
                                ResultCodeMessage.FOLLOWLIST_EMPTY.getMessage());
                    }else{
                        String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                        genResult = ResultGenerator.genSuccessMsgDataResult(newToken,adviserInfoList);
                    }
                }
            }else{
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.TOKEN_DIFFERENT.getCode(),
                        ResultCodeMessage.TOKEN_DIFFERENT.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
        return genResult;
    }


    /**
     * 用户点赞
     * @param sType 1:文章 2：视频 3：线上课程 4：线下课程
     * @param sType 点赞类型
     * @param sId 点赞对象id
     * @return
     */
    @PostMapping("/support")
    public Result support(HttpServletRequest request,@RequestParam Integer sType,@RequestParam Integer sId){
        String authToken  = request.getHeader("token");
        Result result = TokenUtil.verifyToken(authToken);
        if(result.getCode()!=Constant.SERVER_SUCCESS){
            return result;
        }
        try{
            Token token = (Token) result.getData();
            String supportKey = RedisUtil.getSupportKey(sType,sId);
            HashOperations operations= redisTemplate.opsForHash();
            Object supportObj = operations.get(Constant.USERSUPPORT,supportKey);
            if(org.springframework.util.StringUtils.isEmpty(supportObj)){
                operations.put(Constant.USERSUPPORT,supportKey,"1");
            }else{
                operations.put(Constant.USERSUPPORT,supportKey,String.valueOf(Integer.parseInt(supportObj.toString())+1));
            }
            int count = accountUserService.selectDataStatistical(sType,sId);
            //处理产品数据统计
            if(count<=0){
                int saveId = accountUserService.saveSupportDataStatistical(sType,sId);
                if(saveId<=0){
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                            ResultCodeMessage.COLLECT_FAIL.getMessage());
                }
            }else{
                int updateId = accountUserService.addSupportDataStatistical(sType,sId);
                if(updateId<=0){
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                            ResultCodeMessage.COLLECT_FAIL.getMessage());
                }
            }
            //处理用户消费产品
            String proIds = accountUserService.selectProIds(token.getUserCode(), Constant.USERSUPPORTTYPE,sType);
            if(proIds==null){
                int saveId = accountUserService.saveUserOper(token.getUserCode(),Constant.USERSUPPORTTYPE,sType,StringZipUtils.gzip(sId+","));
                if(saveId<0){
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.INSERT_SUPPORT_RECOURD_FAIL.getCode(),
                            ResultCodeMessage.INSERT_SUPPORT_RECOURD_FAIL.getMessage());
                }
            }else{
                List<String> proIdList = new ArrayList<>();
                proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(proIds).split(",")));
                if(!ListUtil.isBlank(proIdList)){
                    StringBuffer sb = new StringBuffer();
                    for(String eleProId : proIdList){
                        sb.append(eleProId+",");
                    }
                    sb.append(sId.toString()+",");
                    String proIdZip = StringZipUtils.gzip(sb.toString());
                    int resultCount = accountUserService.updateUserOperProId(token.getUserCode(),Constant.USERSUPPORTTYPE,sType,proIdZip);
                    if(resultCount<0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.INSERT_SUPPORT_RECOURD_FAIL.getCode(),
                                ResultCodeMessage.INSERT_SUPPORT_RECOURD_FAIL.getMessage());
                    }
                }

            }

            Integer supportSum = Integer.parseInt(operations.get(Constant.USERSUPPORT,supportKey).toString());
            if(supportSum%50==0){
                if(supportSum==50){
                    int insertResult = accountUserService.insertSupportSum(sType,sId,supportSum);
                    if(insertResult==0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.INSERT_SUPPORT_SUM_FAIL.getCode(),
                                ResultCodeMessage.INSERT_SUPPORT_SUM_FAIL.getMessage());
                    }
                }else{
                    int udpateResult = accountUserService.updateSupportSum(sType,sId,supportSum);
                    if(udpateResult==0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.UPDATE_SUPPORT_SUM_FAIL.getCode(),
                                ResultCodeMessage.UPDATE_SUPPORT_SUM_FAIL.getMessage());
                    }
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("supportSum",supportSum);
            String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
            return ResultGenerator.genSuccessMsgDataResult(newToken,map);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }

    private Result collectOrCancelResult(Integer proId,Integer oprType,Integer proType,Token token){
        try{
            String proIds = accountUserService.selectProIds(token.getUserCode(),oprType,proType);
            int count = accountUserService.selectDataStatistical(proType,proId);
            //取消收藏
            if(proIds!=null){
                List<String> proIdList = new ArrayList<>();
                proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(proIds).split(",")));
                String proIdZip = "";
                boolean contains = false;
                //处理产品数据统计
                if(proIdList.contains(proId.toString())){
                    contains = true;
                    proIdList.remove(proId.toString());
                    int removeId = accountUserService.updateDataStatistical(proType,proId);
                    if(removeId<=0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.CANCELCOLLECT_FAIL.getCode(),
                                ResultCodeMessage.CANCELCOLLECT_FAIL.getMessage());
                    }
                }else{
                    proIdList.add(proId.toString());
                    if(count<=0){
                        int saveId = accountUserService.saveCollectDataStatistical(proType,proId);
                        if(saveId<=0){
                            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                                    ResultCodeMessage.COLLECT_FAIL.getMessage());
                        }
                    }else{
                        int addId = accountUserService.addCollectDataStatistical(proType,proId);
                        if(addId<=0){
                            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                                    ResultCodeMessage.COLLECT_FAIL.getMessage());
                        }
                    }
                }

                //处理用户消费产品
                int resultCount = 0;
                String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                if(!ListUtil.isBlank(proIdList)){
                    StringBuffer sb = new StringBuffer();
                    for(String eleProId : proIdList){
                        sb.append(eleProId+",");
                    }
                    proIdZip = StringZipUtils.gzip(sb.toString());
                    resultCount = accountUserService.updateUserOperProId(token.getUserCode(),oprType,proType,proIdZip);
                }else{
                    int delCount = accountUserService.deleteCollects(token.getUserCode(),oprType,proType);
                    if(delCount<=0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.CANCELCOLLECT_FAIL.getCode(),
                                ResultCodeMessage.CANCELCOLLECT_FAIL.getMessage());
                    }else{
                        resultCount = delCount;
                    }
                }
                if(contains){
                    if(resultCount>0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.CANCEL_COLLECT_SUCCESS.getCode(),newToken);
                    }else{
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.CANCELCOLLECT_FAIL.getCode(),
                                ResultCodeMessage.CANCELCOLLECT_FAIL.getMessage());
                    }
                }else{
                    if(resultCount>0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_SUCCESS.getCode(),newToken);
                    }else{
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                                ResultCodeMessage.COLLECT_FAIL.getMessage());
                    }
                }
            //收藏
            }else{
                //处理产品数据统计
                if(count<=0){
                    int saveId = accountUserService.saveCollectDataStatistical(proType,proId);
                    if(saveId<=0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                                ResultCodeMessage.COLLECT_FAIL.getMessage());
                    }
                }else{
                    int updateId = accountUserService.addCollectDataStatistical(proType,proId);
                    if(updateId<=0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                                ResultCodeMessage.COLLECT_FAIL.getMessage());
                    }
                }
                //处理用户消费产品
                if(proIds==null){
                    int saveId = accountUserService.saveUserOper(token.getUserCode(),oprType,proType,StringZipUtils.gzip(proId+","));
                    if(saveId>0){
                        String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_SUCCESS.getCode(),newToken);
                    }else{
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                                ResultCodeMessage.COLLECT_FAIL.getMessage());
                    }
                }

                if(proIds.equals("")){
                    int delCount = accountUserService.deleteCollects(token.getUserCode(),oprType,proType);
                    if(delCount<=0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                                ResultCodeMessage.COLLECT_FAIL.getMessage());
                    }
                }
                return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }

    private Result followOrCancelResult(Integer fId,Integer fType,Token token){
        try{
            int savedCount = accountUserService.selectFollows(token.getUserCode(),fId,fType);
            if(savedCount>0){
                int cancelounts = accountUserService.cancelFollowsResult(token.getUserCode(),fId,fType);
                if(cancelounts>0){
                    String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.CANCEL_FOLLOW_SUCCESS.getCode(),newToken);
                }else{
                    return ResultGenerator.genCodeMsgResult(ResultCodeMessage.CANCELFOLLOW_FAIL.getCode(),
                            ResultCodeMessage.CANCELFOLLOW_FAIL.getMessage());
                }
            }
            int afId = accountUserService.saveFollows(token.getUserCode(),fId,fType);
            if(afId>0){
                String newToken = TokenUtil.generateToken(token.getUserCode(),token.getOpenid(),wxConfig.getSecretKey());
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.FOLLOW_SUCCESS.getCode(),newToken);
            }else{
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.FOLLOW_FAIL.getCode(),
                        ResultCodeMessage.FOLLOW_FAIL.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }
}
