package com.atguigu.spingcloud.controller;

import com.atguigu.spingcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/id")
    public String PaymentInfo_Ok(@RequestParam("id") Integer id){
        String result = paymentService.PaymentInfo_Ok(id);
        log.info("**********result:{}",result);
        return result;
    }

    @GetMapping("/payment/hystrix/timeout/id")
    public String PaymentInfo_TimeOut(@RequestParam("id") Integer id){
        String result = paymentService.PaymentInfo_TimeOut(id);
        log.info("**********result:{}",result);
        return result;
    }


    //====服务熔断
    @GetMapping("/payment/circuit/id")
    public String paymentCircuitBreaker(@RequestParam("id") Integer id)
    {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result: "+result);
        return result;
    }
}
