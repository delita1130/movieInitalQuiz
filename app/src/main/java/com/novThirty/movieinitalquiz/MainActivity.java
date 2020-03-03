package com.novThirty.movieinitalquiz;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.novThirty.movieinitalquiz.adapter.StageAdapter;
import com.novThirty.movieinitalquiz.database.GameDao;
import com.novThirty.movieinitalquiz.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        List<Movie> movieList = gameDao.getMovieList();

        StageAdapter adapter = new StageAdapter(this, movieList);

        gridView = findViewById(R.id.gridView);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

}