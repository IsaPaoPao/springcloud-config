package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")//全局服务降级，fallback方法
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/id")
    String PaymentInfo_Ok(@RequestParam("id") Integer id){
        String result = paymentHystrixService.PaymentInfo_Ok(id);
        return result;
    }

    @GetMapping("/consumer/payment/hystrix/timeout/id")
    /*@HystrixCommand(fallbackMethod = "PaymentTimeOutFallBackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })*/
    @HystrixCommand
    public String PaymentInfo_TimeOut(Integer id){
        int timeNumber = 5;
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_TimeOut,id:" + id + "O(∩_∩)O~" + "耗时(秒):" + timeNumber;
    }

    public String PaymentTimeOutFallBackMethod(Integer id){
        return "我是消费者80，对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己";
    }

    // 下面是全局fallback方法
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
    }

}
