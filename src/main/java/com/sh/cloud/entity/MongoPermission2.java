package com.sh.cloud.entity;


public class MongoPermission2 {

    public String _id;
    public String name;
    public String permission;
    public String url;
    public String remark;
    public String modulename;
    public String pid;
    public String uid;
    public int status = 0;
    public Long registertime = 0L;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

