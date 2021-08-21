package com.sh.cloud.web.member;

import carinfo.bean.CarInfo;
import carinfo.bean.CarSeries;
import carinfo.bean.CarSeriesGroup;
import carinfo.service.CarDataQuery;
import com.sft.member.bean.*;
import com.sft.member.obtain.member.MemberService;
import com.sft.member.obtain.pay.PayService;
import com.sft.member.obtain.user.PlatUserService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.entity.GetPendingReviewListRequest;
import com.sh.cloud.entity.GetUserListRequest;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/archives")
public class ArchivesController {
    @Resource
    UserService shUserService;
    @Resource
    MemberService memberService;
    @Resource
    CarDataQuery CarService;
    @Resource
    PayService payService;
    @Resource
    PlatUserService platUserService;

    @RequiresPermissions(value = {"member:archives:more"}, logical = Logical.OR)
    @RequestMapping("getUserByPost")
    public List<PlatUser> getUserByPost(@RequestParam String post) {
        PlatUser user = new PlatUser();
        user.post = post;
        return platUserService.getPlatUserListByPost(user);
    }

    @RequiresPermissions(value = {"member:archives:more"}, logical = Logical.OR)
    @RequestMapping("getUserCost")
    public UserCost getUserCost(@RequestParam String userid) {
        return payService.getUserCost(String.valueOf(userid));
    }

    @RequiresPermissions(value = {"member:archives:add"}, logical = Logical.OR)
    @PostMapping(value = "addArchives")
    public String addUser(@RequestBody User user) {
        String ret = shUserService.addUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        if (ret == null || ret.equals(""))
            return "成功！";
        else
            return ret;
    }

    @RequiresPermissions(value = {"member:archivesRoot"}, logical = Logical.OR)
    @GetMapping("getArchivesList")
    public Map<String, Object> getArchivesList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        User c = new User();
        c.customername = query;
        List<User> data = shUserService.getUserList(c, page, limit);
//        data.addAll(data);
        ret.put("count", shUserService.getUserList(c));
        ret.put("data", data);

        return ret;
    }

    @RequiresPermissions(value = {"member:memberUseCoupon:useCoupon"}, logical = Logical.OR)
    @RequestMapping("getUserList")
    public Map<String, Object> getUserList(@RequestBody GetUserListRequest request) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        List<User> data = shUserService.getUserList(request.getUser(), request.getPage(), request.getLimit());
        ret.put("count", shUserService.getUserList(request.getUser()));
        ret.put("data", data);

        return ret;
    }

    @RequiresPermissions(value = {"member:archives:more"}, logical = Logical.OR)
    @RequestMapping("getConsumptionHistories")
    public Map<String, Object> getConsumptionHistories(@RequestBody GetPendingReviewListRequest request) {
        CouponCheck couponCheck = request.getCouponCheck();
        couponCheck.type = "1";
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");
        ret.put("data", payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), request.getGroupBy()));
        ret.put("count", payService.getUnCheckRecordCount(request.getUser(), couponCheck, request.getGroupBy()));

        return ret;
    }

    @RequiresPermissions(value = {"member:archives:delete"}, logical = Logical.OR)
    @PostMapping("deleteArchives")
    public String deleteArchives(@RequestParam String userId) {
        User c = new User();
        c.userId = userId;
        String ret = shUserService.deleteUser(PlatUserUtils.getCurrentLoginPlatUser(), c);
        if (ret == null || ret.equals(""))
            return "删除成功！";
        else
            return ret;
    }

    @RequiresPermissions(value = {"member:archives:more"}, logical = Logical.OR)
    @PostMapping(value = "editArchives")
    public User editArchives(@RequestBody User user) {
        User c;
        c = shUserService.editUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        return c;
    }

//    TODO: 用到该URL的页面较多，暂不设权限控制
//    @RequiresPermissions(value = {"member:archives"}, logical = Logical.OR)
    @PostMapping(value = "getUserInfo")
    public User requireUser(@RequestParam String userId) {
        User c = new User();
        c.userId = userId;
        return shUserService.getUser(c);
    }

    @RequiresPermissions(value = {"member:archives:level"}, logical = Logical.OR)
    @PostMapping(value = "getMemberNameList")
    public List<Member> getMemberNameList() {
        List<Member> ret = memberService.getMemberNameList();
        return ret;
    }

    @PostMapping(value = "getBrandNameList")
    public List<CarSeriesGroup> getBrandNameList() {

        List<CarSeriesGroup> ret = CarService.getAllCarSeriesGroups("14");

        return ret;
    }

    @PostMapping(value = "getCarSeriesgroupNameList")
    public List<CarSeries> getCarSeriesgroupNameList(@RequestParam String BrandId) {
        List<carinfo.bean.CarSeries> ret = CarService.getAllCarSeries(BrandId);
        return ret;
    }

    @PostMapping(value = "getAllCarSeriesNameList")
    public List<CarInfo> getAllCarSeriesNameList(@RequestParam String GroupId) {
        List<carinfo.bean.CarInfo> ret = CarService.getAllCarInfo(GroupId);
        return ret;
    }

    @RequiresPermissions(value = {"member:archives:level"}, logical = Logical.OR)
    @PostMapping(value = "alertLevel")
    public String alertLevel(@RequestParam String userId, @RequestParam String id) {
        Member m = new Member();
        m.id = id;
        // m.level="11";
        User c = new User();
        c.userId = userId;
        c.member = m;
        String ret = shUserService.alertLevel(PlatUserUtils.getCurrentLoginPlatUser(), c);
        if (ret == null || ret.equals(""))
            return "添加成功";
        else
            return ret;
    }
}

