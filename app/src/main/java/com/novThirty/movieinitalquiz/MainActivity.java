package com.novThirty.movieinitalquiz;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.novThirty.movieinitalquiz.adapter.StageAdapter;
import com.novThirty.movieinitalquiz.config.GameStatus;
import com.novThirty.movieinitalquiz.database.GameDao;
import com.novThirty.movieinitalquiz.model.Movie;
import com.novThirty.movieinitalquiz.model.User;

import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private GridView gridView;
    private GameDao gameDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_stage);
        /*Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);*/

        gameDao = new GameDao(this);
        gameDao.dbConnectTest();

        loadStageList();
    }

    private void loadStageList() {
        // get User
        User getUser = gameDao.getUser();
        GameStatus.user = getUser;

        // print Stage
        List<Movie> movieList = gameDao.getStageList();

        StageAdapter adapter = new StageAdapter(this, movieList);

        gridView = findViewById(R.id.gridView);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "진행가능..");
            }
        });

    }

}