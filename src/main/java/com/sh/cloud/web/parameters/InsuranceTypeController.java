package com.sh.cloud.web.parameters;

import com.sft.member.bean.InsuranceType;
import com.sft.member.insurance.InsuranceTypeService;
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
@RequestMapping("service/parameters/insuranceType")
public class InsuranceTypeController {
    @Resource
    InsuranceTypeService insuranceTypeService;
    @Resource
    LogService logService;

    /**
     * 根据查询参数获取险种列表
     * @param query 查询参数（按名称查询）
     * @return 险种列表
     */
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

    /**
     * 获取全部的险种名称
     * @return 全部的险种名称的列表
     */
    @RequestMapping("getInsuranceTypeNameList")
    public List<InsuranceType> getInsuranceCompanyNameList() {
        return insuranceTypeService.getInsuranceTypeList(new InsuranceType(), 1, Integer.MAX_VALUE);
    }

    /**
     * 新增险种
     * @param insuranceType 险种信息
     * @return "成功！"|失败原因
     */
    @RequiresPermissions({"member:customParameters:insuranceType:add"})
    @RequestMapping("addInsuranceType")
    public String addInsuranceType(@RequestBody InsuranceType insuranceType) {
        String ret = insuranceTypeService.addInsuranceType(PlatUserUtils.getCurrentLoginPlatUser(), insuranceType);
        if (ret == null) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("新增险种 险种名称:" + insuranceType.name));
            return "成功！";
        } else
            return ret;
    }

    /**
     * 编辑险种
     * @param insuranceType 新的险种信息
     * @return "成功！"
     */
    @RequiresPermissions({"member:customParameters:insuranceType:edit"})
    @RequestMapping("editInsuranceType")
    public String editInsuranceType(@RequestBody InsuranceType insuranceType) {
        insuranceTypeService.editInsuranceType(PlatUserUtils.getCurrentLoginPlatUser(), insuranceType);
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("编辑险种 险种ID:" + insuranceType.id));
        return "成功！";
    }

    /**
     * 删除险种
     * @param id 险种ID
     * @return "删除成功！"|"删除失败！"
     */
    @RequiresPermissions({"member:customParameters:insuranceType:delete"})
    @RequestMapping("deleteInsuranceType")
    public String deleteInsuranceType(@RequestParam String id) {
        InsuranceType insuranceType = new InsuranceType();
        insuranceType.id = id;
        if (insuranceTypeService.deleteInsuranceType(PlatUserUtils.getCurrentLoginPlatUser(), insuranceType) == null) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("删除险种 险种ID:" + insuranceType.id));
            return "删除成功！";
        } else
            return "删除失败！";
    }
}
