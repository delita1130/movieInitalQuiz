package com.novThirty.movieinitalquiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.novThirty.movieinitalquiz.database.GameDao;

public class QuizMainActivity extends AppCompatActivity {
    private String TAG = "QuizMainActivity";
    private GameDao gameDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

    }
}
