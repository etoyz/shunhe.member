package com.sh.cloud.web;

import com.sft.member.bean.Coupon;
import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import com.sft.member.obtain.coupon.CouponService;
import com.sft.member.obtain.statistics.StatisticsService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.entity.GetRequestPacket;
import com.sh.cloud.utils.ReturnStatisticalJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/balanceStatistical")
public class BalanceStatisticalController {
    @Autowired
    StatisticsService statisticsService; // 统计相关接口
    @Autowired
    CouponService couponService; // 卡券相关接口
    @Autowired
    UserService userService; // 用户相关接口



    // 获取整个列表
    @RequestMapping("getBalanceStatisticalList")
    public Map<String, Object> getBalanceStatisticalList(@RequestBody GetRequestPacket request) {
        // 创建返回数据变量
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");

        // 源数据列表
        // 因为储值统计需要拿到的是 用户的 单条的信息，所以groupBy总为false
        List<CouponCheck> sourceDataList = statisticsService.getStoreValueStatics(request.getUser(), request.getCouponCheck(), request.getPage(), request.getLimit(), false);;
        // 返回时的列表
        List<ReturnStatisticalJson> resJsonList = new ArrayList<>();

        // 又因为返回的是原始列表，只有数据id，所以需要逐条解析。
        // 创建返回时的数据包。
        User user = new User();
        Coupon coupon = new Coupon();
        CouponCheck couponCheck = new CouponCheck();

        int rechargeMoney, usageMoney, remainingMoney;
        for (CouponCheck data : sourceDataList) {
            // data里有userId，通过userId新建User对象，然后调用接口函数传入刚刚新建的User对象，来获取一个具备完整User信息的对象。
            user.userId = data.userId;
            user = userService.getUser(user);
            coupon.couponId = Integer.parseInt(data.couponId);
            coupon = couponService.getCoupon(coupon);
            couponCheck.couponId = data.couponId;

            // 添加到返回数据里
            ReturnStatisticalJson resJson = new ReturnStatisticalJson();
            resJson.setCustomername(user.customername);
            resJson.setMemberNumber(user.memberNumber);
            resJson.setVin(user.vehicle.vin);
            resJson.setPlatenumber(user.vehicle.platenumber);
            resJson.setCouponName(coupon.name);
            rechargeMoney = statisticsService.getStoreValueCountByMoney(user, couponCheck, 0);
            remainingMoney = statisticsService.getStoreValueCountByMoney(user, couponCheck, 1);
            usageMoney = rechargeMoney - remainingMoney;
            resJson.setRechargeMoney(rechargeMoney);
            resJson.setUsageMoney(usageMoney);
            resJson.setRemainingMoney(remainingMoney);

            resJsonList.add(resJson);
        }


        ret.put("data", resJsonList);
        ret.put("count", statisticsService.getStoreValueStaticsCount(request.getUser(), request.getCouponCheck(), request.getGroupBy()));

        return ret;
    }

    /*
    //接口没做卡券的检索条件，故注释。
    // 获取储值类型卡券名字列表
    @RequestMapping("getBalanceCouponNameList")
    public Map<String, Object> getBalanceCouponNameList(@RequestBody GetRequestPacket request) {
        // 创建返回数据变量
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");

        // 源数据列表
        List<Coupon> sourceDataList = couponService.getCouponNameList();
        // 返回时的列表
        List<Coupon> resList = new ArrayList<>();

        // 我们只要储值类型的卡券，即type为1，所以要进行过滤。
        for (Coupon coupon : sourceDataList) {
            if (coupon.type == 1) {
                resList.add(coupon);
            }
        }



        ret.put("data", resList);
        ret.put("count", resList.size());

        return ret;
    }
    */



}


/*
                ,{field: 'customername', title: '客户名称', width:150, sort: true}
                ,{field: 'memberNumber', title: '会员卡号', width:200, sort: true}
                ,{field: 'vin', title: '车架号', width:200, sort: true}
                ,{field: 'platenumber', title: '车牌号', width:150, sort: true}
                ,{field: 'couponName', title: '卡券', width: 80, sort: true}
                ,{field: 'rechargeMoney', title: '充值金额', width: 150, sort: true}
                ,{field: 'usageMoney', title: '使用金额', width: 150, sort: true}
                ,{field: 'remainingMoney', title: '剩余金额', width: 150, sort: true}
* */
