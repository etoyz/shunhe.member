package com.sh.cloud.web.parameters;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.Coupon;
import com.sft.member.obtain.consume.ConsumeProjectService;
import com.sft.member.obtain.coupon.CouponService;
import com.sh.cloud.entity.CouponAndConsumeProjects;
import com.sh.cloud.utils.PlatUserUtils;
import io.swagger.models.auth.In;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("service/parameters/coupon")
public class CouponController {
    @Resource
    CouponService couponService;
    @Resource
    ConsumeProjectService projectService;

    @RequiresPermissions({"member:customParameters:coupon:delete"})
    @PostMapping("deleteCoupon")
    public String deleteCoupon(@RequestBody Coupon coupon) {
        if (couponService.deleteCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon))
            return "删除成功！";
        else
            return "删除失败！";
    }

    @RequiresPermissions({"member:customParameters:coupon:add"})
    @PostMapping(value = "addCoupon")
    public String addCoupon(@RequestBody Coupon coupon) {
//        SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
//        coupon.couponId = Integer.parseInt(df.format(new Date()) + getRandom(4));
        String ret = couponService.addCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon);
        if (ret == null || ret.equals(""))
            return "添加成功";
        else
            return ret;
    }

    public static String getRandom(int len) {
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }

//    @Deprecated
    @PostMapping("getCouponTypes")
    public Map<String, Object> getCouponTypes() {
        //获取后台给的卡券类型
        Map<String, Object> res = new HashMap<>();
        res.put("status", "ok");
        List<String> li = new ArrayList<>();
        li.add("代金券");
        li.add("储值");
        li.add("消费券");
        res.put("types", li);
//        Thread.sleep(900);
        return res;
    }

    @RequiresPermissions({"member:customParameters:coupon:edit"})
    @PostMapping("editCoupon")
    public String editCoupon(@RequestBody Coupon coupon) {
        couponService.editCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon);
        return "修改成功！";
    }

    @RequiresPermissions({"member:customParameters:coupon:relItem"})
    @PostMapping("relateConsumeProject")
    public String relateConsumeItem(@RequestBody CouponAndConsumeProjects couponAndConsumeProjects) {
        projectService.setConsumeProjectByCoupon(PlatUserUtils.getCurrentLoginPlatUser(), couponAndConsumeProjects.getCoupon(), couponAndConsumeProjects.getProjects());
        return "关联成功！";
    }

    @RequiresPermissions({"member:customParameters:coupon:list"})
    @GetMapping("getCouponList")
    public Map<String, Object> getCouponList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        Coupon c = new Coupon();
        c.name = query;
        c.type = -1;
        List<Coupon> data = couponService.getCouponList(c, page, limit);
        ret.put("count", couponService.getCouponListCount(c));
        ret.put("data", data);

        return ret;
    }

    @GetMapping("getCouponNameList")
    public Map<String, Object> getCouponNameList() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        List<Coupon> data = couponService.getCouponNameList();
        ret.put("data", data);

        return ret;
    }

    @RequiresPermissions({"member:customParameters:consumeItem:relCoupon"})
    @RequestMapping("getCouponListByConsumeProject")
    public List<Coupon> getConsumeProjectListByCoupon(@RequestBody ConsumeProject project) {
        return couponService.getCouponListByConsumeProject(project);
    }

    @RequestMapping("getCoupon")
    Coupon getCoupon(@RequestParam int couponId) {
        Coupon coupon = new Coupon();
        coupon.couponId = couponId;
        return couponService.getCoupon(coupon);
    }
}
