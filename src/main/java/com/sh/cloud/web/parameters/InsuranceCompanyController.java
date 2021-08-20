package com.sh.cloud.web.parameters;

import com.sft.member.bean.InsuranceCompany;
import com.sft.member.insurance.InsuranceCompanyService;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/parameters/insuranceCompany")
public class InsuranceCompanyController {
    @Resource
    InsuranceCompanyService insuranceCompanyService;

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

    @RequestMapping("getInsuranceCompanyNameList")
    public List<InsuranceCompany> getInsuranceCompanyNameList() {
        return insuranceCompanyService.getInsuranceCompanyList(new InsuranceCompany(), 1, Integer.MAX_VALUE);
    }

    @RequiresPermissions({"member:customParameters:insuranceCompany:add"})
    @RequestMapping("addInsuranceCompany")
    public String addInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany) {
        String ret = insuranceCompanyService.addInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany);
        if(ret == null)
            return "成功！";
        else
            return ret;
    }

    @RequiresPermissions({"member:customParameters:insuranceCompany:edit"})
    @RequestMapping("editInsuranceCompany")
    public String editInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany) {
        insuranceCompanyService.editInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany);
        return "成功！";
    }

    @RequiresPermissions({"member:customParameters:insuranceCompany:delete"})
    @RequestMapping("deleteInsuranceCompany")
    public String deleteInsuranceCompany(@RequestParam String id) {
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.id = id;
        if (insuranceCompanyService.deleteInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany) == null)
            return "删除成功！";
        else
            return "删除失败！";
    }
}
