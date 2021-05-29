package com.sh.cloud.entity;

import com.sft.member.bean.ConsumeProject;
import com.sft.member.bean.Coupon;
import com.sft.member.bean.PracticalProject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ConsumeProjectMetaInfo {
    ConsumeProject baseInfo;

    List<PracticalProject> practicalItems;

    List<Coupon> relateCoupons;
}
