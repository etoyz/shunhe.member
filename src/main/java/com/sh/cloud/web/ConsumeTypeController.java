package com.sh.cloud.web;

import com.sft.member.bean.ConsumeType;
import com.sft.member.obtain.consume.ConsumeTypeService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("getConsumeType")
    @ResponseBody
    public List<ConsumeType> getConsumeType() {
        return consumeTypeService.getConsumeTypeList();
    }

    @RequestMapping("getConsumeTypeList")
    @ResponseBody
    public Map<String, Object> getConsumeTypeList(){
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        List<ConsumeType> data = consumeTypeService.getConsumeTypeList();
//        ret.put("count", consumeTypeService.get);
        ret.put("data", data);
        return ret;
    }

    @RequestMapping("addConsumeType")
    @ResponseBody
    public String addConsumeType(@RequestBody ConsumeType consumeType) {
        return consumeTypeService.addConsumeType(PlatUserUtils.getCurrentLoginPlatUser(),consumeType);
    }
}
