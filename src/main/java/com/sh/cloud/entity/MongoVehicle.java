package com.sh.cloud.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MongoVehicle {

    @Id
    private String _id;

    @ApiModelProperty(value = "发动机号")
    private String engine;
//    @ApiModelProperty(value = "设备类型")
//    private String vehicle_type;
//    @ApiModelProperty(value = "车型")
//    private String vehicle_brand;
//    @ApiModelProperty(value = "车系")
//    private String vehicle_style;
    @ApiModelProperty(value = "车牌号")
    private String platenumber;
//    @ApiModelProperty(value = "车辆颜色")
//    private String vehicle_colour;

    @ApiModelProperty(value = "疑问")
    private String name;
    @ApiModelProperty(value = "设备号")
    private Set devicenum;
    @ApiModelProperty(value = "车架号")
    private String vin;
    @ApiModelProperty(value = "公司代码")
    private String code;

    @ApiModelProperty(value = "创建人")
    private String uid;
    @ApiModelProperty(value = "是否可用")
    private int status = 0;
    @ApiModelProperty(value = "注册时间(时间戳)")
    private String registertime;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", engine='" + engine + '\'' +
                ", devicenum=" + devicenum +
                ", vin='" + vin + '\'' +
                ", code='" + code + '\'' +
                ", uid='" + uid + '\'' +
                ", status=" + status +
                '}';
    }
}

