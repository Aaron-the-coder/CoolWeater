package com.dale.coolweather.util;

import com.dale.coolweather.db.City;
import com.dale.coolweather.db.County;
import com.dale.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dale on 2017/8/8.
 * 用来解析网络请求返回的数据
 *http://www.guolin.tech/api/weather/?cityid=CN101190401&&key=fc286968c1c64dbf938b23cb4d873361
 */

public class Utility {

    /**
     * 解析省数据
     * @param
     */
    public static boolean handleProvinceResponse(String response){
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                Province province = new Province();
                province.setProvinceCode(id);
                province.setName(name);
                province.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 解析市数据
     * @param
     */
    public static boolean handleCityResponse(String response, int provinceId){
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                City city = new City();
                city.setCityCode(id);
                city.setName(name);
                city.setProvinceId(provinceId);
                city.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 解析县数据
     * @param
     */
    public static boolean handleCountyResponse(String response, int cityId){
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String weatherId = jsonObject.getString("weather_id");
                County county = new County();
                county.setCountyCode(id);
                county.setName(name);
                county.setWeather_id(weatherId);
                county.setCityId(cityId);
                county.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
