package com.sh.cloud.web;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.PracticalProject;
import com.sft.member.bean.UserCoupon;
import com.sft.member.obtain.consume.PracticalProjectService;
import com.sft.member.obtain.pay.PayService;
import com.sh.cloud.utils.PlatUserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("service/useCard")
public class UseCard {
    @Resource
    PayService payService;
    @Resource
    PracticalProjectService practicalProjectService;

    @RequestMapping("getEnablePayCoupon")
    @ResponseBody
    public List<UserCoupon> getEnablePayCoupon(@RequestParam String userid, @RequestParam String consumeProjectId) {
        return payService.getEnablePayCoupon(userid, consumeProjectId);
    }

    @RequestMapping("submitForReview")
    @ResponseBody
    public String submitForReview(@RequestBody List<CouponCheck> list) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String groupId = df.format(new Date());
        // 根据具体项目Id，获取到卡券Id
        for (CouponCheck couponCheck : list) {
            PracticalProject project = new PracticalProject();
            project.practicalProjectId = Integer.parseInt(couponCheck.practicalProjectId);
            couponCheck.couponId = String.valueOf(practicalProjectService.getPracticalProject(project).couponId);
            couponCheck.groupId = groupId + couponCheck.groupId.substring(couponCheck.groupId.length() - 3);
            System.out.println(couponCheck.groupId);
        }
        if (payService.addUnCheckRecord(PlatUserUtils.getCurrentLoginPlatUser(), list) == null)
            return "失败！";
        else
            return "成功！";
    }
}
