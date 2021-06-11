package com.sh.cloud.entity;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.Coupon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CouponsAndConsumeProject {
    int consumeProjectId;

    List<Coupon> coupons;
}
