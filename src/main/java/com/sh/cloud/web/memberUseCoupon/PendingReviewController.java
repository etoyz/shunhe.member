package com.sh.cloud.web.memberUseCoupon;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import com.sft.member.obtain.log.LogService;
import com.sft.member.obtain.pay.PayService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.entity.GetPendingReviewListRequest;
import com.sh.cloud.utils.LogUtils;
import com.sh.cloud.utils.PlatUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresPermissions("member:memberUseCoupon:review")
@RestController
@RequestMapping("service/useCoupon/review")
public class PendingReviewController {
    @Resource
    PayService payService;
    @Resource
    LogService logService;
    @Resource
    UserService shUserService;

    /**
     * 获取待审核列表
     * @param request 查询参数
     * @return 查询到的待审核列表
     */
    @RequestMapping("getPendingReviewList")
    public Map<String, Object> getPendingReviewList(@RequestBody GetPendingReviewListRequest request) {
        CouponCheck couponCheck = request.getCouponCheck();
        couponCheck.type = "0";
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");
        List<CouponCheck> data;
//        if (request.getGroupBy()) {
//            data = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), true);
//            for (CouponCheck c : data) {
//                Float totalM = 0f;
//                Integer cnt = 0;
//                List<CouponCheck> t = payService.getUnCheckRecord(request.getUser(), c, request.getPage(), request.getLimit(), false);
//                for (CouponCheck ct : t) {
//                    totalM += Float.parseFloat(ct.totalMoney);
//                    cnt += Integer.parseInt(ct.count);
//                }
//                c.totalMoney = String.valueOf(totalM);
//                c.count = String.valueOf(cnt);
//            }
//        } else {
            data = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), request.getGroupBy());
//        }
        ret.put("data", data);
        ret.put("count", payService.getUnCheckRecordCount(request.getUser(), couponCheck, request.getGroupBy()));

        return ret;
    }

    /**
     * 审核通过某消费单
     * @param groupId 消费单的groupId
     * @param userId 客户id
     * @return "成功！"|错误信息
     */
    @RequestMapping("checkCoupon")
    public String checkCoupon(@RequestParam String groupId, @RequestParam String userId) {
        String ret = payService.checkCoupon(PlatUserUtils.getCurrentLoginPlatUser(), groupId);
        if (ret.equals("")) {
            User user = new User();
            user.userId = userId;
            user = shUserService.getUser(user);
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("审核消费单 客户名称:" + user.customername
                            + "、会员卡号:" + user.memberNumber
                            + "、消费单:" + groupId
                    ));
            return "成功！";
        } else
            return ret;
    }

    /**
     * 驳回某消费单
     * @param groupId 消费单的groupId
     * @param userId 客户id
     * @return "成功！"|错误信息
     */
    @RequestMapping("cancelRecord")
    public String cancelRecord(@RequestParam String groupId, @RequestParam String userId) {
        String ret = payService.cancelRecord(PlatUserUtils.getCurrentLoginPlatUser(), groupId);
        if (ret == null) {
            User user = new User();
            user.userId = userId;
            user = shUserService.getUser(user);
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("驳回消费单 客户名称:" + user.customername
                            + "、会员卡号:" + user.memberNumber
                            + "、消费单:" + groupId));
            return "成功！";
        } else
            return ret;
    }
}
