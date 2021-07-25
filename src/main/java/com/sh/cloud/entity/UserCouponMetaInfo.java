package com.sh.cloud.entity;

import com.sft.member.bean.UserCoupon;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCouponMetaInfo {
    UserCoupon userCoupon;

    public int buyCount;

    public int usedCount;

    public int availableCount;
}
