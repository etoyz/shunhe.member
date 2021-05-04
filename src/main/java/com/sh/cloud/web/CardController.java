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

        return "Deny" + cardMp.toString();
    }

    @PostMapping("getCardTypes")
    public Map<String, Object> getCardTypes() throws InterruptedException {
        //获取他们给的卡券类型
        Map<String, Object> res = new HashMap<>();
        res.put("status", "ok");

        List<String> li = new ArrayList<>();
        li.add("类型1");
        li.add("类型2");
        li.add("类型666");
        res.put("types", li);
//        Thread.sleep(900);
        return res;
    }

    @PostMapping("modCard")
    public String modCard(@RequestBody Map<String, Object> cardMp) {

        return "Deny" + cardMp.toString();
    }

    @PostMapping("getCardInfo")
    public Map<String, Object> getCardInfo(@RequestParam String cardName) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "ok");
        Map<String, String> info = new HashMap<>();
        info.put("cardName", cardName);

        info.put("cardType", "类型666");

        res.put("info", info);
        return res;
    }

    @PostMapping("relateConsumeItem")
    public String relateConsumeItem(@RequestBody Map<String, Object> items) {
        String card = (String) items.get("selectedCard");
        List itemsList = (List) items.get("selectedItems");

        return items.toString();
    }
}
