package com.novThirty.movieinitalquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.novThirty.movieinitalquiz.database.DataBaseHelper;
import com.novThirty.movieinitalquiz.database.GameDao;
import com.novThirty.movieinitalquiz.model.Movie;
import com.novThirty.movieinitalquiz.model.User;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        GameDao dao = new GameDao(this);
        dao.dbConnectTest();
    }

}