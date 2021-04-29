package com.sh.cloud.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MongoPermission {

    @Id
    private String _id;

    @ApiModelProperty(value = "权限名称")
    private String name;
    @ApiModelProperty(value = "权限标识")
    private String permission;
    @ApiModelProperty(value = "链接地址")
    private String url;

    @ApiModelProperty(value = "权限描述")
    private String remark;

    @ApiModelProperty(value = "模块名字")
    private String modulename;

    @ApiModelProperty(value = "父id")
    private String pid;

    @ApiModelProperty(value = "用户id")
    private String uid;


    @ApiModelProperty(value = "是否可用")
    private int status = 0;


    @ApiModelProperty(value = "注册时间(时间戳)")
    private Long registertime = 0L;

    public String getpid() {
        return pid;
    }

    public void setpid(String pid) {
        this.pid = pid;
    }
}

