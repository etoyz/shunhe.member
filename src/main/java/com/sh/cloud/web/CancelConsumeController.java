package com.sh.cloud.web;

import com.sft.member.bean.PlatUser;
import com.sft.member.obtain.pay.PayService;
import com.sh.cloud.entity.GetConsumeListRequest;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("service/cancelConsume")
public class CancelConsumeController {
    @Resource
    PayService payService;

    @RequestMapping("getConsumeList")
    public Map<String, Object> getConsumeList(@RequestBody GetConsumeListRequest request) {
        Map<String, Object> ret = new HashMap();

        ret.put("code", 0);
        ret.put("msg", "");
        ret.put("data", payService.getUnCheckRecord(request.getUser(), request.getCouponCheck(), request.getPage(), request.getLimit(), request.getGroupBy()));
        ret.put("count", payService.getUnCheckRecordCount(request.getUser(), request.getCouponCheck(), request.getGroupBy()));

        return ret;
    }

    @RequestMapping("rollBack")
    public String rollBack(@RequestParam String consumeID) {
        return payService.rollBack(PlatUserUtils.getCurrentLoginPlatUser(), consumeID);
    }


}
