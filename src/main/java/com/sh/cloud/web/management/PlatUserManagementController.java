package com.sh.cloud.web.management;

import com.sft.member.bean.PlatUser;
import com.sft.member.obtain.log.LogService;
import com.sft.member.obtain.user.PlatUserService;
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
@RequestMapping("service/platUserManage")
public class PlatUserManagementController {
    @Resource
    PlatUserService platUserService;
    @Resource
    LogService logService;

    @RequiresPermissions("member:management")
    @RequestMapping("getUserList")
    public Map<String, Object> getUserList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        PlatUser platUser = new PlatUser();
        platUser.name = query;
        platUser.platUserId = PlatUserUtils.getCurrentLoginPlatUser().platUserId;
        List<PlatUser> data = platUserService.getPlatUserList(platUser, page, limit);
        ret.put("count", platUserService.getPlatUserListCount(platUser));
        ret.put("data", data);

        return ret;
    }

    @RequiresPermissions("member:management")
    @RequestMapping("addUser")
    public String addUser(@RequestBody PlatUser user) {
        String ret = platUserService.addUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        if (ret == null) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("新增用户 用户名:" + user.name));
            return "成功！";
        } else
            return ret;
    }

    @RequiresPermissions("member:management")
    @RequestMapping("editUser")
    public String editUser(@RequestBody PlatUser user) {
        platUserService.editPlatUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("编辑用户 用户ID:" + user.platUserId));
        return "修改成功！";
    }

    @RequiresPermissions("member:management")
    @RequestMapping("resetPassword")
    public String resetPassword(@RequestParam String platUserId, @RequestParam String passwd) {
        PlatUser user = new PlatUser();
        user.platUserId = platUserId;
        String ret = platUserService.resetPassword(PlatUserUtils.getCurrentLoginPlatUser(), user, passwd);
        if (ret == null) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("重置用户密码 用户ID:" + user.platUserId));
            return "成功";
        } else
            return ret;
    }

    @RequestMapping("getUserInfo")
    public PlatUser getUserInfo(@RequestParam String platUserId) {
        PlatUser user = new PlatUser();
        user.platUserId = platUserId;
        return platUserService.getPlatUser(user);
    }

    @RequestMapping("getCurrentLoginUser")
    public PlatUser getCurrentLoginUser() {
        return platUserService.getPlatUser(PlatUserUtils.getCurrentLoginPlatUser());
    }
}
