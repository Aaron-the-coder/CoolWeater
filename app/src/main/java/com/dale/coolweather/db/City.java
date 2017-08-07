package com.dale.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dale on 2017/8/7.
 */

public class City extends DataSupport {

    /**
     * id : 113
     * name : 南京
     */

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
