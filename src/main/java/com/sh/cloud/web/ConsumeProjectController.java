package com.sh.cloud.web;

import com.sft.member.obtain.consume.ConsumeProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("service/consumeProject")
public class ConsumeProjectController {
    @Resource
    ConsumeProjectService consumeProjectService;

    @PostMapping("getProjectList")
    @ResponseBody
    public String getProjectList() {
        return "..";
    }
}
