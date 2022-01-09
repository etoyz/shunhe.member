package com.sh.cloud.web.parameters;

import com.sft.member.bean.Member;
import com.sft.member.obtain.log.LogService;
import com.sft.member.obtain.member.MemberService;
import com.sh.cloud.utils.LogUtils;
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
    @Resource
    LogService logService;

    /**
     * 删除会员信息
     * @param id 会员ID
     * @return "删除成功！"|"删除失败！"
     */
    @RequiresPermissions("member:customParameters:memberLevel:delete")
    @PostMapping("deleteMember")
    public String deleteCard(@RequestParam String id) {
        //此处获取他们给的原始数据，然后处理业务逻辑，然后返回结果
        Member c = new Member();
        c.id = id;
        String ret = memberService.deleteMember(PlatUserUtils.getCurrentLoginPlatUser(), c);
        if (ret == null || ret.equals("")) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("删除会员级别 会员级别ID:" + id));
            return "删除成功！";
        } else
            return "删除失败！";
    }

    /**
     * 根据查询参数获取会员级别列表
     * @param query 查询参数（按名称查询）
     * @return 会员级别列表
     */
    @RequiresPermissions("member:customParameters:memberLevel:list")
    @GetMapping("getMemberList")
    public Map<String, Object> getMemberList(@RequestParam String query, @RequestParam int page, @RequestParam int limit) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        Member c = new Member();
        c.name = query;
        List<Member> data = memberService.getMemberList(c, page, limit);
        ret.put("count", memberService.getMemberListCount(c));
        ret.put("data", data);

        return ret;
    }

    /**
     * 新增会员
     * @param member 新增的会员信息
     * @return "添加成功"|失败原因
     */
    @RequiresPermissions("member:customParameters:memberLevel:add")
    @PostMapping("addMember")
    public String addMember(@RequestBody Member member) {
        // 通过他们的接口将map数据插入后端
        String ret = memberService.addMember(PlatUserUtils.getCurrentLoginPlatUser(), member);
//        System.out.println("exists");
        if (ret == null || ret.equals("")) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("新增会员级别 会员级别名称:" + member.name));
            return "添加成功";
        } else
            return ret;
    }

    /**
     * 编辑会员信息
     * @param member 新的会员信息
     * @return 编辑后的新的会员信息
     */
    @RequiresPermissions("member:customParameters:memberLevel:edit")
    @PostMapping("editMember")
    public Member editMember(@RequestBody Member member) {
        Member m;
        m = memberService.editMember(PlatUserUtils.getCurrentLoginPlatUser(), member);
        logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                LogUtils.newLogInstance("编辑会员级别 会员级别ID:" + member.id));
        return m;
    }
}
