package com.sh.cloud.entity;

import com.sft.member.bean.CouponCheck;
import com.sft.member.bean.User;
import lombok.Getter;
import lombok.Setter;

/*
 *
 * 如果存在数据冗余问题，加条件，来特定生成。
 * 适用用于：好多好多。
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
