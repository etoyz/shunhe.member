package com.sh.cloud.web.memberUseCoupon;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import com.sft.member.bean.UserCoupon;
import com.sft.member.obtain.log.LogService;
import com.sft.member.obtain.pay.PayService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.utils.LogUtils;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@RestController
@RequestMapping("service/useCoupon/useCoupon")
public class UseCardController {
    @Resource
    PayService payService;
    @Resource
    LogService logService;
    @Resource
    UserService shUserService;

    /**
     * 获取某客户在某消费项目下可用的卡券列表
     *
     * @param userid           客户id
     * @param consumeProjectId 消费项目id
     * @return 可用的卡券列表
     */
    @RequestMapping("getEnablePayCoupon")
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

    /**
     * 提交审核
     *
     * @param list 消费单列表
     * @return "失败！"|"成功！"
     */
    @RequiresPermissions("member:memberUseCoupon:useCoupon")
    @RequestMapping("submitForReview")
    public String submitForReview(@RequestBody List<CouponCheck> list) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String groupId = df.format(new Date());
        for (CouponCheck couponCheck : list) {
            couponCheck.groupId = groupId + couponCheck.groupId.substring(couponCheck.groupId.length() - 3);
            couponCheck.payStyle = couponCheck.payStyle.split(",")[0];
        }
        if (payService.addUnCheckRecord(PlatUserUtils.getCurrentLoginPlatUser(), list) == null)
            return "失败！";
        else {
            User user = new User();
            user.userId = list.get(0).userId;
            user = shUserService.getUser(user);
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("创建消费单 客户名称:" + user.customername
                            + "、会员卡号:" + user.memberNumber
                            + "、消费单:" + list.get(0).groupId
                    ));
            return "成功！";
        }
    }
}
