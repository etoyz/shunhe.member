package com.sh.cloud.web;
import com.sft.member.bean.Member;
import com.sft.member.bean.PlatUser;
import com.sft.member.bean.User;
import com.sft.member.obtain.member.MemberService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/membershipLevel")
public class MembershipLevelController {
    @Resource
    MemberService memberService;
    @PostMapping("deleteMember")
    public String deleteCard(@RequestParam String id) {
        //此处获取他们给的原始数据，然后处理业务逻辑，然后返回结果
        Member c=new Member();
        c.id=id;
        if(memberService.deleteMember(PlatUserUtils.getCurrentLoginPlatUser(),c).equals("true"))
        return "修改成功";
        else
            return "修改失败";
    }

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
    @PostMapping("addMember")
    public String addMember(@RequestBody Member member) {
        // 通过他们的接口将map数据插入后端
        memberService.addMember(PlatUserUtils.getCurrentLoginPlatUser(),member);
//        if (ret == null || ret.equals(""))
//            return "添加成功";
//        else
            return "添加成功";
    }
    @PostMapping("editMember")
    public String editMember(@RequestBody Member member) {
        memberService.editMember(PlatUserUtils.getCurrentLoginPlatUser(), member);
        return "修改成功！";
    }
}
