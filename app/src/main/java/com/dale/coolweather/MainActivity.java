package com.dale.coolweather;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "hello world!");
        db = LitePal.getDatabase();
    }
}
