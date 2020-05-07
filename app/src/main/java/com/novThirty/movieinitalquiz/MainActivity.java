package com.novThirty.movieinitalquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import androidx.appcompat.app.AppCompatActivity;

import com.novThirty.movieinitalquiz.adapter.StageAdapter;
import com.novThirty.movieinitalquiz.config.GameStatus;
import com.novThirty.movieinitalquiz.database.GameDao;
import com.novThirty.movieinitalquiz.model.Movie;
import com.novThirty.movieinitalquiz.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// 깃 테스트
public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private GridView gridView;
    private GameDao gameDao;
    private AdView mAdView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_stage);
        /*Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);*/

        gameDao = new GameDao(this);
        gameDao.dbConnect();

        loadStageList();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




    }

    private void loadStageList() {
        // get User
        User getUser = GameStatus.user;
        GameStatus.user = getUser;

        // print Stage//
        List<Movie> movieList = gameDao.getStageList();

        StageAdapter adapter = new StageAdapter(this, movieList);
        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
    }
}