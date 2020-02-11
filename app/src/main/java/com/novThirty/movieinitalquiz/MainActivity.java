package com.novThirty.movieinitalquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    DataBaseHelper dbHelper;
    SQLiteDatabase database;
    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "시작");
        setContentView(R.layout.activity_main);
        createDatabase();
        insertRecord();
    }

    private void createDatabase() {
        dbHelper = new DataBaseHelper(this);

        dbHelper = new DataBaseHelper(this);
        database = dbHelper.getWritableDatabase();


    }


    public void insertRecord() {
        if (database != null) {
            database.execSQL("insert into xb_movie(" +
                    "mov_name," +
                    "stage," +
                    "step," +
                    "mov_script," +
                    "mov_actor," +
                    "mov_img_path"
                    + ") values ('영화 제목', '1단계', '1', '안녕하세요', '홍길동, 외 1명', 'aaa')");
        }
    }
}