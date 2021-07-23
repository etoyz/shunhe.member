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

    @RequestMapping("getInsuranceCompanyNameList")
    @ResponseBody
    public List<InsuranceCompany> getInsuranceCompanyNameList() {
        return insuranceCompanyService.getInsuranceCompanyList(new InsuranceCompany(), 1, Integer.MAX_VALUE);
    }

    @RequestMapping("getInsuranceCompany")
    @ResponseBody
    public InsuranceCompany getInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany) {
        return insuranceCompanyService.getInsuranceCompany(insuranceCompany);
    }

    @RequestMapping("addInsuranceCompany")
    @ResponseBody
    public String addInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany) {
        String ret = insuranceCompanyService.addInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany);
        if(ret == null)
            return "成功！";
        else
            return ret;
    }

    @RequestMapping("editInsuranceCompany")
    @ResponseBody
    public String editInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany) {
        insuranceCompanyService.editInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany);
        return "成功！";
    }

    @RequestMapping("deleteInsuranceCompany")
    @ResponseBody
    public String deleteInsuranceCompany(@RequestParam String id) {
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.id = id;
        if (insuranceCompanyService.deleteInsuranceCompany(PlatUserUtils.getCurrentLoginPlatUser(), insuranceCompany) == null)
            return "删除成功！";
        else
            return "删除失败！";
    }
}
