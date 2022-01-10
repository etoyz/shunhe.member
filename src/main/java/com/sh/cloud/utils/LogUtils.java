package com.sh.cloud.utils;

import com.sft.member.bean.Log;

public class LogUtils {
    public static Log newLogInstance(String content) {
        Log log = new Log();
        log.content = content;
        log.createId = PlatUserUtils.getCurrentLoginPlatUser().platUserId;
        log.time = TimeUtils.getCurrentFormatTime();
        return log;
    }
}
