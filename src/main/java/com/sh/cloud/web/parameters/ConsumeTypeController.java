package com.sh.cloud.web.parameters;

import com.sft.member.bean.ConsumeType;
import com.sft.member.obtain.consume.ConsumeTypeService;
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
@RequestMapping("service/parameters/consumeType")
public class ConsumeTypeController {
    @Resource
    ConsumeTypeService consumeTypeService;
    @Resource
    LogService logService;

    /**
     * 根据关键字查询消费类型列表
     *
     * @param query 关键字（按名称查询）
     * @return 消费类型列表
     */
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

    /**
     * 获取全部消费类型的名称
     *
     * @return 全部消费类型的名称
     */
    @RequestMapping("getConsumeTypeNameList")
    public List<ConsumeType> getConsumeTypeList() {
        ConsumeType consumeType = new ConsumeType();
        return consumeTypeService.getConsumeTypeList(consumeType, 1, Integer.MAX_VALUE);
    }

    /**
     * 新增消费类型
     *
     * @param consumeType 消费类型信息
     * @return 是否新增成功
     */
    @RequiresPermissions({"member:customParameters:consumeType:add"})
    @RequestMapping("addConsumeType")
    public String addConsumeType(@RequestBody ConsumeType consumeType) {
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("新增消费类型 消费类型名:" + consumeType.name));
        return consumeTypeService.addConsumeType(PlatUserUtils.getCurrentLoginPlatUser(), consumeType);
    }

    /**
     * 删除某消费类型
     *
     * @param id 消费类型ID
     * @return "删除成功！"|"删除失败！"
     */
    @RequiresPermissions({"member:customParameters:consumeType:delete"})
    @RequestMapping("deleteConsumeType")
    public String deleteConsumeType(@RequestParam String id) {
        ConsumeType consumeType = new ConsumeType();
        consumeType.consumeTypeId = Integer.parseInt(id);
        if (consumeTypeService.deleteConsumeType(PlatUserUtils.getCurrentLoginPlatUser(), consumeType)) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("删除消费类型 消费类型ID:" + id));
            return "删除成功！";
        } else
            return "删除失败！";
    }

    /**
     * 编辑某消费类型
     *
     * @param consumeType 新的消费类型信息
     * @return 编辑后的新的消费类型信息
     */
    @RequiresPermissions({"member:customParameters:consumeType:edit"})
    @RequestMapping("editConsumeType")
    public ConsumeType editConsumeType(@RequestBody ConsumeType consumeType) {
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("编辑消费类型 消费类型ID:" + consumeType.consumeTypeId));
        return consumeTypeService.editConsumeType(PlatUserUtils.getCurrentLoginPlatUser(), consumeType);
    }
}
