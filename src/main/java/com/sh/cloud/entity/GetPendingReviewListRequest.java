package com.sh.cloud.entity;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPendingReviewListRequest {
    User user;

    CouponCheck couponCheck;

    Boolean groupBy;

    int page;

    int limit;
}
