package com.dale.coolweather;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = LitePal.getDatabase();
    }
}
