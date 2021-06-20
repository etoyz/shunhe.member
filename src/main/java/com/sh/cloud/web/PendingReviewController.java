package com.sh.cloud.web;

import com.sft.member.bean.CouponCheck;
import com.sft.member.obtain.pay.PayService;
import com.sh.cloud.entity.GetPendingReviewListRequest;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("service/review")
public class PendingReviewController {
    @Resource
    PayService payService;

    @RequestMapping("getPendingReviewList")
    public Map<String, Object> getPendingReviewList(@RequestBody GetPendingReviewListRequest request) {
        CouponCheck couponCheck = request.getCouponCheck();
        couponCheck.type = "0";
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");
        ret.put("data", payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), request.getGroupBy()));
        ret.put("count", payService.getUnCheckRecordCount(request.getUser(), couponCheck, request.getGroupBy()));

        return ret;
    }

    @RequestMapping("checkCoupon")
    public String checkCoupon(@RequestParam String groupId) {
        String ret = payService.checkCoupon(PlatUserUtils.getCurrentLoginPlatUser(), groupId);
        if (ret.equals(""))
            return "成功！";
        else
            return ret;
    }

    @RequestMapping("cancelRecord")
    public String cancelRecord(@RequestParam String groupId) {
        String ret = payService.cancelRecord(PlatUserUtils.getCurrentLoginPlatUser(), groupId);
        if (ret == null)
            return "成功！";
        else
            return ret;
    }
}
