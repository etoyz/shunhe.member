package com.sh.cloud.web;

import com.sft.member.bean.ConsumeType;
import com.sft.member.obtain.consume.ConsumeTypeService;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("service/consumeType")
public class ConsumeTypeController {
    @Resource
    ConsumeTypeService consumeTypeService;

    @RequestMapping("getConsumeTypeList")
    @RequiresPermissions({"member:consumeType:list"})
    @ResponseBody
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
    @ResponseBody
    public List<ConsumeType> getConsumeTypeList() {
        ConsumeType consumeType = new ConsumeType();
        return consumeTypeService.getConsumeTypeList(consumeType, 1, Integer.MAX_VALUE);
    }

    @RequestMapping("addConsumeType")
    @ResponseBody
    public String addConsumeType(@RequestBody ConsumeType consumeType) {
        return consumeTypeService.addConsumeType(PlatUserUtils.getCurrentLoginPlatUser(), consumeType);
    }

    @RequestMapping("deleteConsumeType")
    @ResponseBody
    public String deleteConsumeType(@RequestParam String id) {
        ConsumeType consumeType = new ConsumeType();
        consumeType.consumeTypeId = Integer.parseInt(id);
        if (consumeTypeService.deleteConsumeType(PlatUserUtils.getCurrentLoginPlatUser(), consumeType))
            return "删除成功！";
        else
            return "删除失败！";
    }

    @RequestMapping("editConsumeType")
    @ResponseBody
    public ConsumeType editConsumeType(@RequestBody ConsumeType consumeType) {
        return consumeTypeService.editConsumeType(PlatUserUtils.getCurrentLoginPlatUser(), consumeType);
    }

    @RequestMapping("getConsumeType")
    @ResponseBody
    public ConsumeType getConsumeType(@RequestParam int consumeTypeId) {
        ConsumeType consumeType = new ConsumeType();
        consumeType.consumeTypeId = consumeTypeId;
        return consumeTypeService.getConsumeType(consumeType);
    }
}
