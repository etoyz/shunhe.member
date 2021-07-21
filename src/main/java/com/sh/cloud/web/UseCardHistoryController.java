package com.sh.cloud.web;

import com.sft.member.bean.Coupon;
import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import com.sft.member.obtain.coupon.CouponService;
import com.sft.member.obtain.pay.PayService;
import com.sft.member.obtain.statistics.StatisticsService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.entity.GetRequestPacket;
import com.sh.cloud.utils.ReturnHistoryJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/useCardHistory")
public class UseCardHistoryController {
    @Autowired
    StatisticsService statisticsService; // 统计相关接口
    @Autowired
    CouponService couponService; // 卡券相关接口
    @Autowired
    UserService userService; // 用户相关接口
    @Autowired
    PayService payService; // 账单相关接口


    // 获取整个列表
    @RequestMapping("getUseCardHistoryList")
    public Map<String, Object> getUseCardHistoryList(@RequestBody GetRequestPacket request) {
        // 创建返回数据变量
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");

        // 源数据列表
        // 因为需要拿到的是 用户的 单条的信息，所以groupBy总为false
        List<CouponCheck> sourceDataList = statisticsService.getCheckRecordStatics(request.getUser(), request.getCouponCheck(), request.getPage(), request.getLimit(), false);;
        // 返回时的列表
        List<ReturnHistoryJson> resJsonList = new ArrayList<>();

        // 又因为返回的是原始列表，只有数据id，所以需要逐条解析。
        // 创建返回时的数据包。
        User user = new User();
        Coupon coupon = new Coupon();

        for (CouponCheck data : sourceDataList) {
            // data里有userId，通过userId新建User对象，然后调用接口函数传入刚刚新建的User对象，来获取一个具备完整User信息的对象。
            user.userId = data.userId;
            user = userService.getUser(user);

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
        ret.put("count", statisticsService.getCheckRecordStaticsCount(request.getUser(), request.getCouponCheck(), request.getGroupBy()));
        return ret;
    }

    // 获取原单列表
    @RequestMapping("getShowBillList")
    public Map<String, Object> getShowBillList(@RequestBody GetRequestPacket request) {
        CouponCheck couponCheck = request.getCouponCheck();
        couponCheck.type = "1";
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");
        List<CouponCheck> data;
        if (request.getGroupBy()) {
            data = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), true);
            for (CouponCheck c : data) {
                couponCheck.groupId = c.groupId;
                Float totalM = 0f;
                Integer cnt = 0;
                List<CouponCheck> t = payService.getUnCheckRecord(request.getUser(), couponCheck, request.getPage(), request.getLimit(), false);
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


}
