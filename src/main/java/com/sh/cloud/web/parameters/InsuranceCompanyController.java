package com.sh.cloud.web.parameters;

import com.sft.member.bean.InsuranceCompany;
import com.sft.member.insurance.InsuranceCompanyService;
import com.sft.member.obtain.log.LogService;
import com.sh.cloud.utils.LogUtils;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/parameters/insuranceCompany")
public class InsuranceCompanyController {
    @Resource
    InsuranceCompanyService insuranceCompanyService;
    @Resource
    LogService logService;

    /**
     * 根据查询参数获取保险公司列表
     * @param query 查询参数（按名称查询）
     * @return 保险公司列表
     */
    @RequiresPermissions({"member:customParameters:insuranceCompany:list"})
    @RequestMapping("getInsuranceCompanyList")
    public Map<String, Object> getInsuranceCompanyList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.name = query;
        List<InsuranceCompany> data = insuranceCompanyService.getInsuranceCompanyList(insuranceCompany, page, limit);
        ret.put("count", insuranceCompanyService.getInsuranceCompanyListCount(insuranceCompany));
        ret.put("data", data);
        return ret;
    }

    /**
     * 获取全部保险公司的名称（用于下拉框）
     * @return 全部保险公司的名称的列表
     */
    @RequestMapping("getInsuranceCompanyNameList")
    public List<InsuranceCompany> getInsuranceCompanyNameList() {
        return insuranceCompanyService.getInsuranceCompanyList(new InsuranceCompany(), 1, Integer.MAX_VALUE);
    }

    /**
     * 新增保险公司
     * @param insuranceCompany 保险公司信息
     * @return "成功！"|失败原因
     */
    @RequiresPermissions({"member:customParameters:insuranceCompany:add"})
    @RequestMapping("addInsuranceCompany")
    public String addInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany) {
        String ret = insuranceCompanyService.addInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany);
        if (ret == null) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("新增保险公司 保险公司名称:" + insuranceCompany.name));
            return "成功！";
        } else
            return ret;
    }

    /**
     * 编辑保险公司信息
     * @param insuranceCompany 新的保险公司信息
     * @return "成功！"
     */
    @RequiresPermissions({"member:customParameters:insuranceCompany:edit"})
    @RequestMapping("editInsuranceCompany")
    public String editInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany) {
        insuranceCompanyService.editInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany);
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("编辑保险公司 保险公司ID:" + insuranceCompany.id));
        return "成功！";
    }

    /**
     * 删除保险公司
     * @param id 保险公司ID
     * @return "删除成功！"|"删除失败！"
     */
    @RequiresPermissions({"member:customParameters:insuranceCompany:delete"})
    @RequestMapping("deleteInsuranceCompany")
    public String deleteInsuranceCompany(@RequestParam String id) {
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.id = id;
        if (insuranceCompanyService.deleteInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany) == null) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("删除保险公司 保险公司ID:" + insuranceCompany.id));
            return "删除成功！";
        } else
            return "删除失败！";
    }
}
