package com.sh.cloud.web;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("service/brand")
@RequiresPermissions("member:brand:view")
public class BrandController {

    @RequestMapping("deleteBrand")
    public String deleteBrand(@RequestParam("brandID") String brandID) {
        //此处获取接口给的原始数据，然后处理业务逻辑，然后返回结果

        return "Access Deny!";
    }

    @PostMapping("addBrand")
    public String addBrand(@RequestBody Map<String, Object> brandMp) {
        // 通过接口将map数据插入后端

        return "Deny" + brandMp.toString();
    }

    @PostMapping("modBrand")
    public String modBrand(@RequestBody Map<String, Object> brandMp) {

        return "Deny" + brandMp.toString();
    }
}
