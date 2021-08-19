package com.sh.cloud.web.others;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    // ----------- Main controller ------------
    @RequestMapping("/main")
    public String main() {
        return "others/main";
    }
    // ----------------------------------------

    // ------------- Menu: Member -------------
    @RequestMapping("/memberArchives")
    public String memberArchives() {
        return "member/memberArchives";
    }
    // ----------------------------------------

    // ----------- Menu: Use card -------------
    @RequestMapping("/useCard")
    public String useCard() {
        return "memberUseCoupon/useCard";
    }

    @RequestMapping("/cancelConsume")
    public String cancelConsume() {
        return "memberUseCoupon/cancelConsume";
    }

    @RequestMapping("/pendingReview")
    public String pendingReview() {
        return "memberUseCoupon/pendingReview";
    }
    // ----------------------------------------

    // ------ Meun: Customize parameter -------
    @RequestMapping("/coupon")
    public String card() {
        return "parameters/coupon";
    }

    @RequestMapping("/consumeType")
    public String consumeType() {
        return "parameters/consumeType";
    }

    @RequestMapping("/consumeItem")
    public String consumeItem() {
        return "parameters/consumeProject";
    }

    @RequestMapping("/insuranceCompany")
    public String insuranceCompany() {
        return "parameters/insuranceCompany";
    }

    @RequestMapping("/insuranceType")
    public String insuranceType() {
        return "parameters/insuranceType";
    }

    @RequestMapping("/membershipLevel")
    public String membershipLevel() {
        return "parameters/membershipLevel";
    }
    // ----------------------------------------

    // ------------- Menu: Report center -------------
    // 用卡历史
    @RequestMapping("/useCardHistory")
    public String useCardHistory() {
        return "statistics/useCardHistory";
    }

    // 反结算历史
    @RequestMapping("/cancelConsumeHistory")
    public String cancelConsumeHistory() {
        return "statistics/cancelConsumeHistory";
    }

    // 卡券统计
    @RequestMapping("/couponStatistical")
    public String couponStatistical() {
        return "statistics/couponStatistical";
    }

    // 储值统计
    @RequestMapping("/balanceStatistical")
    public String depositStatistical() {
        return "statistics/balanceStatistical";
    }

//    // 群发统计
//    @RequestMapping("/massStatistical")
//    public String massStatistical() {
//        return "massStatistical";
//    }
//
//    // 抽奖统计
//    @RequestMapping("/raffleStatistical")
//    public String raffleStatistical() {
//        return "raffleStatistical";
//    }
    // ----------------------------------------

    // ------------- Menu: System management -------------
    @RequestMapping("/userManagement")
    public String userManagement() {
        return "others/userManagement";
    }
    // ----------------------------------------
}
