package com.novThirty.movieinitalquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.novThirty.movieinitalquiz.database.DataBaseHelper;
import com.novThirty.movieinitalquiz.model.Movie;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    // 데이터 베이스 갱신 방법 - view > tool window > device file explorer > data폴더 > data폴더 > databases 삭제 후 에뮬 제 실행
    DataBaseHelper dbHelper;
    SQLiteDatabase database;
    String TAG = "MainActivity";
    List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "시작");
        setContentView(R.layout.activity_main);
        createDatabase();

        // insertMovie("영화1", "1", );
        movieList = getMovieList();

        Log.e(TAG, movieList.get(0).getMovActor());
    }


    // database 관련 함수를 다른 class 로 이동 계획 중..
    private void createDatabase() {
        dbHelper = new DataBaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    public int getCountStage(){
        return 0;
    }

    public List<Movie> getMovieList(){
        List<Movie> list = null;

        if (database != null) {
            String sql = "select     mov_num, " +
                                    "mov_name, " +
                                    "stage, " +
                                    "step, " +
                                    "mov_script, " +
                                    "mov_actor, " +
                                    "mov_img_path" +
                        " from      xb_movie ";

            try {
                Cursor cursor = database.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    int movNum = cursor.getInt(0);
                    String movName = cursor.getString(1);
                    String stage = cursor.getString(2);
                    String step = cursor.getString(3);
                    String movScript = cursor.getString(4);
                    String movActor = cursor.getString(5);
                    String movImgPath = cursor.getString(6);

                    list = new ArrayList<Movie>();
                    list.add(new Movie(movNum, movName, stage, step, movScript, movActor, movImgPath));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return list;
    }

    public void insertMovie(String movName, String stage, String step, String movScript, String movActor, String movImgPath) {
        if (database != null) {
            database.execSQL("insert into xb_movie(" +
                                "mov_name," +
                                "stage," +
                                "step," +
                                "mov_script," +
                                "mov_actor," +
                                "mov_img_path)" +
                            "values (movName, stage, step, movScript, movActoer, movImgPath)");
        }
    }
}