package com.sh.cloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("service/card")
public class CardController {

    @PostMapping("deleteCard")
    @ResponseBody
    public String deleteCard(@RequestParam("cardName") String cardName){
        //此处获取他们给的原始数据，然后处理业务逻辑，然后返回结果

        return "Deny" + cardName;
    }

}
