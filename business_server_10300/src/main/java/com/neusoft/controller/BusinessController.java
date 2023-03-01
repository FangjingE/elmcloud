package com.neusoft.controller;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.feign.FoodFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import com.neusoft.po.Business;
import com.neusoft.po.CommonResult;
import com.neusoft.po.Food;
import com.neusoft.service.BusinessService;
@CrossOrigin("*")
@RestController
@RequestMapping("/BusinessController")
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
        return new CommonResult(200,"success",list);
    }

    @RequestMapping("/getBusinessById/{businessId}")
    public CommonResult<Business> getBusinessById(@PathVariable("businessId") Integer
                                                          businessId) throws Exception{

        Business business = businessService.getBusinessById(businessId);
//        //通过服务提供者名（food-server）获取Eureka Server上的元数据
//        List<ServiceInstance> instanceList = discoveryClient.getInstances("food-server");
//        //现在，元数据集合中只有一个服务信息（food-server）
//        ServiceInstance instance = instanceList.get(0);



        //使用DiscoveryClient获取元数据，主机地址与端口就可以不硬编码了
        CommonResult<List> result =
                foodFeignClient.listFoodByBusinessId(businessId);
        System.out.println(result.getMessage());
        if(result.getCode()==200) {
            business.setFoodList(result.getResult());
        }
        return new CommonResult(200,"success",business);
    }
}