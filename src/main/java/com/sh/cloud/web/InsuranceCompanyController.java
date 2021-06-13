package com.sh.cloud.web;

import com.sft.member.bean.InsuranceCompany;
import com.sft.member.insurance.InsuranceCompanyService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("service/insuranceCompany")
public class InsuranceCompanyController {
    @Resource
    InsuranceCompanyService insuranceCompanyService;

    @RequestMapping("getInsuranceCompanyList")
    @ResponseBody
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

    @RequestMapping("addInsuranceCompany")
    @ResponseBody
    public String addInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany) {
        return insuranceCompanyService.addInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany);
    }

    @RequestMapping("editInsuranceCompany")
    @ResponseBody
    public InsuranceCompany editInsuranceCompany(@RequestParam String id, @RequestParam String name) {
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.id = id;
        insuranceCompany.name = name;
        insuranceCompanyService.editInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany);
        return insuranceCompany;
    }

    @RequestMapping("deleteInsuranceCompany")
    @ResponseBody
    public String deleteInsuranceCompany(@RequestParam String id) {
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.id = id;
        Boolean ba;
        if (ba =   Boolean.parseBoolean(insuranceCompanyService.deleteInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany)))
            return "删除成功！";
        else
            return "删除失败！";
    }
}