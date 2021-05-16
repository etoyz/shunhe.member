package com.sh.cloud.web;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.obtain.consume.ConsumeProjectService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("service/consumeProject")
public class ConsumeProjectController {
    @Resource
    ConsumeProjectService consumeProjectService;

    @RequestMapping("getConsumeProjectList")
    @ResponseBody
    public Map<String, Object> getProjectList(@RequestParam String query, @RequestParam String consumeType, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.name = query;
        consumeProject.consumeTypeName = consumeType;
        List<ConsumeProject> data = consumeProjectService.getConsumeProjectList(consumeProject, page, limit);
        ret.put("data", data);
        ret.put("count", consumeProjectService.getConsumeProjectListCount(consumeProject));
        return ret;
    }

    @PostMapping("addConsumeProject")
    @ResponseBody
    public String addConsumeProject(@RequestBody ConsumeProject consumeProject) {
        return consumeProjectService.addConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProject);
    }

    @RequestMapping("deleteConsumeProject")
    @ResponseBody
    public String deleteConsumeProject(@RequestParam String id) {
        ConsumeProject consumeProject = new ConsumeProject();
        consumeProject.consumeProjectId = Integer.parseInt(id);
        if (consumeProjectService.deleteConsumeProject(PlatUserUtils.getCurrentLoginPlatUser(), consumeProject))
            return "删除成功！";
        else
            return "删除失败！";
    }
}