package com.sh.cloud.web;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.PracticalProject;
import com.sft.member.obtain.consume.PracticalProjectService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("service/practicalProject")
public class PracticalProjectController {
    @Resource
    PracticalProjectService practicalProjectService;

    @RequestMapping("getPracticalProjectListByConsumeProject")
    public Map<String, Object> getPracticalProjectListByConsumeProject(@RequestParam String consumeProjectId) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.consumeProjectId = Integer.parseInt(consumeProjectId);
        ret.put("data", practicalProjectService.getPracticalProjectListByConsumeProject(consumeProject));

        return ret;
    }

    @RequestMapping("addPracticalProject")
    public String addPracticalProject(@RequestBody PracticalProject project) {
        return practicalProjectService.addPracticalProject(PlatUserUtils.getCurrentLoginPlatUser(), project);
    }

    @RequestMapping("deletePracticalProject")
    public String deletePracticalProject(@RequestBody PracticalProject project) {
        if (practicalProjectService.deletePracticalProject(PlatUserUtils.getCurrentLoginPlatUser(), project))
            return "成功！";
        else
            return "失败！";

    }
}
