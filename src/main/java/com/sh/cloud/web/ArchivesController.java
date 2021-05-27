package com.sh.cloud.web;

import com.sft.member.bean.Coupon;
import com.sft.member.bean.User;
import com.sft.member.obtain.coupon.CouponService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/Archives")
//@RequiresPermissions("member:Archives:view")
public class ArchivesController {
    @Resource
    UserService shUserService;
    @PostMapping(value = "addArchives")
    public String addUser(@RequestBody User user) {
        String ret = shUserService.addUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        if (ret == null || ret.equals(""))
            return "添加成功";
        else
            return ret;
    }
    @GetMapping("getArchivesList")
    public Map<String, Object> getArchivesList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        User c = new User();
        c.customername = query;
        List<User> data = shUserService.getUserList(c, page, limit);
        ret.put("count", shUserService.getUserList(c));
        ret.put("data", data);

        return ret;
    }
    @PostMapping("deleteArchives")
    public String deleteArchives(@RequestParam String userId) {
        User c = new User();
        c.userId = userId;
        if (shUserService.deleteUser(PlatUserUtils.getCurrentLoginPlatUser(), c).equals("true"))
            return "删除成功！";
        else
            return "删除失败！";
    }
    @PostMapping(value = "editArchives")
    public String editArchives(@RequestBody User user) {
     shUserService.editUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        return "修改成功！";
    }
    @PostMapping(value = "requireUser")
    public Map<String, Object> requireUser(@RequestParam String userId) {
        Map<String, Object> ret1 = new HashMap<>();
        User c = new User();
        c.userId = userId;
        User data = shUserService.getUser(c);
        ret1.put("data", data);
        return ret1;
    }
}

