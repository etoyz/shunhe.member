package com.sh.cloud.web.parameters;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.Coupon;
import com.sft.member.obtain.consume.ConsumeProjectService;
import com.sft.member.obtain.coupon.CouponService;
import com.sft.member.obtain.log.LogService;
import com.sh.cloud.entity.ConsumeProjectAndConsumeProjects;
import com.sh.cloud.entity.CouponsAndConsumeProject;
import com.sh.cloud.utils.LogUtils;
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
    @Resource
    LogService logService;

    /**
     * 获取某消费类型下的消费项目列表
     *
     * @param query         查询参数（按名称查询）
     * @param consumeTypeId 消费类型ID
     * @return 消费项目列表
     */
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

    /**
     * 获取某消费类型下的消费项目名称的列表，用于下拉列表、关联弹窗
     *
     * @param consumeTypeId 消费类型ID
     * @return 消费项目名称的列表
     */
    @RequestMapping("getConsumeProjectNameList")
    public Map<String, Object> getConsumeProjectNameList(@RequestParam String consumeTypeId) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);

        ConsumeProject consumeProject = new ConsumeProject();
        if (consumeTypeId.equals(""))
            consumeProject.consumeTypeId = -1;
        else
            consumeProject.consumeTypeId = Integer.parseInt(consumeTypeId);
        List<ConsumeProject> dataByQuery = consumeProjectService.getConsumeProjectList(consumeProject, 1, Integer.MAX_VALUE);
        ret.put("data", dataByQuery);
        return ret;
    }

    /**
     * 新增消费项目
     *
     * @param project 消费项目信息
     * @return "成功！"|错误信息
     */
    @RequiresPermissions({"member:customParameters:consumeItem:add"})
    @PostMapping("addConsumeProject")
    public String addConsumeProject(@RequestBody ConsumeProject project) {
        String ret = consumeProjectService.addConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), project);
        if (ret == null) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("新增消费项目 消费项目名:" + project.name));
            return "成功！";
        } else
            return ret;
    }

    /**
     * 编辑消费项目
     *
     * @param project 新的消费项目信息
     * @return "成功！"
     */
    @RequiresPermissions({"member:customParameters:consumeItem:edit"})
    @PostMapping("editConsumeProject")
    public String editConsumeProject(@RequestBody ConsumeProject project) {
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("编辑消费项目 消费项目ID:" + project.consumeProjectId));
        consumeProjectService.editConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), project);
        return "成功！";
    }

    /**
     * 将某消费项目与多个卡券关联
     *
     * @param couponsAndConsumeProject 需要被关联的某消费项目和卡券列表
     * @return "成功！"
     */
    @RequiresPermissions({"member:customParameters:consumeItem:relCoupon"})
    @PostMapping("relateCoupon")
    public String relateCoupon(@RequestBody CouponsAndConsumeProject couponsAndConsumeProject) {
        ConsumeProject project = new ConsumeProject();
        project.consumeProjectId = couponsAndConsumeProject.getConsumeProjectId();
        couponService.updateCouponListByConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), project, couponsAndConsumeProject.getCoupons());
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("更新消费项目关联的消费卡券 消费项目ID:" + project.consumeProjectId));
        return "成功！";
    }

    /**
     * 删除消费项目
     *
     * @param id 消费项目ID
     * @return "删除失败！"|"删除失败！"
     */
    @RequiresPermissions({"member:customParameters:consumeItem:delete"})
    @PostMapping("deleteConsumeProject")
    public String deleteConsumeProject(@RequestParam String id) {
        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.consumeProjectId = Integer.parseInt(id);
        if (consumeProjectService.deleteConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProject)) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("删除消费项目 消费项目ID:" + id));
            return "删除成功！";
        } else
            return "删除失败！";
    }

    /**
     * 根据卡券查询关联的消费项目
     *
     * @param coupon 卡券信息
     * @return 关联的消费项目列表
     */
    @RequiresPermissions({"member:customParameters:coupon:relItem"})
    @RequestMapping("getConsumeProjectListByCoupon")
    public List<ConsumeProject> getConsumeProjectListByCoupon(@RequestBody Coupon coupon) {
        return consumeProjectService.getConsumeProjectByCoupon(coupon);
    }

    /**
     * 获取关联的消费项目
     *
     * @param project 某消费项目
     * @return 被某消费项目关联的消费项目列表
     */
    @RequiresPermissions({"member:customParameters:consumeItem:relItem"})
    @RequestMapping("getRelateConsumeProject")
    public List<ConsumeProject> getRelateConsumeProject(@RequestBody ConsumeProject project) {
        return consumeProjectService.getRelateConsumeProject(project);
    }

    /**
     * 将某消费项目与多个消费项目关联
     *
     * @param consumeProjectAndConsumeProjects 某消费项目与被关联的消费项目列表
     * @return "成功！"|失败原因
     */
    @RequiresPermissions({"member:customParameters:consumeItem:relItem"})
    @RequestMapping("relateConsumeProject")
    public String relateConsumeProject(@RequestBody ConsumeProjectAndConsumeProjects consumeProjectAndConsumeProjects) {
        String ret = consumeProjectService.setRelateConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProjectAndConsumeProjects.getProject(), consumeProjectAndConsumeProjects.getProjects());
        if (ret == null) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("更新消费项目关联的消费项目 消费项目ID:" + consumeProjectAndConsumeProjects.getProject().consumeProjectId));
            return "成功！";
        } else
            return ret;
    }
}
