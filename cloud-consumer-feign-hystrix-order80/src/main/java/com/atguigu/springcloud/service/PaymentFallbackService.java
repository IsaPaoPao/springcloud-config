package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{


    @Override
    public String PaymentInfo_Ok(Integer id) {
        return "PaymentFallbackService---------------------OK";
    }

    @Override
    public String PaymentInfo_TimeOut(Integer id) {
        return "PaymentFallbackService---------------------TimeOut";
    }
}
