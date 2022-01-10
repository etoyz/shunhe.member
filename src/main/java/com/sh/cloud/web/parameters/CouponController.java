package com.sh.cloud.web.parameters;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.Coupon;
import com.sft.member.obtain.consume.ConsumeProjectService;
import com.sft.member.obtain.coupon.CouponService;
import com.sft.member.obtain.log.LogService;
import com.sh.cloud.entity.CouponAndConsumeProjects;
import com.sh.cloud.utils.LogUtils;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/parameters/coupon")
public class CouponController {
    @Resource
    CouponService couponService;
    @Resource
    ConsumeProjectService projectService;
    @Resource
    LogService logService;

    /**
     * 删除卡券
     *
     * @param coupon 卡券信息（只需要设置ID）
     * @return "删除成功！"|"删除失败！"
     */
    @RequiresPermissions({"member:customParameters:coupon:delete"})
    @PostMapping("deleteCoupon")
    public String deleteCoupon(@RequestBody Coupon coupon) {
        if (couponService.deleteCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon)) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("删除卡券 卡券ID:" + coupon.couponId));
            return "删除成功！";
        } else
            return "删除失败！";
    }

    /**
     * 新增卡券
     *
     * @param coupon 卡券信息
     * @return "添加成功"|失败原因
     */
    @RequiresPermissions({"member:customParameters:coupon:add"})
    @PostMapping(value = "addCoupon")
    public String addCoupon(@RequestBody Coupon coupon) {
        String ret = couponService.addCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon);
        if (ret == null || ret.equals("")) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("新增卡券 卡券名称:" + coupon.name));
            return "添加成功";
        } else
            return ret;
    }

    /**
     * 获取卡券类型
     *
     * @return 卡券类型列表
     */
    @PostMapping("getCouponTypes")
    public Map<String, Object> getCouponTypes() {
        //获取后台给的卡券类型
        Map<String, Object> res = new HashMap<>();
        res.put("status", "ok");
        Map<Integer, String> types = new HashMap<>();
        types.put(0, "代金券");
        types.put(1, "储值");
        types.put(2, "消费券");
        res.put("types", types);
        return res;
    }

    /**
     * 编辑卡券信息
     *
     * @param coupon 新的卡券信息
     * @return 编辑后的新的卡券信息
     */
    @RequiresPermissions({"member:customParameters:coupon:edit"})
    @PostMapping("editCoupon")
    public Coupon editCoupon(@RequestBody Coupon coupon) {
        Coupon couponRes = couponService.editCoupon(PlatUserUtils.getCurrentLoginPlatUser(), coupon);
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("编辑卡券 卡券ID:" + coupon.couponId));
        return couponRes;
    }

    /**
     * 将某卡券与多个消费项目关联起来
     *
     * @param couponAndConsumeProjects 某卡券与消费项目列表
     * @return "关联成功！"
     */
    @RequiresPermissions({"member:customParameters:coupon:relItem"})
    @PostMapping("relateConsumeProject")
    public String relateConsumeItem(@RequestBody CouponAndConsumeProjects couponAndConsumeProjects) {
        projectService.setConsumeProjectByCoupon(PlatUserUtils.getCurrentLoginPlatUser(), couponAndConsumeProjects.getCoupon(), couponAndConsumeProjects.getProjects());
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("更新卡券关联的消费项目 卡券ID:" + couponAndConsumeProjects.getCoupon().couponId));
        return "关联成功！";
    }

    /**
     * 根据查询参数获取卡券列表
     *
     * @param query 查询参数（按名称查询）
     * @return 卡券列表
     */
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

    /**
     * 获取全部卡券的名称
     *
     * @return 全部卡券的名称的列表
     */
    @GetMapping("getCouponNameList")
    public Map<String, Object> getCouponNameList() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        List<Coupon> data = couponService.getCouponNameList();
        ret.put("data", data);

        return ret;
    }

    /**
     * 获取与某个消费项目关联的卡券的列表
     *
     * @param project 消费项目
     * @return 卡券列表
     */
    @RequiresPermissions({"member:customParameters:consumeItem:relCoupon"})
    @RequestMapping("getCouponListByConsumeProject")
    public List<Coupon> getCouponListByConsumeProject(@RequestBody ConsumeProject project) {
        return couponService.getCouponListByConsumeProject(project);
    }

    /**
     * 获取卡券具体信息
     *
     * @param couponId 卡券ID
     * @return 卡券具体信息
     */
    @RequestMapping("getCoupon")
    Coupon getCoupon(@RequestParam int couponId) {
        Coupon coupon = new Coupon();
        coupon.couponId = couponId;
        return couponService.getCoupon(coupon);
    }
}
