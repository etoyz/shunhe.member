package com.sh.cloud.mongoConfig;

import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by jxh on 2019/3/1.
 */
public class MongoDBPageModel implements Serializable {
    /**
     * @Fields: serialVersionUID
     * @Todo: TODO
     */
    private static final long serialVersionUID = 1L;
    // 当前页
    private Integer pagenumber = 1;
    // 当前页面条数
    private Integer pagesize = 10;
    // 排序条件
    private Sort sort;
    public Integer getPagenumber() {
        return pagenumber;
    }
    public void setPagenumber(Integer pagenumber) {
        this.pagenumber = pagenumber;
    }
    public Integer getPagesize() {
        return pagesize;
    }
    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }
    public Sort getSort() {
        return sort;
    }
    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
