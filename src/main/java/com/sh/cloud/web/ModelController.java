package com.sh.cloud.web;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("service/model")
public class ModelController {

    @RequestMapping("deleteModel")
    public String deleteModel(@RequestParam("modelName") String modelName) {
        //此处获取接口原始数据，然后处理业务逻辑，然后返回结果

        return "Access Deny!";
    }

    @PostMapping("addModel")
    public String addModel(@RequestBody Map<String, Object> modelMp) {
        // 通过接口将map数据插入后端

        return "Deny" + modelMp.toString();
    }

    @PostMapping("modModel")
    public String modModel(@RequestBody Map<String, Object> modelMp) {

        return "Deny" + modelMp.toString();
    }
}
