package cn.com.zx.ibossapi.controller.bizController;

import cn.com.zx.ibossapi.domain.Advt;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.result.ResultCodeMessage;
import cn.com.zx.ibossapi.result.ResultGenerator;
import cn.com.zx.ibossapi.service.AdvtService;
import cn.com.zx.ibossapi.util.LogUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** 广告信息处理
 * @author lvxuezhan
 * @date 2019/6/16
 **/
@RestController
@RequestMapping("/advt")
@Api(tags = "广告管理接口")
@ApiResponses(@ApiResponse(code = 500, message = "服务器内部异常",response = Result.class))
public class AdvtController {

    @Autowired
    private AdvtService advtService;

    /**
     * 获取广告列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取广告列表", notes = "获取广告列表")
    public Result list()
    {
        try{
            List<Advt> advtList = advtService.selectAdvtList();
            return ResultGenerator.genSuccessResult(advtList);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }

    /**
     * 获取广告详情
     * @return
     */
    @GetMapping("/info")
    @ApiOperation(value = "获取文章详细信息", notes = "获取文章详细信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "token", value = "请求头token",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "eaId", value = "文章编号",required = true, dataType = "String",paramType = "query")})
    public Result info(@RequestParam Integer adId)
    {
        try{
            Advt advt = advtService.getAdvtInfoByAdId(adId);
            return ResultGenerator.genSuccessResult(advt);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).info(e.getMessage());
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }




}
