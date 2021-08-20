package com.sh.cloud.web.parameters;

import com.sft.member.bean.ConsumeType;
import com.sft.member.obtain.consume.ConsumeTypeService;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/parameters/consumeType")
public class ConsumeTypeController {
    @Resource
    ConsumeTypeService consumeTypeService;

    @RequiresPermissions({"member:customParameters:consumeType:list"})
    @RequestMapping("getConsumeTypeList")
    public Map<String, Object> getConsumeTypeList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        ConsumeType consumeType = new ConsumeType();
        consumeType.name = query;
        List<ConsumeType> data = consumeTypeService.getConsumeTypeList(consumeType, page, limit);
        ret.put("count", consumeTypeService.getConsumeTypeListCount(consumeType));
        ret.put("data", data);
        return ret;
    }

    @RequestMapping("getConsumeTypeNameList")
    public List<ConsumeType> getConsumeTypeList() {
        ConsumeType consumeType = new ConsumeType();
        return consumeTypeService.getConsumeTypeList(consumeType, 1, Integer.MAX_VALUE);
    }

    @RequiresPermissions({"member:customParameters:consumeType:add"})
    @RequestMapping("addConsumeType")
    public String addConsumeType(@RequestBody ConsumeType consumeType) {
        return consumeTypeService.addConsumeType(PlatUserUtils.getCurrentLoginPlatUser(), consumeType);
    }

    @RequiresPermissions({"member:customParameters:consumeType:delete"})
    @RequestMapping("deleteConsumeType")
    public String deleteConsumeType(@RequestParam String id) {
        ConsumeType consumeType = new ConsumeType();
        consumeType.consumeTypeId = Integer.parseInt(id);
        if (consumeTypeService.deleteConsumeType(PlatUserUtils.getCurrentLoginPlatUser(), consumeType))
            return "删除成功！";
        else
            return "删除失败！";
    }

    @RequiresPermissions({"member:customParameters:consumeType:edit"})
    @RequestMapping("editConsumeType")
    public ConsumeType editConsumeType(@RequestBody ConsumeType consumeType) {
        return consumeTypeService.editConsumeType(PlatUserUtils.getCurrentLoginPlatUser(), consumeType);
    }
}
