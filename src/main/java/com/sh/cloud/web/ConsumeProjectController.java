package com.sh.cloud.web;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.Coupon;
import com.sft.member.obtain.consume.ConsumeProjectService;
import com.sft.member.obtain.consume.PracticalProjectService;
import com.sft.member.obtain.coupon.CouponService;
import com.sh.cloud.entity.CouponsAndConsumeProject;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("service/consumeProject")
public class ConsumeProjectController {
    @Resource
    ConsumeProjectService consumeProjectService;
    @Resource
    PracticalProjectService practicalProjectService;
    @Resource
    CouponService couponService;

    @RequestMapping("getConsumeProjectList")
    @ResponseBody
    public Map<String, Object> getProjectList(@RequestParam String query, @RequestParam String consumeType, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.name = query;
        if (consumeType.equals(""))
            consumeProject.consumeTypeId = -1;
        else
            consumeProject.consumeTypeId = Integer.parseInt(consumeType);
        List<ConsumeProject> dataByQuery = consumeProjectService.getConsumeProjectList(consumeProject, page, limit);
        ret.put("data", dataByQuery);
        ret.put("count", consumeProjectService.getConsumeProjectListCount(consumeProject));
        return ret;
    }

    @PostMapping("addConsumeProject")
    @ResponseBody
    public String addConsumeProject(@RequestBody ConsumeProject project) {
        consumeProjectService.addConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), project);
        return "成功！";
    }

    @PostMapping("editConsumeProject")
    @ResponseBody
    public String editConsumeProject(@RequestBody ConsumeProject project) {
        consumeProjectService.editConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), project);
        return "成功！";
    }

    @PostMapping("relateCoupon")
    @ResponseBody
    public String relateCoupon(@RequestBody CouponsAndConsumeProject couponsAndConsumeProject) {
        ConsumeProject project = new ConsumeProject();
        project.consumeProjectId = couponsAndConsumeProject.getConsumeProjectId();
        couponService.updateCouponListByConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), project, couponsAndConsumeProject.getCoupons());
        return "成功！";
    }

    @PostMapping("deleteConsumeProject")
    @ResponseBody
    public String deleteConsumeProject(@RequestParam String id) {
        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.consumeProjectId = Integer.parseInt(id);
        if (consumeProjectService.deleteConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProject))
            return "删除成功！";
        else
            return "删除失败！";
    }

    @RequestMapping("getConsumeProjectListByCoupon")
    @ResponseBody
    public List<ConsumeProject> getConsumeProjectListByCoupon(@RequestBody Coupon coupon) {
        return consumeProjectService.getConsumeProjectByCoupon(coupon);
    }
}
