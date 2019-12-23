package cn.com.zx.ibossapi.controller.bizController;

import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Api(tags = "支付订单接口")
@ApiResponses(@ApiResponse(code = 500, message = "服务器内部异常",response = Result.class))
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrderDetail")
    @ApiOperation(value = "获取订单详情", notes = "获取订单详情")
    public String getOrderDetail(){
        return orderService.getOrderDetail();
    }

}
