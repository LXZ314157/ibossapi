package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.mapper.pay.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信 服务实现层
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public String getOrderDetail() {
        return orderMapper.getOrderDetail();
    }
}
