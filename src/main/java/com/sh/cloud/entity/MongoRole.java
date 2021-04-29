package com.sh.cloud.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Set;

@Data
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MongoRole {

    @Id
    private String _id;

    @ApiModelProperty(value = "角色标识")
    private String name;

    @ApiModelProperty(value = "模块名字")
    private String modulename;

    @ApiModelProperty(value = "角色描述")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String uid;


    @ApiModelProperty(value = "是否可用")
    private int status = 0;

    @ApiModelProperty(value = "角色标识")
    private String role;

    @ApiModelProperty(value = "权限集合")
    private Set<ObjectId> pset;

    @ApiModelProperty(value = "注册时间(时间戳)")
    private Long registertime = 0L;

    @Transient
    private String value;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

