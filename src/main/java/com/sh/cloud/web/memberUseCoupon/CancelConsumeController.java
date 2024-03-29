package com.sh.cloud.web.memberUseCoupon;

import com.sft.member.bean.CouponCheck;
import com.sft.member.obtain.log.LogService;
import com.sft.member.obtain.pay.PayService;
import com.sh.cloud.entity.GetRequestPacket;
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

@RequiresPermissions("member:memberUseCoupon:cancelConsume")
@RestController
@RequestMapping("service/useCoupon/cancelConsume")
public class CancelConsumeController {
    @Resource
    PayService payService;
    @Resource
    LogService logService;

    /**
     * 获取反结算列表
     *
     * @param request 查询参数
     * @return 查询到的反结算列表
     */
    @RequestMapping("getConsumeList")
    public Map<String, Object> getConsumeList(@RequestBody GetRequestPacket request) {
        CouponCheck couponCheck = request.getCouponCheck();
        couponCheck.type = "1";
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");
        List<CouponCheck> data;
        if (request.getGroupBy()) {
            data = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), true);
            for (CouponCheck c : data) {
                Float totalM = 0f;
                Integer cnt = 0;
                List<CouponCheck> t = payService.getUnCheckRecord(request.getUser(), c, request.getPage(), request.getLimit(), false);
                for (CouponCheck ct : t) {
                    totalM += Float.parseFloat(ct.totalMoney);
                    cnt += Integer.parseInt(ct.count);
                }
                c.totalMoney = String.valueOf(totalM);
                c.count = String.valueOf(cnt);
            }
        } else {
            data = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), false);
        }
        ret.put("data", data);
        ret.put("count", payService.getUnCheckRecordCount(request.getUser(), couponCheck, request.getGroupBy()));

        return ret;
    }

    /**
     * 反结算某消费单
     *
     * @param id 消费单id
     * @return "成功！"|错误信息
     */
    @RequestMapping("rollBack")
    public String rollBack(@RequestParam String id) {
        String ret = payService.rollBack(PlatUserUtils.getCurrentLoginPlatUser(), id);
        if (ret.equals("")) {
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("反结算账单 账单ID:" + id));
            return "成功！";
        } else
            return ret;
    }
}
