package com.sh.cloud.web.parameters;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.Coupon;
import com.sft.member.obtain.consume.ConsumeProjectService;
import com.sft.member.obtain.coupon.CouponService;
import com.sh.cloud.entity.ConsumeProjectAndConsumeProjects;
import com.sh.cloud.entity.CouponsAndConsumeProject;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/parameters/consumeItem")
public class ConsumeProjectController {
    @Resource
    ConsumeProjectService consumeProjectService;
    @Resource
    CouponService couponService;

    @RequiresPermissions({"member:customParameters:consumeItem:list"})
    @RequestMapping("getConsumeProjectList")
    public Map<String, Object> getConsumeProjectList(@RequestParam String query, @RequestParam String consumeTypeId, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.name = query;
        if (consumeTypeId.equals(""))
            consumeProject.consumeTypeId = -1;
        else
            consumeProject.consumeTypeId = Integer.parseInt(consumeTypeId);
        List<ConsumeProject> dataByQuery = consumeProjectService.getConsumeProjectList(consumeProject, page, limit);
        ret.put("data", dataByQuery);
        ret.put("count", consumeProjectService.getConsumeProjectListCount(consumeProject));
        return ret;
    }

    @RequestMapping("getConsumeProjectNameList")
    public Map<String, Object> getConsumeProjectNameList(@RequestParam String query, @RequestParam String consumeTypeId) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);

        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.name = query;
        if (consumeTypeId.equals(""))
            consumeProject.consumeTypeId = -1;
        else
            consumeProject.consumeTypeId = Integer.parseInt(consumeTypeId);
        List<ConsumeProject> dataByQuery = consumeProjectService.getConsumeProjectList(consumeProject, 1, Integer.MAX_VALUE);
        ret.put("data", dataByQuery);
        return ret;
    }

    @RequiresPermissions({"member:customParameters:consumeItem:add"})
    @PostMapping("addConsumeProject")
    public String addConsumeProject(@RequestBody ConsumeProject project) {
        return consumeProjectService.addConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), project);
    }

    @RequiresPermissions({"member:customParameters:consumeItem:edit"})
    @PostMapping("editConsumeProject")
    public String editConsumeProject(@RequestBody ConsumeProject project) {
        consumeProjectService.editConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), project);
        return "成功！";
    }

    @RequiresPermissions({"member:customParameters:consumeItem:relCoupon"})
    @PostMapping("relateCoupon")
    public String relateCoupon(@RequestBody CouponsAndConsumeProject couponsAndConsumeProject) {
        ConsumeProject project = new ConsumeProject();
        project.consumeProjectId = couponsAndConsumeProject.getConsumeProjectId();
        couponService.updateCouponListByConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), project, couponsAndConsumeProject.getCoupons());
        return "成功！";
    }

    @RequiresPermissions({"member:customParameters:consumeItem:delete"})
    @PostMapping("deleteConsumeProject")
    public String deleteConsumeProject(@RequestParam String id) {
        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.consumeProjectId = Integer.parseInt(id);
        if (consumeProjectService.deleteConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProject))
            return "删除成功！";
        else
            return "删除失败！";
    }

    @RequiresPermissions({"member:customParameters:coupon:relItem"})
    @RequestMapping("getConsumeProjectListByCoupon")
    public List<ConsumeProject> getConsumeProjectListByCoupon(@RequestBody Coupon coupon) {
        return consumeProjectService.getConsumeProjectByCoupon(coupon);
    }

    @RequiresPermissions({"member:customParameters:consumeItem:relItem"})
    @RequestMapping("getRelateConsumeProject")
    public List<ConsumeProject> getRelateConsumeProject(@RequestBody ConsumeProject project) {
        return consumeProjectService.getRelateConsumeProject(project);
    }

    @RequiresPermissions({"member:customParameters:consumeItem:relItem"})
    @RequestMapping("relateConsumeProject")
    public String relateConsumeProject(@RequestBody ConsumeProjectAndConsumeProjects consumeProjectAndConsumeProjects) {
        String ret = consumeProjectService.setRelateConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProjectAndConsumeProjects.getProject(), consumeProjectAndConsumeProjects.getProjects());
        if (ret == null)
            return "成功！";
        else
            return ret;
    }
}
