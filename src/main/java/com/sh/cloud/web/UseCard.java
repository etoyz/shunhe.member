package com.sh.cloud.web;

import com.sft.member.obtain.pay.PayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("service/useCard")
public class UseCard {
    @Resource
    PayService payService;

//    @RequestMapping("getEnablePayCoupon")
//    public String getEnablePayCoupon(){
//        payService.getEnablePayCoupon();
//    }
}
