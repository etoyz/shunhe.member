package com.sh.cloud.web.statistics;

import com.sft.member.bean.Coupon;
import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import com.sft.member.obtain.coupon.CouponService;
import com.sft.member.obtain.statistics.StatisticsService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.entity.GetRequestPacket;
import com.sh.cloud.entity.ReturnHistoryJson;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresPermissions("member:statistics:rollback")
@RestController
@RequestMapping("service/statistics/cancelConsumeHistory")
public class CancelConsumeHistoryController {
    @Resource
    StatisticsService statisticsService; // 统计相关接口
    @Resource
    CouponService couponService; // 卡券相关接口
    @Resource
    UserService shUserService; // 用户相关接口

    /**
     * 获取反结算列表
     *
     * @param request 查询参数
     * @return 反结算列表
     * @author fqh
     */
    @RequestMapping("getCancelConsumeHistoryList")
    public Map<String, Object> getCancelConsumeHistoryList(@RequestBody GetRequestPacket request) {
        // 创建返回数据变量
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");

        // 源数据列表
        List<CouponCheck> sourceDataList = statisticsService.getRollBackStatics(request.getUser(), request.getCouponCheck(), request.getPage(), request.getLimit(), false);
        // 返回时的列表
        List<ReturnHistoryJson> resJsonList = new ArrayList<>();

        // 又因为返回的是原始列表，只有数据id，所以需要逐条解析。
        // 创建返回时的数据包。
        User user = new User();
        Coupon coupon = new Coupon();

        for (CouponCheck data : sourceDataList) {
            // data里有userId，通过userId新建User对象，然后调用接口函数传入刚刚新建的User对象，来获取一个具备完整User信息的对象。
            user.userId = data.userId;
            user = shUserService.getUser(user);

            // 添加到返回数据里
            ReturnHistoryJson resJson = new ReturnHistoryJson();
            resJson.setGroupId(data.groupId);
            resJson.setTime(data.time);
            resJson.setCustomername(user.customername);
            resJson.setMemberNumber(user.memberNumber);
            resJson.setVin(user.vehicle.vin);
            resJson.setPlatenumber(user.vehicle.platenumber);
            resJson.setConsumeTypeName(data.consumeTypeName);
            resJson.setConsumeProjectName(data.consumeProjectName);
            resJson.setPracticalProjectName(data.practicalProjectName);
            // 给的是支付ID，ID大于0代表卡券ID，通过卡券ID查出名字来就OK啦。
            coupon.couponId = Integer.parseInt(data.payStyle);
            coupon = couponService.getCoupon(coupon);
            resJson.setPayStyleName(coupon.name);
            // 占位-工时费
            // 占位-材料费
            resJson.setTotalMoney(data.totalMoney);
            // 占位-工单号
            // 占位-行驶里程
            resJson.setCount(data.count);
            // 占位-发票号
            // 占位-开票金额
            resJson.setCostPrice(coupon.costPrice);
            // 占位-项目备注


            resJsonList.add(resJson);
        }


        ret.put("data", resJsonList);
        ret.put("count", statisticsService.getRollBackStaticsCount(request.getUser(), request.getCouponCheck(), request.getGroupBy()));
        return ret;
    }
}
