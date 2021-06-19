package com.sh.cloud.web;

import com.dubbo.user.service.VehicleService;
import com.sh.cloud.common.Result;
import com.sh.cloud.common.ResultConst;
import com.sh.cloud.utils.UtilValidate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by jxh on 2021/4/29.
 */
@RestController
@RequestMapping("demo")
@Api("demo")
@Log4j2
public class DemoController {

    @Resource
    private VehicleService vehicleService;

    @PostMapping("/test")
    public String test() {
        return "OK";
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Result get(@ApiParam(value = "id", name = "id", required = true) Long id) {
        String vehicleStr = vehicleService.getVehicle("");
        if (UtilValidate.isNotEmpty(vehicleStr)) {
            return new Result(ResultConst.SUCCESS.getResult(), ResultConst.SUCCESS.getMessage(), vehicleStr);
        } else {
            return new Result(ResultConst.FAIL.getResult(), ResultConst.FAIL.getMessage(), "");
        }
    }
}
