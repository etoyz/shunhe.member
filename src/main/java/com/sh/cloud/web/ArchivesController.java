package com.sh.cloud.web;

import carinfo.bean.CarBrand;
import carinfo.bean.CarInfo;
import carinfo.bean.CarSeries;
import carinfo.bean.CarSeriesGroup;
import carinfo.service.CarDataQuery;
import com.sft.member.bean.Member;
import com.sft.member.bean.User;
import com.sft.member.obtain.member.MemberService;
import com.sft.member.obtain.user.UserService;
import com.sft.member.obtain.vehicle.BrandService;
import com.sft.member.obtain.vehicle.CarSeriesService;
import com.sh.cloud.entity.GetUserListRequest;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/Archives")
public class ArchivesController {
    @Resource
    UserService shUserService;
    @Resource
    MemberService memberService;
    @Resource
    CarDataQuery CarService;
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
//        data.addAll(data);
        ret.put("count", shUserService.getUserList(c));
        ret.put("data", data);

        return ret;
    }

    @RequestMapping("getUserList")
    @ResponseBody
    public Map<String, Object> getUserList(@RequestBody GetUserListRequest request) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        List<User> data = shUserService.getUserList(request.getUser(), request.getPage(), request.getLimit());
        ret.put("count", shUserService.getUserList(request.getUser()));
        ret.put("data", data);

        return ret;
    }

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

    @PostMapping(value = "editArchives")
    public User editArchives(@RequestBody User user) {
        User c;
       c = shUserService.editUser(PlatUserUtils.getCurrentLoginPlatUser(), user);
        return  c;
    }

    @PostMapping(value = "getUserInfo")
    public User requireUser(@RequestParam String userId) {
        User c = new User();
        c.userId = userId;
        return shUserService.getUser(c);
    }

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

