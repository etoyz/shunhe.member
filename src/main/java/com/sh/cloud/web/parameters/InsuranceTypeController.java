package com.sh.cloud.web.parameters;

import com.sft.member.bean.InsuranceType;
import com.sft.member.insurance.InsuranceTypeService;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/parameters/insuranceType")
public class InsuranceTypeController {
    @Resource
    InsuranceTypeService insuranceTypeService;

    @RequiresPermissions({"member:customParameters:insuranceType:list"})
    @RequestMapping("getInsuranceTypeList")
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
    public List<InsuranceType> getInsuranceCompanyNameList() {
        return insuranceTypeService.getInsuranceTypeList(new InsuranceType(), 1, Integer.MAX_VALUE);
    }

    @RequiresPermissions({"member:customParameters:insuranceType:add"})
    @RequestMapping("addInsuranceType")
    public String addInsuranceType(@RequestBody InsuranceType insuranceType) {
        String ret = insuranceTypeService.addInsuranceType(PlatUserUtils.getCurrentLoginPlatUser(), insuranceType);
        if(ret == null)
            return "成功！";
        else
            return ret;
    }

    @RequiresPermissions({"member:customParameters:insuranceType:edit"})
    @RequestMapping("editInsuranceType")
    public String editInsuranceType(@RequestBody InsuranceType insuranceType) {
        insuranceTypeService.editInsuranceType(PlatUserUtils.getCurrentLoginPlatUser(), insuranceType);
        return "成功！";
    }

    @RequiresPermissions({"member:customParameters:insuranceType:delete"})
    @RequestMapping("deleteInsuranceType")
    public String deleteInsuranceType(@RequestParam String id) {
        InsuranceType insuranceType = new InsuranceType();
        insuranceType.id = id;
        if (insuranceTypeService.deleteInsuranceType(PlatUserUtils.getCurrentLoginPlatUser(), insuranceType) == null)
            return "删除成功！";
        else
            return "删除失败！";
    }
}
