package com.sh.cloud.utils;

import com.sft.member.bean.PlatUser;

public class PlatUserUtils {
    public static PlatUser getCurrentLoginPlatUser() {
        PlatUser platUser = new PlatUser();
        platUser.platUserId = UserUtils.getUser().get_id();
        return platUser;
    }
}
