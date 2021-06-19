package com.sh.cloud.web;

import com.sh.cloud.config.ShiroCasConfiguration;
import com.sh.cloud.utils.UserUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@Log4j2
public class HomeController {
    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map, Model model) throws Exception {
        System.out.println("HomeController.login()");
        System.out.println("redirect:" + ShiroCasConfiguration.loginUrl);
        return "redirect:" + ShiroCasConfiguration.loginUrl;
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "403";
    }

    @RequestMapping("/logout")
    public String logout() {
        UserUtils.getSubject().logout();
        return "redirect:" + ShiroCasConfiguration.logoutUrl;
    }

    @RequestMapping("/vehicleManagement")
    public String vehicleManagement() {
        return "userweb/vehicleManagement";
    }
//    @RequestMapping("/index")
//    public String indexManagement(){
//        return "userweb/index";
//    }


}
