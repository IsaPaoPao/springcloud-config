package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    //引入service层
    @Resource
    private PaymentService paymentService;

    //动态获取服务端口号，读取配置文件里的值
    @Value("${server.port}")
    private String serverPort;

    //服务发现Discovery
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("******插入结果:result={}",result);
        if (result > 0){
            return new CommonResult(200,"插入数据库成功,serverPort:" + serverPort,result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping("/payment/get/id")
    public CommonResult getPaymentById(@RequestParam Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("******查询结果{}",payment);
        if (payment != null){
            return new CommonResult(200,"查询成功,serverPort:" + serverPort,payment);
        }else {
            return new CommonResult(444,"没有对应记录,查询ID：" + id,null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*******service:{}",service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("*******serviceId:{},host:{},port:{},uri:{} ",instance.getServiceId(),instance.getHost(),
                                                                  instance.getPort(),instance.getUri());
        }
        return this.discoveryClient;
    }

}
