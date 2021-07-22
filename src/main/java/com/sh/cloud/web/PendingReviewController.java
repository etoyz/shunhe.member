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
import java.util.List;
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
        List<CouponCheck> data;
        if (request.getGroupBy()) {
            data = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), true);
            for (CouponCheck c : data) {
                Float totalM = 0f;
                Integer cnt = 0;
                List<CouponCheck> t = payService.getUnCheckRecord(request.getUser(), c, request.getPage(), request.getLimit(), false);
                for (CouponCheck ct : t) {
                    totalM += Float.parseFloat(ct.totalMoney);
                    cnt += Integer.parseInt(ct.count);
                }
                c.totalMoney = String.valueOf(totalM);
                c.count = String.valueOf(cnt);
            }
        } else {
            data = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), false);
        }
        ret.put("data", data);
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
