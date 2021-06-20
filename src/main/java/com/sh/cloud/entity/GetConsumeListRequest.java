package com.sh.cloud.entity;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import com.sft.member.bean.Vehicle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetConsumeListRequest {
    User user;

    CouponCheck couponCheck;

    Vehicle vehicle;

    Boolean groupBy;

    int page;

    int limit;
}
