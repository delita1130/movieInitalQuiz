package com.novThirty.movieinitalquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.novThirty.movieinitalquiz.config.GameStatus;
import com.novThirty.movieinitalquiz.database.GameDao;
import com.novThirty.movieinitalquiz.model.Movie;

public class QuizMainActivity extends AppCompatActivity {
    private String TAG = "QuizMainActivity";
    private GameDao gameDao;
    private Movie movie;
    private Button confirmBtn;
    private Button hintBtn1;
    private Button hintBtn2;
    private Button hintBtn3;
    private Button hintBtn4;
    private TextView answerEdit;
    private TextView hintText1;
    private TextView hintText2;
    private TextView hintText3;
    private TextView hintText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        gameDao = new GameDao(this);
        gameDao.dbConnect();
        Log.d("test :: ", GameStatus.user.toString());
        Log.d("test :: 완료 된 영화번호", GameStatus.user.getDoneMovNum() + "");
        //gameDao.updateDoneMovNum(0);
        setUp(GameStatus.user.getDoneMovNum()+1);
    }

    public void setUp(int nextMovNum){
        movie = gameDao.getMovie(nextMovNum);

        backButton();
        printQuiz();
        confirmBtn();
        hintButton();
    }

    public void printQuiz(){
        // 단계 출력
        TextView stepText = findViewById(R.id.stepText);
        stepText.setText(movie.getStep() + " / 5");

        // 초성 생성
        StringBuilder x = getInitialSound(movie.getMovName());

        // 초성 출력
        TextView quizText = findViewById(R.id.quizText);
        quizText.setText(x.toString());
    }

    // 정답확인 버튼
    public void confirmBtn(){
        confirmBtn = findViewById(R.id.confirmBtn);
        answerEdit = findViewById(R.id.answerEdit);
        answerEdit.setText("");

        confirmBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerEdit.getText().toString().trim().equals(movie.getMovName().trim())){
                    gameDao.updateDoneMovNum(GameStatus.user.getDoneMovNum()+1);
                    setUp(GameStatus.user.getDoneMovNum()+1);

                    Log.i("test :: ", "정답입니다.");
                }else{
                    Log.i("test :: ", "틀렸습니다.");
                }
            }
        }) ;
    }

    // 힌트 버튼들
    public void hintButton(){
        hintBtn1 = findViewById(R.id.hintBtn1);
        hintBtn2 = findViewById(R.id.hintBtn2);
        hintBtn3 = findViewById(R.id.hintBtn3);
        hintBtn4 = findViewById(R.id.hintBtn4);

        hintText1 = findViewById(R.id.hintText1);
        hintText2 = findViewById(R.id.hintText2);
        hintText3 = findViewById(R.id.hintText3);
        hintText4 = findViewById(R.id.hintText4);

        hintText1.setText("");
        hintText2.setText("");
        hintText3.setText("");
        hintText4.setText("");

        // 힌트1
        hintBtn1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintText1.setText(movie.getMovScript());
            }
        });

        // 힌트2
        hintBtn2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintText2.setText(movie.getMovActor());
            }
        });

        // 힌트3
        hintBtn3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintText3.setText(movie.getMovImgPath());
            }
        });

        // 힌트4
        hintBtn4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintText4.setText(movie.getMovName());
            }
        });
    }

    // 뒤로가기 버튼
    public void backButton(){
        ImageButton btn = findViewById(R.id.backButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // 초성 함수 - 한글을 제외한 모든 문자, 숫자등은 공백으로 처리..
    public StringBuilder getInitialSound(String text) {
        String[] chs = {
                "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
                "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
                "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
                "ㅋ", "ㅌ", "ㅍ", "ㅎ"
        };

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<text.length(); i++){
            char chName = text.charAt(i);
            if (chName >= 0xAC00) {
                int uniVal = chName - 0xAC00;
                int cho = ((uniVal - (uniVal % 28)) / 28) / 21;

                sb.append(chs[cho]);
            }else{
                sb.append(text.charAt(i));
            }
        }

        return sb;
    }
}