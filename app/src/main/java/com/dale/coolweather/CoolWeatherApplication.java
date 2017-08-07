package com.dale.coolweather;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by dale on 2017/8/7.
 */

public class CoolWeatherApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mContext == null){
            mContext = getApplicationContext();
        }
        LitePal.initialize(mContext);
    }

    public static Context getContext(){
        return mContext;
    }
}