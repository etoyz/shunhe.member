package com.sh.cloud.web;

import com.sft.member.bean.Brand;
import com.sft.member.bean.CarSeries;
import com.sft.member.bean.Member;
import com.sft.member.bean.User;
import com.sft.member.obtain.member.MemberService;
import com.sft.member.obtain.user.UserService;
import com.sft.member.obtain.vehicle.BrandService;
import com.sft.member.obtain.vehicle.CarSeriesService;
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
    BrandService brandService;
    @Resource
    CarSeriesService carSeriesService;
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
    public List<Brand> getBrandNameList() {
        List<Brand> ret = brandService.getBrandNameList();
        return ret;
    }
//    @PostMapping(value = "getCarSeriesNameList")
//    public List<CarSeries> getCarSeriesNameList() {
//        List<CarSeries> ret = carSeriesService.getCarSeriesNameList();
//        return ret;
//    }
//    车型接口没有resource
//    @PostMapping(value = "getCarTypeNameList")
//    public List<CarType> getMemberNameList() {
//        List<Member> ret = memberService.getMemberNameList();
//        return ret;
//    }
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

