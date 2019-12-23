package cn.com.zx.ibossapi.controller.bizController;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.config.IbossConfig;
import cn.com.zx.ibossapi.domain.ExpertArticle;
import cn.com.zx.ibossapi.domain.Token;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.result.ResultCodeMessage;
import cn.com.zx.ibossapi.result.ResultGenerator;
import cn.com.zx.ibossapi.service.AccountUserService;
import cn.com.zx.ibossapi.service.ExpertArticleService;
import cn.com.zx.ibossapi.util.ListUtil;
import cn.com.zx.ibossapi.util.LogUtil;
import cn.com.zx.ibossapi.util.StringZipUtils;
import cn.com.zx.ibossapi.util.TokenUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 专家文章 信息操作处理
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
@RestController
@RequestMapping("/expertArticle")
@Api(tags = "文章管理接口")
@ApiResponses(@ApiResponse(code = 500, message = "服务器内部异常",response = Result.class))
public class ExpertArticleController
{
	@Autowired
	private ExpertArticleService expertArticleService;

	@Autowired
	private AccountUserService accountUserService;

	@Autowired
    private IbossConfig ibossConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;

	/**
	 * 分页查询专家文章列表--不含参数查询
	 */
    @ApiOperation(value = "分页获取文章列表", notes = "分页获取文章列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "token", value = "请求头token",dataType = "String",paramType = "query"),
                        @ApiImplicitParam(name = "pageIndex", value = "当前页数",defaultValue = "1",required = true, dataType = "String",paramType = "query"),
                        @ApiImplicitParam(name = "orgTg", value = "机构标签", defaultValue = "1", required = false, dataType = "String",paramType = "query")})
    @GetMapping("/list")
	public Result list(HttpServletRequest request,@RequestParam(defaultValue = "1") String orgTg,@RequestParam(defaultValue = "1") String pageIndex)
	{
		try{
		    try{
                Integer.parseInt(orgTg);
                Integer.parseInt(pageIndex);
            }catch (Exception e){
                e.printStackTrace();
                LogUtil.getLogger(this.getClass()).info(e.getMessage());
                return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
            }
            PageInfo<ExpertArticle> pageInfo = null;
            int pageSize = Integer.parseInt(ibossConfig.getPagesize());
            String authToken  = request.getHeader("token");
            HashOperations operations= redisTemplate.opsForHash();
            if(StringUtils.isEmpty(authToken)){
                pageInfo = expertArticleService.selectExpertArticleList(pageIndex,pageSize,"",operations,orgTg);
            }else{
                Result result = TokenUtil.verifyToken(authToken);
                if(result.getCode()!=Constant.SERVER_SUCCESS){
                    return result;
                }
                Token token = (Token) result.getData();
                pageInfo = expertArticleService.selectExpertArticleList(pageIndex,pageSize,token.getUserCode(),operations,orgTg);
            }
			return ResultGenerator.genSuccessResult(pageInfo);
		}catch (Exception e){
			e.printStackTrace();
			LogUtil.getLogger(this.getClass()).info(e.getMessage());
			return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
		}
	}

	/**
	 * 查看专家文章详细信息
	 */
    @ApiOperation(value = "获取文章详细信息", notes = "获取文章详细信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "token", value = "请求头token",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query")})
	@GetMapping("/info")
	public Result info(HttpServletRequest request)
	{
		try{
		    String eaId = request.getParameter("eaId");
            try{
                if(eaId == null){
                    return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
                }
                Integer.parseInt(eaId);
            }catch (Exception e){
                e.printStackTrace();
                LogUtil.getLogger(this.getClass()).info(e.getMessage());
                return ResultGenerator.genFailResult(ResultCodeMessage.PARAM_EXCEPTION.getMessage());
            }
            ExpertArticle expertArticle = expertArticleService.getExpertArticleInfo(eaId);
            if(expertArticle==null){
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.EXPERTARTICLE_NOT_EXISTS.getCode(),
                        ResultCodeMessage.EXPERTARTICLE_NOT_EXISTS.getMessage());
            }
            String authToken  = request.getHeader("token");
            if(StringUtils.isEmpty(authToken)){
                expertArticle.setCollectState(Constant.NOT_COLLECTED);
            }else{
                Result result = TokenUtil.verifyToken(authToken);
                if(result.getCode()!=Constant.SERVER_SUCCESS){
                    return result;
                }
                Token token = (Token) result.getData();

                //判断是否收藏
                String proIds = accountUserService.selectProIds(token.getUserCode(),Constant.USERCOLLECTTYPE,Constant.EXPERTARTICLE);
                if(StringUtils.isEmpty(proIds)){
                    expertArticle.setCollectState(Constant.NOT_COLLECTED);
                }else{
                    List<String> proIdList = new ArrayList<>();
                    proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(proIds).split(",")));
                    if(proIdList.contains(eaId)){
                        expertArticle.setCollectState(Constant.COLLECTED);
                    }else{
                        expertArticle.setCollectState(Constant.NOT_COLLECTED);
                    }
                }

                //判断是否点赞
                String supportIds = accountUserService.selectProIds(token.getUserCode(),Constant.USERSUPPORTTYPE,Constant.EXPERTARTICLE);
                if(StringUtils.isEmpty(supportIds)){
                    expertArticle.setIsSupport(Constant.NOT_SUPPORT);
                }else{
                    List<String> proIdList = new ArrayList<>();
                    proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(supportIds).split(",")));
                    if(proIdList.contains(eaId)){
                        expertArticle.setIsSupport(Constant.SUPPORT);
                    }else{
                        expertArticle.setIsSupport(Constant.NOT_SUPPORT);
                    }
                }

                //判断是否阅读
                //处理用户消费产品
                String readIds = accountUserService.selectProIds(token.getUserCode(), Constant.USERREADTYPE,Constant.EXPERTARTICLE);
                List<String> proIdList = new ArrayList<>();
                if(readIds==null){
                    int saveId = accountUserService.saveUserOper(token.getUserCode(),Constant.USERREADTYPE,Constant.EXPERTARTICLE,StringZipUtils.gzip(eaId+","));
                    if(saveId<0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.INSERT_SUPPORT_RECOURD_FAIL.getCode(),
                                ResultCodeMessage.INSERT_SUPPORT_RECOURD_FAIL.getMessage());
                    }
                }else{
                    proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(readIds).split(",")));
                    if(!proIdList.contains(eaId)){
                        if(!ListUtil.isBlank(proIdList)){
                            StringBuffer sb = new StringBuffer();
                            for(String eleProId : proIdList){
                                sb.append(eleProId+",");
                            }
                            sb.append(eaId+",");
                            String proIdZip = StringZipUtils.gzip(sb.toString());
                            int resultCount = accountUserService.updateUserOperProId(token.getUserCode(),Constant.USERREADTYPE,Constant.EXPERTARTICLE,proIdZip);
                            if(resultCount<0){
                                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.INSERT_SUPPORT_RECOURD_FAIL.getCode(),
                                        ResultCodeMessage.INSERT_SUPPORT_RECOURD_FAIL.getMessage());
                            }
                        }
                    }
                }
                int count = accountUserService.selectDataStatistical(Constant.EXPERTARTICLE,Integer.parseInt(eaId));
                //处理产品数据统计
                if(count<=0){
                    int saveId = accountUserService.saveReadDataStatistical(Constant.EXPERTARTICLE,Integer.parseInt(eaId));
                    if(saveId<=0){
                        return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                                ResultCodeMessage.COLLECT_FAIL.getMessage());
                    }
                }else{
                    if(!proIdList.contains(eaId)){
                        int updateId = accountUserService.addReadDataStatistical(Constant.EXPERTARTICLE,Integer.parseInt(eaId));
                        if(updateId<=0){
                            return ResultGenerator.genCodeMsgResult(ResultCodeMessage.COLLECT_FAIL.getCode(),
                                    ResultCodeMessage.COLLECT_FAIL.getMessage());
                        }
                    }
                }
            }
			return ResultGenerator.genSuccessResult(expertArticle);
		}catch (Exception e){
			e.printStackTrace();
			LogUtil.getLogger(this.getClass()).info(e.getMessage());
			return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
		}
	}


    /**
     * 分页查询专家文章列表--含参数多条件查询
     */
    @GetMapping("/listByParams")
    public Result selectExpertArticleListByParams(@RequestParam String pageIndex,String eaTitle)
    {
        try{
            int pageSize = Integer.parseInt(ibossConfig.getPagesize());
            PageInfo<ExpertArticle> pageInfo = expertArticleService.selectExpertArticleListByParams(pageIndex,pageSize,eaTitle);
            return ResultGenerator.genSuccessResult(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }

}
