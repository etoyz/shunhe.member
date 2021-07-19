package com.sh.cloud.web;

import com.sft.member.bean.InsuranceType;
import com.sft.member.insurance.InsuranceTypeService;
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
@RequestMapping("service/insuranceType")
public class InsuranceTypeController {
    @Resource
    InsuranceTypeService insuranceTypeService;

    @RequestMapping("getInsuranceTypeList")
    @ResponseBody
    public Map<String, Object> getInsuranceTypeList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        InsuranceType insuranceType = new InsuranceType();
        insuranceType.name = query;
        List<InsuranceType> data = insuranceTypeService.getInsuranceTypeList(insuranceType, page, limit);
        ret.put("count", insuranceTypeService.getInsuranceTypeListCount(insuranceType));
        ret.put("data", data);
        return ret;
    }

    @RequestMapping("getInsuranceTypeNameList")
    @ResponseBody
    public List<InsuranceType> getInsuranceCompanyNameList() {
        return insuranceTypeService.getInsuranceTypeList(new InsuranceType(), 1, Integer.MAX_VALUE);
    }

    @RequestMapping("addInsuranceType")
    @ResponseBody
    public String addInsuranceType(@RequestBody InsuranceType insuranceType) {
        return insuranceTypeService.addInsuranceType(PlatUserUtils.getCurrentLoginPlatUser(), insuranceType);
    }

    @RequestMapping("editInsuranceType")
    @ResponseBody
    public String editInsuranceType(@RequestBody InsuranceType insuranceType) {
        insuranceTypeService.editInsuranceType(PlatUserUtils.getCurrentLoginPlatUser(), insuranceType);
        return "成功！";
    }

    @RequestMapping("deleteInsuranceType")
    @ResponseBody
    public String deleteInsuranceType(@RequestParam String id) {
        InsuranceType insuranceType = new InsuranceType();
        insuranceType.id = id;
        if (insuranceTypeService.deleteInsuranceType(PlatUserUtils.getCurrentLoginPlatUser(), insuranceType) == null)
            return "删除成功！";
        else
            return "删除失败！";
    }
}
