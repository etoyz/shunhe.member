package com.sh.cloud.web;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.PracticalProject;
import com.sft.member.obtain.consume.PracticalProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("service/practicalProject")
public class PracticalProjectController {
    @Resource
    PracticalProjectService service;

    @RequestMapping("getPracticalProjectListByConsumeProject")
    public List<PracticalProject> getPracticalProjectListByConsumeProject(@RequestParam String consumeProjectId) {
        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.consumeProjectId = Integer.parseInt(consumeProjectId);
        return service.getPracticalProjectListByConsumeProject(consumeProject);
    }
}
