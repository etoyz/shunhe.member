package com.sh.cloud.web.parameters;

import com.sft.member.bean.Member;
import com.sft.member.obtain.member.MemberService;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/parameters/membershipLevel")
public class MembershipLevelController {
    @Resource
    MemberService memberService;

    @RequiresPermissions("member:customParameters:memberLevel:delete")
    @PostMapping("deleteMember")
    public String deleteCard(@RequestParam String id) {
        //此处获取他们给的原始数据，然后处理业务逻辑，然后返回结果
        Member c = new Member();
        c.id = id;
        String ret=memberService.deleteMember(PlatUserUtils.getCurrentLoginPlatUser(), c);
        if (ret == null || ret.equals(""))
            return "修改成功";
        else
            return "修改失败";
    }

    @RequiresPermissions("member:customParameters:memberLevel:list")
    @GetMapping("getMemberList")
    public Map<String, Object> getMemberList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret3 = new HashMap<>();
        ret3.put("code", 0);
        ret3.put("msg", "");

        Member c = new Member();
        c.name = query;
        List<Member> data = memberService.getMemberList(c, page, limit);
        ret3.put("count", memberService.getMemberListCount(c));
        ret3.put("data", data);

        return ret3;
    }

    @RequiresPermissions("member:customParameters:memberLevel:add")
    @PostMapping("addMember")
    public String addMember(@RequestBody Member member) {
        // 通过他们的接口将map数据插入后端
        String ret = memberService.addMember(PlatUserUtils.getCurrentLoginPlatUser(), member);
//        System.out.println("exists");
        if (ret == null || ret.equals(""))
            return "添加成功";
        else
            return ret;
    }

    @RequiresPermissions("member:customParameters:memberLevel:edit")
    @PostMapping("editMember")
    public Member editMember(@RequestBody Member member) {
        Member m;
        m = memberService.editMember(PlatUserUtils.getCurrentLoginPlatUser(), member);
        return m;
    }
}
