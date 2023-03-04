package com.neusoft;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//开启Spring Cloud Feign的支持功能
@EnableFeignClients
public class MyApplicationBusiness10300 {
    //向容器中添加RestTemplate实例
//    @Bean
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }
    public static void main(String[] args) {
        SpringApplication.run(MyApplicationBusiness10300.class, args);
    }
}