package com.sh.cloud.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Set;

@Data
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MongoUser {

    @Id
    private String _id;

    @ApiModelProperty(value = "登录用户")
    private String loginname;

    @ApiModelProperty(value = "昵称")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "盐值")
    private String salt;

    @ApiModelProperty(value = "公司代码")
    private String companycode;

    @ApiModelProperty(value = "激活码")
    private String validatecode;

    @ApiModelProperty(value = "父id")
    private String pid;

    @ApiModelProperty(value = "性别：1男2女0保密")
    private int gender = 0;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "图片地址")
    private String imgurl = "";

    @ApiModelProperty(value = "地址")
    private String address = "";

    @ApiModelProperty(value = "注册时间()")
    private String registertime;

    @ApiModelProperty(value = "角色集合")
    private Set<String> roleset;
    @Transient
    private Set<String> rolesetS;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "_id='" + _id + '\'' +
                ", loginname='" + loginname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pid='" + pid + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", registertime='" + registertime + '\'' +
                ", roleset=" + roleset +
                "}";
    }
}

