package com.sh.cloud.web.statistics;

import com.sft.member.bean.Coupon;
import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import com.sft.member.bean.UserCoupon;
import com.sft.member.obtain.coupon.CouponService;
import com.sft.member.obtain.statistics.StatisticsService;
import com.sft.member.obtain.user.UserService;
import com.sh.cloud.entity.GetRequestPacket;
import com.sh.cloud.entity.ReturnStatisticalJson;
import com.sh.cloud.entity.UserCouponMetaInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/statistics/couponStatistical")
public class CouponStatisticalController {
    @Resource
    StatisticsService statisticsService; // 统计相关接口
    @Resource
    CouponService couponService; // 卡券相关接口
    @Resource
    UserService shUserService; // 用户相关接口

    /**
     * 获取卡券统计列表
     *
     * @param request 查询参数
     * @return 卡券统计列表
     * @author fqh
     */
    @RequiresPermissions("member:statistics:coupon")
    @RequestMapping("getCouponStatisticalList")
    public Map<String, Object> getCouponStatisticalList(@RequestBody GetRequestPacket request) {
        // 创建返回数据变量
        Map<String, Object> ret = new HashMap();
        ret.put("code", 0);
        ret.put("msg", "");

        // 源数据列表
        // 因为需要拿到的是 用户的 单条的信息，所以groupBy总为false
        List<CouponCheck> sourceDataList = statisticsService.getCouponStatics(request.getUser(), request.getCouponCheck(), request.getPage(), request.getLimit(), false);
        // 返回时的列表
        List<ReturnStatisticalJson> resJsonList = new ArrayList<>();

        // 又因为返回的是原始列表，只有数据id，所以需要逐条解析。
        // 创建返回时的数据包。
        User user = new User();
        Coupon coupon = new Coupon();
        CouponCheck couponCheck = new CouponCheck();

        int rechargeTimes, usageTimes, remainingTimes;
        for (CouponCheck data : sourceDataList) {
            // data里有userId，通过userId新建User对象，然后调用接口函数传入刚刚新建的User对象，来获取一个具备完整User信息的对象。
            user.userId = data.userId;
            user = shUserService.getUser(user);
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
            rechargeTimes = statisticsService.getStoreValueCountByCoupon(user, couponCheck, 0);
            remainingTimes = statisticsService.getStoreValueCountByCoupon(user, couponCheck, 1);
            usageTimes = rechargeTimes - remainingTimes;
            resJson.setRechargeTimes(rechargeTimes);
            resJson.setUsageTimes(usageTimes);
            resJson.setRemainingTimes(remainingTimes);

            resJsonList.add(resJson);
        }


        ret.put("data", resJsonList);
        ret.put("count", statisticsService.getCouponStaticsCount(request.getUser(), request.getCouponCheck(), request.getGroupBy()));

        return ret;
    }

    /**
     * 获取某用户拥有的卡券
     *
     * @param userId 用户Id
     * @return 用户拥有的卡券
     */
    @RequiresPermissions(value = {"member:archives:more"}, logical = Logical.OR)
    @RequestMapping("getUserCoupon")
    public Map<String, Object> getUserCoupon(@RequestParam String userId) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("msg", "");

        List<UserCouponMetaInfo> data = new ArrayList<>();
        List<UserCoupon> srcData = couponService.getUserCouponListByUser(userId);
        User tmpUser = new User();
        tmpUser.userId = userId;
        for (int i = 0; i < srcData.size(); i++) {
            // 无法设置父类writeMethod, 不能用copyProperties
            // BeanUtils.copyProperties(srcData.get(i), data.get(i));
            // UserCoupon tuc =  srcDatum; // 向下转型
            // data.add((UserCouponMetaInfo) tuc);
            data.add(new UserCouponMetaInfo());
            data.get(i).setUserCoupon(srcData.get(i));
            Coupon tmpCoupon = new Coupon();
            tmpCoupon.couponId = Integer.parseInt(data.get(i).getUserCoupon().couponId);
            data.get(i).getUserCoupon().coupon = couponService.getCoupon(tmpCoupon);
        }
        for (UserCouponMetaInfo info : data) {
            if (info.getUserCoupon().coupon.type == 0) {
                info.setBuyCount(couponService.getUserCouponCountByUser(userId, info.getUserCoupon().couponId, info.getUserCoupon().startEffectiveTime, info.getUserCoupon().endEffectiveTime, 0));
                info.setAvailableCount(couponService.getUserCouponCountByUser(userId, info.getUserCoupon().couponId, info.getUserCoupon().startEffectiveTime, info.getUserCoupon().endEffectiveTime, 1));
                info.setUsedCount(info.getBuyCount() - info.getAvailableCount());
            } else {
                User user = new User();
                user.userId = userId;
                CouponCheck couponCheck = new CouponCheck();
                couponCheck.couponId = info.getUserCoupon().couponId;
                info.setBuyCount(statisticsService.getStoreValueCountByMoney(user, couponCheck, 0));
                info.setAvailableCount(statisticsService.getStoreValueCountByMoney(user, couponCheck, 1));
                info.setUsedCount(info.getBuyCount() - info.getAvailableCount());
            }
        }
        ret.put("data", data);

        return ret;
    }
}
