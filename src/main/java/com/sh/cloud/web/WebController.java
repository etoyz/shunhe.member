package com.sh.cloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @RequestMapping("/testPage")
    public String testPage() {
        return "testPage";
    }

    @RequestMapping("/card")
    public String card() {
        return "card";
    }

    @RequestMapping("/useCard")
    public String useCard() {
        return "useCard";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/memberArchives")
    public String memberArchives() {
        return "memberArchives";
    }

    @RequestMapping("/memberArchives1")
    public String memberArchives1() {
        return "memberArchives1";
    }
}
