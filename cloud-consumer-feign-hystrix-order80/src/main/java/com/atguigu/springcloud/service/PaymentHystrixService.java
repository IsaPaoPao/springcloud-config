package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {
    @GetMapping("/payment/hystrix/ok/id")
     String PaymentInfo_Ok(@RequestParam("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/id")
     String PaymentInfo_TimeOut(@RequestParam("id") Integer id);
}
