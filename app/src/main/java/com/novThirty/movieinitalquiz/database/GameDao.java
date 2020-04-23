package com.novThirty.movieinitalquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.novThirty.movieinitalquiz.config.GameStatus;
import com.novThirty.movieinitalquiz.model.Movie;
import com.novThirty.movieinitalquiz.model.User;

import java.util.ArrayList;
import java.util.List;

public class GameDao {
    private final String TAG = "GameDao";
    public Context context = null;

    // 데이터 베이스 갱신 방법 - view > tool window > device file explorer > data폴더 > data폴더 > databases 삭제 후 에뮬 제 실행
    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public static List<Movie> MovieList;

    public GameDao(Context context){
        this.context = context;
    }

    public void dbConnect(){
        createDatabase();

        MovieList = getMovieList();

        GameStatus.user = getUser();
    }

    private void createDatabase() {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // A-1
    public User getUser(){
        User user = null;

        if (database != null) {
            String sql = "select    user_code," +
                    "done_mov_num," +
                    "curr_mov_num," +
                    "point" +
                    " from      xb_user ";

            try {
                Cursor cursor = database.rawQuery(sql, null);
                String userCode = null;
                int doneMovNum = 0;
                int currMovNum = 0;
                int point = 0;

                while (cursor.moveToNext()) {
                    userCode = cursor.getString(0);
                    doneMovNum = cursor.getInt(1);
                    currMovNum = cursor.getInt(2);
                    point = cursor.getInt(3);
                }

                user = new User(userCode, doneMovNum, currMovNum, point);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return user;
    }


    // A-2
    // @return list : 스테이지들의 1단계 movNum을 넣었습니다.
    public List<Movie> getStageList(){
        List<Movie> list = null;

        if (database != null) {
            String sql = "  SELECT    ta.mov_num, " +
                    "               ta.mov_name, " +
                    "               ta.stage " +
                    "       FROM (" +
                    "           SELECT  mov_num, " +
                    "                   mov_name, " +
                    "                   stage " +
                    "           FROM    xb_movie " +
                    "           ORDER BY step ASC" +
                    "       ) ta " +
                    "       GROUP BY stage";

            try {

                Cursor cursor = database.rawQuery(sql, null);

                int movNum = 0;
                String movName = null;
                String stage = null;

                list = new ArrayList<Movie>();

                while (cursor.moveToNext()) {
                    movNum = cursor.getInt(0);
                    movName = cursor.getString(1);
                    stage = cursor.getString(2);

                    Movie movie = new Movie(movNum, movName, stage);
                    list.add(movie);
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return list;
    }

    // A-3
    // @param currMovNum : 가져와야 할 무비 번호를 넣어주세요.
    public Movie getMovie(int currMovNum){
        Movie movie = null;

        if (database != null) {
            String sql = "SELECT mov_num, mov_name, stage, step, mov_script, mov_actor, mov_img_path FROM xb_movie WHERE mov_num =" + currMovNum;

            try {
                Cursor cursor = database.rawQuery(sql, null);

                int movNum = 0;
                String movName = null;
                String stage = null;
                String step = null;
                String movScript = null;
                String movActor = null;
                String movImgPath = null;

                while (cursor.moveToNext()) {
                    movNum = cursor.getInt(0);
                    movName = cursor.getString(1);
                    stage = cursor.getString(2);
                    step = cursor.getString(3);
                    movScript = cursor.getString(4);
                    movActor = cursor.getString(5);
                    movImgPath = cursor.getString(6);
                }

                movie = new Movie(movNum, movName, step, stage, movScript, movActor, movImgPath);

            }catch (Exception e){
                Log.e(TAG, e.toString());
            }
        }

        return movie;
    }

    // A-4
    // @param point : 감소 또는 증가 된 결과 포인트를 넣어주세요
    public void updatePoint(int point){
        if (database != null) {
            try {
                ContentValues values = new ContentValues();
                values.put("point",  point);

                database.update("xb_user", values, "1=1", new String[] {});
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        GameStatus.user = getUser();
    }

    // A-5
    // @param doneMovNum : 완료 된 movNum
   public void updateDoneMovNum(int doneMovNum){
        if (database != null) {
            try {
                ContentValues values = new ContentValues();
                values.put("done_mov_num",  doneMovNum);

                database.update("xb_user", values, "1=1", new String[] {});
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        GameStatus.user = getUser();
    }


    // ------------------------------------ db Test -----------------------------------------------
    public void dbConnectTest(){
        createDatabase();

        MovieList = getMovieList();
        Log.e(TAG, MovieList.get(0).getMovActor());

        GameStatus.user = getUser();

        int x = GameStatus.user.getPoint();
        Log.i("test :: ", x + "");

        updatePoint(getUser().getPoint() + 10);

        GameStatus.user = getUser();
        x = GameStatus.user.getPoint();

        List<Movie> list = getStageList();

        for(Movie li : list){
            Log.i("번호 :: ", li.getMovNum() + "");
        }

        Movie movie = getMovie(1);

        String actor = movie.getMovActor();

        Log.i("test :: ", actor);
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
                list = new ArrayList<Movie>();

                Cursor cursor = database.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    int movNum = cursor.getInt(0);
                    String movName = cursor.getString(1);
                    String stage = cursor.getString(2);
                    String step = cursor.getString(3);
                    String movScript = cursor.getString(4);
                    String movActor = cursor.getString(5);
                    String movImgPath = cursor.getString(6);

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
            database.execSQL("insert into xb_movie( mov_name," +
                    "stage," +
                    "step," +
                    "mov_script," +
                    "mov_actor," +
                    "mov_img_path)" +
                    "values (movName, stage, step, movScript, movActoer, movImgPath)");
        }
    }

    public void insertUser(String userCode, int doneMovNum, int currMovNum, int point){
        if (database != null) {
            database.execSQL("insert into xb_user(" +
                    "user_code," +
                    "done_mov_num," +
                    "curr_mov_num," +
                    "point)" +
                    "values (userCode, doneMovNum, currMovNum, point)");
        }
    }

}
