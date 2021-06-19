package com.sh.cloud.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by jxh on 2019/4/16.
 */
@Entity(name = "dict")
public class Dict {

    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Long id;    // 数据值
    @Column(name = "value", columnDefinition = "varchar(50) null")
    private String value;    // 数据值
    @Column(name = "label", columnDefinition = "varchar(50) null")
    private String label;    // 标签名
    @Column(name = "type", columnDefinition = "varchar(50) null")
    private String type;    // 类型
    @Column(name = "description", columnDefinition = "varchar(250) null")
    private String description;// 描述
    @Column(name = "sort")
    private Integer sort;    // 排序
    @Column(name = "parentId", columnDefinition = "varchar(64) null")
    private String parentId;//父Id

    public Dict() {
        super();
    }

    public Dict(Long id) {
        this();
        this.id = id;
    }

    public Dict(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute
    @Length(min = 1, max = 100)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute
    @Length(min = 1, max = 100)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Length(min = 1, max = 100)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute
    @Length(min = 0, max = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min = 1, max = 100)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return label;
    }
}
