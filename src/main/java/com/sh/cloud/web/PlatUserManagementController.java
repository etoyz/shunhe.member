package com.sh.cloud.web;

import com.sft.member.bean.PlatUser;
import com.sft.member.obtain.user.PlatUserService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("service/platUserManage")
public class PlatUserManagementController {

    @Resource
    PlatUserService platUserService;

    @RequestMapping("getUserList")
    @ResponseBody
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

    @RequestMapping("addUser")
    public String addUser(@RequestBody PlatUser user) {
        return platUserService.addUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
    }

    @RequestMapping("resetPassword")
    @ResponseBody
    public String resetPassword(@RequestBody PlatUser platUser) {
        String ret = platUserService.resetPassword(PlatUserUtils.getCurrentLoginPlatUser(), platUser, "111111");
        if (ret == null)
            return "";
        else
            return ret;
    }
}