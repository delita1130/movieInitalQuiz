package com.novThirty.movieinitalquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Handler hd = new Handler();
        hd.postDelayed(new splashHandler(), 2000);

    }

    private class splashHandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), MainActivity.class));
            LoadingActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed(){
        // 스플래시 화면에서 뒤로가기 버튼 못하게 만듦..
    }
}
