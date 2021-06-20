package com.sh.cloud.entity;

import com.sft.member.bean.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetUserListRequest {
    User user;

    int page;

    int limit;
}
