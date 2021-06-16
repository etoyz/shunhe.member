package com.sh.cloud.web;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import com.sft.member.obtain.pay.PayService;
import com.sh.cloud.entity.GetPendingReviewListRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @ResponseBody
    public Map<String, Object> getPendingReviewList(@RequestBody GetPendingReviewListRequest request) {
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");
        List<CouponCheck> data = payService.getUnCheckRecord(request.getUser(), request.getCouponCheck(), request.getPage(), request.getLimit(), request.getGroupBy());
        ret.put("data", data);
        ret.put("count", data.size());

        return ret;
    }
}
