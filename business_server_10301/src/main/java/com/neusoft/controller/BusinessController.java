package com.neusoft.controller;

import com.neusoft.feign.FoodFeignClient;
import com.neusoft.po.Business;
import com.neusoft.po.CommonResult;
import com.neusoft.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
//@CrossOrigin("*")
@RestController
@RequestMapping("/BusinessController")
@RefreshScope //开启动态刷新
public class BusinessController {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private FoodFeignClient foodFeignClient;
//    @Autowired
//    private RestTemplate restTemplate;

//    @Autowired
//    private DiscoveryClient discoveryClient;

    @GetMapping("/listBusinessByOrderTypeId/{orderTypeId}")
    public CommonResult<List> listBusinessByOrderTypeId(@PathVariable("orderTypeId") Integer
                                                                orderTypeId) throws Exception{
        List<Business> list = businessService.listBusinessByOrderTypeId(orderTypeId);
        return new CommonResult(200,"success（10301）",list);
    }

    @GetMapping("/getBusinessById/{businessId}")
    public CommonResult<Business> getBusinessById(@PathVariable("businessId") Integer
                                                          businessId) throws Exception{

        Business business = businessService.getBusinessById(businessId);
//        //通过服务提供者名（food-server）获取Eureka Server上的元数据
//        List<ServiceInstance> instanceList = discoveryClient.getInstances("food-server");
//        //现在，元数据集合中只有一个服务信息（food-server）
//        ServiceInstance instance = instanceList.get(0);



        //在商家微服务中调用食品微服务
        CommonResult<List> result = foodFeignClient.listFoodByBusinessId(businessId);
        System.out.println(result.getMessage());
        //如果食品微服务返回降级响应，那么就返回空集合
        if(result.getCode()==200) {
            business.setFoodList(result.getResult());
        }else {
            business.setFoodList(new ArrayList());
        }
        return new CommonResult(200,"success（10301）",business);
    }
}