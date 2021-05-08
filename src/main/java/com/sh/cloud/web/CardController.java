package com.sh.cloud.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.Coupon;
import com.sft.member.obtain.coupon.CouponService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/coupon")
public class CardController {

    @Resource
    CouponService couponService;

    @PostMapping("deleteCoupon")
    public String deleteCoupon(@RequestBody Coupon coupon) {
        if (couponService.deleteCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon))
            return "删除成功！";
        else
            return "删除失败！";
    }

    @PostMapping(value = "addCoupon")
    public String addCoupon(@RequestBody Coupon coupon) {
        return couponService.addCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon);
    }

    @Deprecated
    @PostMapping("getCouponTypes")
    public Map<String, Object> getCouponTypes() {
        //获取后台给的卡券类型
        Map<String, Object> res = new HashMap<>();
        res.put("status", "ok");
        List<String> li = new ArrayList<>();
        li.add("代金券");
        li.add("储值");
        res.put("types", li);
//        Thread.sleep(900);
        return res;
    }

    @PostMapping("modCoupon")
    @ResponseBody
    public String modCoupon(@RequestBody Coupon coupon) {
        couponService.editCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon);
        return "ok";
    }

    @PostMapping("relateConsumeItem")
    public String relateConsumeItem(@RequestBody Map<String, Object> items) {
        Coupon c = (Coupon) items.get("selectedCoupon");
        List<ConsumeProject> itemsList = (List<ConsumeProject>) items.get("selectedItems");

        return items.toString();
    }

    @PostMapping("getCouponList")
    public Map<String, Object> getCouponList() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");
        ret.put("count", 2);
        ret.put("data", couponService.getCouponNameList());
        return ret;
    }
}
