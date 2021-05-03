package com.sh.cloud.web;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service/card")
public class CardController {

    @PostMapping("deleteCard")
    public String deleteCard(@RequestParam("cardName") String cardName) {
        //此处获取他们给的原始数据，然后处理业务逻辑，然后返回结果

        return "Access Deny!";
    }

    @PostMapping("addCard")
    public String addCard(@RequestBody Map<String, Object> cardMp) {
        // 通过他们的接口将map数据插入后端

        return "Deny" + cardMp.get("cardName");
    }

    @PostMapping("getCardTypes")
    public Map<String, Object> getCardTypes() {
        //获取他们给的卡券类型
        Map<String, Object> res = new HashMap<>();
        res.put("status", "ok");

        List<String> li = new ArrayList<>();
        li.add("类型1");
        li.add("类型2");
        li.add("类型666");
        res.put("types", li);
        return res;
    }
}
