package com.sh.cloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    // ----------- Main controller ------------
    @RequestMapping("/main")
    public String main() {
        return "main";
    }
    // ----------------------------------------

    // ------------- Menu: Member -------------
    @RequestMapping("/memberArchives")
    public String memberArchives() {
        return "memberArchives";
    }
    // ----------------------------------------

    // ----------- Menu: Use card -------------
    @RequestMapping("/useCard")
    public String useCard() {
        return "useCard";
    }

    @RequestMapping("/cancelConsume")
    public String cancelConsume() {
        return "cancelConsume";
    }

    @RequestMapping("/pendingReview")
    public String pendingReview() {
        return "pendingReview";
    }
    // ----------------------------------------

    // ------ Meun: Customize parameter -------
    @RequestMapping("/card")
    public String card() {
        return "card";
    }

    @RequestMapping("/consumeType")
    public String consumeType() {
        return "consumeType";
    }

    @RequestMapping("/consumeItem")
    public String consumeItem() {
        return "consumeItem";
    }
    // ----------------------------------------

    // ------------ For debugging ------------
    @RequestMapping("/testPage")
    public String testPage() {
        return "testPage";
    }
    // ----------------------------------------
}
