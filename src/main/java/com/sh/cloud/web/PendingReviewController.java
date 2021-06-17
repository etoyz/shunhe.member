package com.sh.cloud.web;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.PlatUser;
import com.sft.member.bean.User;
import com.sft.member.obtain.pay.PayService;
import com.sh.cloud.entity.GetPendingReviewListRequest;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/review")
public class PendingReviewController {
    @Resource
    PayService payService;

    @RequestMapping("getPendingReviewList")
    public Map<String, Object> getPendingReviewList(@RequestBody GetPendingReviewListRequest request) {
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");
        ret.put("data", payService.getUnCheckRecord(request.getUser(), request.getCouponCheck(), request.getPage(), request.getLimit(), request.getGroupBy()));
        ret.put("count", payService.getUnCheckRecordCount(request.getUser(), request.getCouponCheck(), request.getGroupBy()));

        return ret;
    }

    @RequestMapping("checkCoupon")
    public String checkCoupon(@RequestParam String groupId) {
        return payService.checkCoupon(PlatUserUtils.getCurrentLoginPlatUser(), groupId);
    }
}
