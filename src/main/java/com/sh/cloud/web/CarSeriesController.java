package com.sh.cloud.web;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("service/carSeries")
@RequiresPermissions("member:carSeries:view")
public class CarSeriesController {

    @RequestMapping("deleteCarSeries")
    public String deleteCarSeries(@RequestParam("carSeriesName") String carSeriesName) {
        //此处获取接口原始数据，然后处理业务逻辑，然后返回结果

        return "Access Deny!";
    }

    @PostMapping("addCarSeries")
    public String addCarSeries(@RequestBody Map<String, Object> carSeriesMp) {
        // 通过接口将map数据插入后端

        return "Deny" + carSeriesMp.toString();
    }

    @PostMapping("modCarSeries")
    public String modCarSeries(@RequestBody Map<String, Object> carSeriesMp) {

        return "Deny" + carSeriesMp.toString();
    }
}
