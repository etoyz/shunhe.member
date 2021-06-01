package com.sh.cloud.web;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.ConsumeType;
import com.sft.member.bean.Coupon;
import com.sft.member.bean.PracticalProject;
import com.sft.member.obtain.consume.ConsumeProjectService;
import com.sft.member.obtain.consume.PracticalProjectService;
import com.sft.member.obtain.coupon.CouponService;
import com.sh.cloud.entity.ConsumeProjectMetaInfo;
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
        consumeProject.consumeProjectId = -1;
        ConsumeType consumeType1 = new ConsumeType();
        consumeType1.name = consumeType;
        List<ConsumeProject> dataByConsumeType = consumeProjectService.getConsumeProjectByConsumeType(consumeType1);
        List<ConsumeProject> dataByQuery = consumeProjectService.getConsumeProjectList(consumeProject, page, limit);
        ret.put("data", dataByQuery);
        ret.put("count", consumeProjectService.getConsumeProjectListCount(consumeProject));
        return ret;
    }

    @PostMapping("addConsumeProject")
    @ResponseBody
    public String addConsumeProject(@RequestBody ConsumeProjectMetaInfo consumeProjectMetaInfo) {
//        StringBuilder ret = new StringBuilder(consumeProjectService.addConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProjectMetaInfo.getBaseInfo()));
        consumeProjectService.addConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProjectMetaInfo.getBaseInfo());
        for (PracticalProject practicalProject : consumeProjectMetaInfo.getPracticalItems()) {
            practicalProject.consumeProjectId = consumeProjectMetaInfo.getBaseInfo().consumeProjectId;
//            ret.append(practicalProjectService.addPracticalProject(PlatUserUtils.getCurrentLoginPlatUser(), practicalProject));
            practicalProjectService.addPracticalProject(PlatUserUtils.getCurrentLoginPlatUser(), practicalProject);
        }
//        ret.append(consumeProjectService.addConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProjectMetaInfo.getBaseInfo()));
        couponService.updateCouponListByConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProjectMetaInfo.getBaseInfo(), consumeProjectMetaInfo.getRelateCoupons());
//        return String.valueOf(ret);
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
