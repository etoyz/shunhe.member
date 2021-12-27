package com.sh.cloud.web.others;

import com.sft.member.bean.PlatUser;
import com.sft.member.obtain.log.LogService;
import com.sft.member.obtain.user.PlatUserService;
import com.sh.cloud.config.ShiroCasConfiguration;
import com.sh.cloud.utils.LogUtils;
import com.sh.cloud.utils.PlatUserUtils;
import com.sh.cloud.utils.UserUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * URL路由
 */
@Controller
@Log4j2
public class HomeController {
    @RequestMapping({"/", "/index"})
    public String index() {
        return "others/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map, Model model) throws Exception {
        return "redirect:" + ShiroCasConfiguration.loginUrl;
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "others/403";
    }

    @RequestMapping("/logout")
    public String logout() {
        UserUtils.getSubject().logout();
        return "redirect:" + ShiroCasConfiguration.logoutUrl;
    }

//    @RequestMapping("/index")
//    public String indexManagement(){
//        return "userweb/index";
//    }


}
