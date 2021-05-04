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
    // Brand
    @RequestMapping("/brand")
    public String brand() { return "brand"; }
    // Car series
    @RequestMapping("/carSeries")
    public String carSeries() {
        return "carSeries";
    }
    // Model
    @RequestMapping("/model")
    public String model() {
        return "model";
    }
    // Insurance company
    @RequestMapping("/insuranceCompany")
    public String insuranceCompany() {
        return "insuranceCompany";
    }
    // Type of insurance
    @RequestMapping("/typeOfInsurance")
    public String typeOfInsurance() {
        return "typeOfInsurance";
    }
    // Membership level
    @RequestMapping("/membershipLevel")
    public String membershipLevel() {
        return "membershipLevel";
    }
    // ----------------------------------------

    // ------------ For debugging ------------
    @RequestMapping("/testPage")
    public String testPage() {
        return "testPage";
    }
    // ----------------------------------------
}
