package com.sh.cloud.web;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.UserCoupon;
import com.sft.member.obtain.pay.PayService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("service/useCard")
public class UseCard {
    @Resource
    PayService payService;

    @RequestMapping("getEnablePayCoupon")
    @ResponseBody
    public List<UserCoupon> getEnablePayCoupon(@RequestParam String userid, @RequestParam String consumeProjectId) {
        return payService.getEnablePayCoupon(userid, consumeProjectId);
    }

    @RequestMapping("submitForReview")
    @ResponseBody
    public String submitForReview(@RequestBody List<CouponCheck> list) {
        if (payService.addUnCheckRecord(PlatUserUtils.getCurrentLoginPlatUser(), list) == null)
            return "失败！";
        else
            return "成功！";
    }
}
