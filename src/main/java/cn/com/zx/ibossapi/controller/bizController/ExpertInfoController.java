package cn.com.zx.ibossapi.controller.bizController;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.config.IbossConfig;
import cn.com.zx.ibossapi.domain.ExpertInfo;
import cn.com.zx.ibossapi.domain.Token;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.result.ResultCodeMessage;
import cn.com.zx.ibossapi.result.ResultGenerator;
import cn.com.zx.ibossapi.service.CommonService;
import cn.com.zx.ibossapi.service.ExpertInfoService;
import cn.com.zx.ibossapi.util.LogUtil;
import cn.com.zx.ibossapi.util.TokenUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 专家信息 信息操作处理
 * 
 * @author lvxuezhan
 * @date 2019-04-15
 */
@RestController
@RequestMapping("/expertInfo")
@Api(tags = "专家信息接口")
@ApiResponses(@ApiResponse(code = 500, message = "服务器内部异常",response = Result.class))
public class ExpertInfoController
{
	@Autowired
	private ExpertInfoService expertInfoService;

    @Autowired
    private CommonService commonService;

	@Autowired
    private IbossConfig ibossConfig;

	/**
	 * 获取专家信息列表
	 */
	@GetMapping("/list")
    @ApiOperation(value = "获取专家信息列表", notes = "获取专家信息列表")
	public Result list(@RequestParam String pageIndex)
	{
		try{
		    int pageSize = Integer.parseInt(ibossConfig.getPagesize());
            PageInfo<ExpertInfo> pageInfo = expertInfoService.selectExpertInfoList(pageIndex,pageSize);
			return ResultGenerator.genSuccessResult(pageInfo);
		}catch (Exception e){
			e.printStackTrace();
			LogUtil.getLogger(this.getClass()).info(e.getMessage());
			return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
		}
	}

    /**
     * 查看专家信息详情
     */
    @GetMapping("/info")
    @ApiOperation(value = "查看专家信息详情", notes = "查看专家信息详情")
    public Result info(@RequestParam String eiId, HttpServletRequest request)
    {
        try{
            ExpertInfo expertInfo = expertInfoService.getExpertInfo(eiId);
            if(expertInfo==null){
                return ResultGenerator.genCodeMsgResult(ResultCodeMessage.EXPERT_NOT_EXISTS.getCode(),
                        ResultCodeMessage.EXPERT_NOT_EXISTS.getMessage());
            }
            String authToken  = request.getHeader("token");
            if(StringUtils.isEmpty(authToken)){
                expertInfo.setFollowState(Constant.NOT_FOLLOWED);
            }else{
                Result result = TokenUtil.verifyToken(authToken);
                if(result.getCode()!=Constant.SERVER_SUCCESS){
                    return result;
                }
                Token token = (Token) result.getData();
                List<Integer> collectIds = commonService.selectFollowIds(token.getUserCode(),Constant.EXPERT);
                if(collectIds.contains(Integer.valueOf(eiId))){
                    expertInfo.setFollowState(Constant.FOLLOWED);
                }else{
                    expertInfo.setFollowState(Constant.NOT_FOLLOWED);
                }
            }
            return ResultGenerator.genSuccessResult(expertInfo);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }


}
