package com.sh.cloud.entity;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import lombok.Getter;
import lombok.Setter;

/*
 *
 * 现适用用于：CancelConsumeController、UseCardHistoryController、CancelConsumeHistoryController、CouponStatisticalController、BalanceStatisticalController。
 * 也可用于：PendingReviewController、ArchivesController等。
 *
 *
 * */

@Setter
@Getter
public class GetRequestPacket {
    User user;

    CouponCheck couponCheck;

    Boolean groupBy;

    int page;

    int limit;
}
