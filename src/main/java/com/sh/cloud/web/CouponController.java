package com.sh.cloud.web;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.Coupon;
import com.sft.member.obtain.coupon.CouponService;
import com.sh.cloud.entity.CouponAndConsumeProjects;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/coupon")
public class CouponController {

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
        String ret = couponService.addCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon);
        if (ret == null || ret.equals(""))
            return "添加成功";
        else
            return ret;
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
    public String modCoupon(@RequestBody Coupon coupon) {
        couponService.editCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon);
        return "修改成功！";
    }

    @PostMapping("relateConsumeProject")
    public String relateConsumeItem(@RequestBody CouponAndConsumeProjects couponAndConsumeProjects) {
//        Coupon c = (Coupon) items.get("selectedCoupon");
//        List<ConsumeProject> itemsList = (List<ConsumeProject>) items.get("selectedItems");
//        couponService.updateCouponListByConsumeProject();
//        List
        for (ConsumeProject consumeProject : couponAndConsumeProjects.getProjects()) {
            List<Coupon> li = new ArrayList<>();
            li.add(couponAndConsumeProjects.getCoupon());
            couponService.updateCouponListByConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProject, li);
        }
        return "关联成功！";
    }

    @GetMapping("getCouponList")
    public Map<String, Object> getCouponList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        Coupon c = new Coupon();
        c.name = query;
        c.type = 666;
        List<Coupon> data = couponService.getCouponList(c, page, limit);
        ret.put("count", couponService.getCouponListCount(c));
        ret.put("data", data);

        return ret;
    }

    @RequestMapping("getCouponListByConsumeProject")
    @ResponseBody
    public List<Coupon> getConsumeProjectListByCoupon(@RequestBody ConsumeProject project) {
        return couponService.getCouponListByConsumeProject(project);
    }
}
