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
import java.util.Hashtable;
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
    public Hashtable<Integer, UserCoupon> getEnablePayCoupon(@RequestParam String userid, @RequestParam String consumeProjectId) {
        List<UserCoupon> srcList = payService.getEnablePayCoupon(userid, consumeProjectId);
        Hashtable<Integer, UserCoupon> dstTable = new Hashtable<>();
        for (UserCoupon uc : srcList) {
            // 卡券的类型  0:代金券  1:储值
            int type = uc.coupon.type;
            if (type == 1) {  // ID一样的"储值型代金券"合并
                if (!dstTable.containsKey(Integer.valueOf(uc.couponId)))
                    dstTable.put(Integer.valueOf(uc.couponId), uc);
                else {
                    dstTable.get(Integer.valueOf(uc.couponId)).money = String.valueOf(Float.parseFloat(dstTable.get(Integer.valueOf(uc.couponId)).money) + Float.parseFloat(uc.money));
                }
            } else if (type == 0) {
                if (!dstTable.containsKey(Integer.valueOf(uc.couponId))) {
                    uc.money = "1";
                    dstTable.put(Integer.valueOf(uc.couponId), uc);
                } else {
                    dstTable.get(Integer.valueOf(uc.couponId)).money = String.valueOf(Float.parseFloat(dstTable.get(Integer.valueOf(uc.couponId)).money) + 1);
                }
            }
        }
        return dstTable;
    }

    @RequestMapping("submitForReview")
    @ResponseBody
    public String submitForReview(@RequestBody List<CouponCheck> list) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String groupId = df.format(new Date());
        // 根据具体项目Id，获取到卡券Id and other process
        for (CouponCheck couponCheck : list) {
            PracticalProject project = new PracticalProject();
            project.practicalProjectId = Integer.parseInt(couponCheck.practicalProjectId);
            couponCheck.couponId = String.valueOf(practicalProjectService.getPracticalProject(project).couponId);
            couponCheck.groupId = groupId + couponCheck.groupId.substring(couponCheck.groupId.length() - 3);
            couponCheck.payStyle = couponCheck.payStyle.split(",")[0];
        }
        if (payService.addUnCheckRecord(PlatUserUtils.getCurrentLoginPlatUser(), list) == null)
            return "失败！";
        else
            return "成功！";
    }
}
