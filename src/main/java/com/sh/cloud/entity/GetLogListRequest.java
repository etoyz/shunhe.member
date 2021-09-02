package com.sh.cloud.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLogListRequest {
    String startTime;

    String endTime;

    String content;

    int page;

    int limit;
}
