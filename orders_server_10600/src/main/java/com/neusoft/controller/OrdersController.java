package com.neusoft.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neusoft.po.CommonResult;
import java.util.List;
import com.neusoft.po.Orders;
import com.neusoft.service.OrdersService;
//@CrossOrigin("*")
@RestController
@RequestMapping("/OrdersController")
@RefreshScope //开启动态刷新
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/createOrders/{userId}/{businessId}/{daId}/{orderTotal}")
    public CommonResult<Integer> createOrders(
            @PathVariable("userId") String userId,
            @PathVariable("businessId") Integer businessId,
            @PathVariable("daId") Integer daId,
            @PathVariable("orderTotal") Double orderTotal) throws Exception{
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setBusinessId(businessId);
        orders.setDaId(daId);
        orders.setOrderTotal(orderTotal);
        int orderId = ordersService.createOrders(orders);
        return new CommonResult(200,"success（10600）",orderId);
    }

    @GetMapping("/getOrdersById/{orderId}")
    public CommonResult<Orders> getOrdersById(
            @PathVariable("orderId") Integer orderId) throws Exception{
        Orders orders = ordersService.getOrdersById(orderId);
        return new CommonResult(200,"success（10600）",orders);
    }

    @GetMapping("/listOrdersByUserId/{userId}")
    public CommonResult<List> listOrdersByUserId(
            @PathVariable("userId") String userId) throws Exception{
        List<Orders> list = ordersService.listOrdersByUserId(userId);
        return new CommonResult(200,"success（10600）",list);
    }
}
