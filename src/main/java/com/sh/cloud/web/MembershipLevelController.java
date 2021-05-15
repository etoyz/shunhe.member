package com.sh.cloud.web;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/membershipLevel")
public class MembershipLevelController {
    @PostMapping("deleteCard")
    public String deleteCard(@RequestParam("cardName") String cardName) {
        //此处获取他们给的原始数据，然后处理业务逻辑，然后返回结果

        return "Access Deny!";
    }

    @PostMapping("addCard")
    public String addCard(@RequestBody Map<String, Object> cardMp) {
        // 通过他们的接口将map数据插入后端

        return "Deny" + cardMp.toString();
    }
    @PostMapping("modCard")
    public String modCard(@RequestBody Map<String, Object> cardMp) {

        return "Deny" + cardMp.toString();
    }
}
