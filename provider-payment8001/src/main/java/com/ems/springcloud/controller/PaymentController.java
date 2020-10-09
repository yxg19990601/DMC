package com.ems.springcloud.controller;

import com.ems.springcloud.entities.CommonResult;
import com.ems.springcloud.entities.Payment;
import com.ems.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;



    @GetMapping("/payment/getPaymentById/{id}")
    public CommonResult getById(@PathVariable("id") long id) {
        Payment payment = paymentService.selectByPrimaryKey(id);
        CommonResult commonResult = new CommonResult(200, "查询成功!");
        if (payment == null) {
            commonResult.setMsg("查询失败！");
            commonResult.setCode(500);
        }

        commonResult.setData(payment);


        return commonResult;
    }


    @PostMapping("/payment/insert")
    public CommonResult insert(@RequestBody Payment payment) {
        int result = paymentService.insert(payment);
        if(result == 1) {
            return  new CommonResult(200,"插入成功!");
        } else {
            return  new CommonResult(500,"插入失败!");
        }

    }

}
