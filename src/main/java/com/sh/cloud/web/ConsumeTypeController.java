package com.sh.cloud.web;

import com.sft.member.bean.ConsumeType;
import com.sft.member.obtain.consume.ConsumeTypeService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("service/consumeType")
public class ConsumeTypeController {
    @Resource
    ConsumeTypeService consumeTypeService;

//    @RequestMapping("getConsumeType")
//    @ResponseBody
//    public List<ConsumeType> getConsumeType() {
//        return consumeTypeService.getConsumeTypeList();
//    }

    @RequestMapping("addConsumeType")
    @ResponseBody
    public String addConsumeType(@RequestBody ConsumeType consumeType) {
        return consumeTypeService.addConsumeType(PlatUserUtils.getCurrentLoginPlatUser(),consumeType);
    }
}
