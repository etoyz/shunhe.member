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
@RequestMapping("service/cancelConsume")
public class CancelConsumeController {
    @Resource
    PayService payService;

    // 获取整个列表
    @RequestMapping("getConsumeList")
    public Map<String, Object> getConsumeList(@RequestBody GetPendingReviewListRequest request) {
        CouponCheck couponCheck = request.getCouponCheck();
        couponCheck.type = "1";
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");
        List<CouponCheck> data;
        if (request.getGroupBy()) {
            data = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), true);
            for (CouponCheck c : data) {
                couponCheck.groupId = c.groupId;
                Float totalM = 0f;
                Integer cnt = 0;
                List<CouponCheck> t = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), false);
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

    @RequestMapping("rollBack")
    public String rollBack(@RequestParam String id) {
        String ret = payService.rollBack(PlatUserUtils.getCurrentLoginPlatUser(), id);
        if (ret.equals(""))
            return "成功！";
        else
            return ret;
    }


}
