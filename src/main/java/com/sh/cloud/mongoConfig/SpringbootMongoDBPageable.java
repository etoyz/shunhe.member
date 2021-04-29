package com.sh.cloud.mongoConfig;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by jxh on 2019/3/1.
 */
public class SpringbootMongoDBPageable implements Serializable,Pageable{
    /**
     * @Fields: serialVersionUID
     * @Todo: TODO
     */
    private static final long serialVersionUID = 1L;

    MongoDBPageModel page;

    public MongoDBPageModel getPage() {
        return page;
    }

    public void setPage(MongoDBPageModel page) {
        this.page = page;
    }

    @Override
    public Pageable first() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getOffset() {
        // TODO Auto-generated method stub
        return (page.getPagenumber() - 1) * page.getPagesize();
    }

    @Override
    public int getPageNumber() {
        // TODO Auto-generated method stub
        return page.getPagenumber();
    }

    @Override
    public int getPageSize() {
        // TODO Auto-generated method stub
        return page.getPagesize();
    }


    @Override
    public boolean hasPrevious() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Pageable next() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Sort getSort() {
        // TODO Auto-generated method stub
        return page.getSort();
    }
}
