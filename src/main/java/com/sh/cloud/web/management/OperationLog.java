package com.sh.cloud.web.management;

import com.sft.member.bean.Log;
import com.sft.member.obtain.log.LogService;
import com.sh.cloud.entity.GetLogListRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresPermissions("member:management")
@RestController
@RequestMapping("service/management/log")
public class OperationLog {
    @Resource
    LogService logService;

    /**
     * 根据查询参数获取操作日志列表
     *
     * @param request 查询参数（起止日期、日志内容）
     * @return 查询到的操作日志列表
     */
    @RequiresPermissions("member:management")
    @RequestMapping("getLogList")
    public Map<String, Object> getLogList(@RequestBody GetLogListRequest request) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");
        List<Log> data = logService.getLogList(request.getStartTime(), request.getEndTime(), request.getContent(), request.getPage(), request.getLimit());
        ret.put("data", data);
        ret.put("count", logService.getLogListCount(request.getStartTime(), request.getEndTime(), request.getContent()));
        return ret;
    }

    /**
     * 获取某用户的操作日志列表
     *
     * @param userId 用户id
     * @return 查询到的操作日志列表
     */
    @RequestMapping("getUserLogList")
    public Map<String, Object> getUserLogList(@RequestParam String userId, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");
        List<Log> data = logService.getUserLogList(userId, page, limit);
        ret.put("data", data);
        ret.put("count", logService.getUserLogListCount(userId));
        return ret;
    }
}
