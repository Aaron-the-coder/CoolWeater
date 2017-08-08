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
    private int cityCode;
    private int provinceId;//所属省的id

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

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", provinceId=" + provinceId +
                '}';
    }
}
