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
}
