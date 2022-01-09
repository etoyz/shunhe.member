package com.sh.cloud.web.parameters;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.PracticalProject;
import com.sft.member.obtain.consume.PracticalProjectService;
import com.sft.member.obtain.log.LogService;
import com.sh.cloud.utils.LogUtils;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/practicalProject")
public class PracticalProjectController {
    @Resource
    PracticalProjectService practicalProjectService;
    @Resource
    LogService logService;

    /**
     * 根据某消费项目获取具体项目列表
     * @param consumeProjectId 某消费项目ID
     * @return 具体项目列表
     */
    @RequestMapping("getPracticalProjectListByConsumeProject")
    public Map<String, Object> getPracticalProjectListByConsumeProject(@RequestParam String consumeProjectId) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");
        if (!consumeProjectId.equals("null")) {
            ConsumeProject consumeProject = new ConsumeProject();
            consumeProject.consumeProjectId = Integer.parseInt(consumeProjectId);
            ret.put("data", practicalProjectService.getPracticalProjectListByConsumeProject(consumeProject));
        } else {
            List<PracticalProject> emptyList = new ArrayList();
            ret.put("data", emptyList);
        }
        return ret;
    }

    /**
     * 新增具体项目
     * @param project 具体项目信息
     * @return "成功！"|失败原因
     */
    @RequestMapping("addPracticalProject")
    public String addPracticalProject(@RequestBody PracticalProject project) {
        String ret = practicalProjectService.addPracticalProject(PlatUserUtils.getCurrentLoginPlatUser(), project);
        if (ret == null) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("新增具体项目 具体项目名称:" + project.name + "消费项目ID:" + project.consumeProjectId));
            return "成功！";
        } else
            return ret;
    }

    /**
     * 编辑具体项目
     * @param project 新的具体项目信息
     * @return "修改成功！"
     */
    @RequestMapping("editPracticalProject")
    public String editPracticalProject(@RequestBody PracticalProject project) {
        practicalProjectService.editPracticalProject(PlatUserUtils.getCurrentLoginPlatUser(), project);
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("编辑具体项目 具体项目ID:" + project.practicalProjectId));
        return "修改成功！";
    }

    /**
     * 删除某具体项目
     * @param project 具体项目信息（只设置ID）
     * @return "成功！"|"失败！"
     */
    @RequestMapping("deletePracticalProject")
    public String deletePracticalProject(@RequestBody PracticalProject project) {
        if (practicalProjectService.deletePracticalProject(PlatUserUtils.getCurrentLoginPlatUser(), project)) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("删除具体项目 具体项目ID:" + project.practicalProjectId));
            return "成功！";
        } else
            return "失败！";

    }

    /**
     * 获取某具体项目的具体信息
     * @param practicalProjectId 具体项目ID
     * @return 该具体项目的具体信息
     */
    @RequestMapping("getPracticalProject")
    public PracticalProject getPracticalProject(@RequestParam int practicalProjectId) {
        PracticalProject project = new PracticalProject();
        project.practicalProjectId = practicalProjectId;
        return practicalProjectService.getPracticalProject(project);
    }
}
