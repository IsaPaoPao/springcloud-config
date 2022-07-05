package com.atguigu.spingcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String PaymentInfo_Ok(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_Ok,id:" + id + "O(∩_∩)O~";
    }



    //value=3000,3秒以内走PaymentInfo_TimeOut，算正常方法，超过三秒，超时了，走fallbackMethod = PaymentInfo_TimeOutHandler
    @HystrixCommand(fallbackMethod = "PaymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String PaymentInfo_TimeOut(Integer id){
        int timeNumber = 5;
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_TimeOut,id:" + id + "O(∩_∩)O~" + "耗时(秒):" + timeNumber;
    }

    public String PaymentInfo_TimeOutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "请重试请重试请重试请重试请重试请重试请重试！！！！！";
    }
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id)
    {
        if(id < 0)
        {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(Integer id)
    {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
}
