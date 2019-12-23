package cn.com.zx.ibossapi.controller.bizController;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.config.IbossConfig;
import cn.com.zx.ibossapi.domain.EducateCourse;
import cn.com.zx.ibossapi.domain.Token;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.result.ResultCodeMessage;
import cn.com.zx.ibossapi.result.ResultGenerator;
import cn.com.zx.ibossapi.service.AccountUserService;
import cn.com.zx.ibossapi.service.CommonService;
import cn.com.zx.ibossapi.service.EducateCourseService;
import cn.com.zx.ibossapi.util.LogUtil;
import cn.com.zx.ibossapi.util.StringZipUtils;
import cn.com.zx.ibossapi.util.TokenUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
 * 机构线下课程 信息操作处理
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
@RestController
@RequestMapping("/educateCourse")
@Api(tags = "线下课程接口")
@ApiResponses(@ApiResponse(code = 500, message = "服务器内部异常",response = Result.class))
public class EducateCourseController
{
	@Autowired
	private EducateCourseService educateCourseService;

	@Autowired
	private AccountUserService accountUserService;

    @Autowired
    private CommonService commonService;

	@Autowired
    private IbossConfig ibossConfig;

	@Autowired
    private StringRedisTemplate redisTemplate;

	/**
	 * 获取线下课程列表
	 */
	@GetMapping("/list")
    @ApiOperation(value = "获取线下课程列表", notes = "获取线下课程列表")
	public Result list(HttpServletRequest request,@RequestParam String pageIndex)
	{
		try{
            String orgTg = request.getParameter("orgTg");
            if(StringUtils.isEmpty(orgTg)){
                orgTg = "1";
            }
            PageInfo<EducateCourse> pageInfo = null;
            int pageSize = Integer.parseInt(ibossConfig.getPagesize());
            String authToken  = request.getHeader("token");
            HashOperations operations= redisTemplate.opsForHash();
            if(StringUtils.isEmpty(authToken)){
                pageInfo = educateCourseService.selectEducateCourseList(pageIndex,pageSize,"",operations,orgTg);
            }else{
                Result result = TokenUtil.verifyToken(authToken);
                if(result.getCode()!=Constant.SERVER_SUCCESS){
                    return result;
                }
                Token token = (Token) result.getData();
                pageInfo = educateCourseService.selectEducateCourseList(pageIndex,pageSize,token.getUserCode(),operations,orgTg);
            }
            return ResultGenerator.genSuccessResult(pageInfo);
		}catch (Exception e){
			e.printStackTrace();
			LogUtil.getLogger(this.getClass()).info(e.getMessage());
			return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
		}
	}

    /**
     * 线下课程详情
     */
    @GetMapping("/info")
    @ApiOperation(value = "线下课程详情", notes = "线下课程详情")
    public Result info(@RequestParam String ecId,HttpServletRequest request)
    {
        try{
            EducateCourse educateCourse = educateCourseService.getEducateCourseInfo(ecId);
            if(educateCourse==null){
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.EDUCATECOURSE_NOT_EXISTS.getCode(),
                        ResultCodeMessage.EDUCATECOURSE_NOT_EXISTS.getMessage());
            }
            String authToken  = request.getHeader("token");
            if(StringUtils.isEmpty(authToken)){
                educateCourse.setCollectState(Constant.NOT_COLLECTED);
            }else{
                Result result = TokenUtil.verifyToken(authToken);
                if(result.getCode()!=Constant.SERVER_SUCCESS){
                    return result;
                }
                Token token = (Token) result.getData();
                String collectIds = accountUserService.selectProIds(token.getUserCode(),Constant.USERCOLLECTTYPE,Constant.EDUCATECOURSE);
                if(StringUtils.isEmpty(collectIds)){
                    educateCourse.setCollectState(Constant.NOT_COLLECTED);
                }else{
                    List<String> proIdList = new ArrayList<>();
                    proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(collectIds).split(",")));
                    if(proIdList.contains(ecId)){
                        educateCourse.setCollectState(Constant.COLLECTED);
                    }else{
                        educateCourse.setCollectState(Constant.NOT_COLLECTED);
                    }
                }
                String supportIds = accountUserService.selectProIds(token.getUserCode(),Constant.USERSUPPORTTYPE,Constant.EDUCATECOURSE);
                if(StringUtils.isEmpty(supportIds)){
                    educateCourse.setIsSupport(Constant.NOT_SUPPORT);
                }else{
                    List<String> proIdList = new ArrayList<>();
                    proIdList.addAll(Arrays.asList(StringZipUtils.gunzip(supportIds).split(",")));
                    if(proIdList.contains(ecId)){
                        educateCourse.setIsSupport(Constant.SUPPORT);
                    }else{
                        educateCourse.setIsSupport(Constant.NOT_SUPPORT);
                    }
                }
            }
            return ResultGenerator.genSuccessResult(educateCourse);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }

}
