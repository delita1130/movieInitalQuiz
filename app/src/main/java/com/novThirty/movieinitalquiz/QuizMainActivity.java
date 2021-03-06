package com.novThirty.movieinitalquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.novThirty.movieinitalquiz.config.GameStatus;
import com.novThirty.movieinitalquiz.database.GameDao;
import com.novThirty.movieinitalquiz.dialog.CorrectDialog;
import com.novThirty.movieinitalquiz.dialog.DefaultDialog;
import com.novThirty.movieinitalquiz.dialog.HintDialog;
import com.novThirty.movieinitalquiz.dialog.HintImageDialog;
import com.novThirty.movieinitalquiz.dialog.IncorrectDialog;
import com.novThirty.movieinitalquiz.model.Movie;
import com.novThirty.movieinitalquiz.model.User;

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
    private ImageButton rewardButton;
    private TextView pointText;

    private RewardedAd rewardedAd;
    private Activity activity;
    private boolean readyAd = false;
    HintDialog rewardDialog;
    final Integer rewardPoint  = new Integer(GameStatus.movRewardPoint);

    final Integer hintPoint1 = new Integer(GameStatus.movScriptHintPoint);
    final Integer hintPoint2 = new Integer(GameStatus.movActorHintPoint);
    final Integer hintPoint3 = new Integer(GameStatus.movImgHintPoint);
    final Integer hintPoint4 = new Integer(GameStatus.movNameHintPoint);
    private int rewardedAdCount = 0;
    IncorrectDialog loadingDialog;
    IncorrectDialog failDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        gameDao = new GameDao(this);
        gameDao.dbConnect();
        pointText = findViewById(R.id.pointText);

        Intent intent = getIntent();
        String clickStage = intent.getStringExtra("clickStage");
        String clickFirstMovNum = intent.getStringExtra("clickFirstMovNum");

        int doingStage = (int)Math.floor(GameStatus.user.getDoneMovNum() / GameStatus.numOfstepPerStage)+1;

        if(doingStage == Integer.parseInt(clickStage)){  // 진행중인문제
            setUp(GameStatus.user.getNextMovNum());
            GameStatus.user.setCurrMovNum(GameStatus.user.getNextMovNum());
        }else{  // 진행했던문제
            setUp(Integer.parseInt(clickFirstMovNum));
            GameStatus.user.setCurrMovNum(Integer.parseInt(clickFirstMovNum));

        }

        activity = this;
        readyAd = false;

        rewardButton = findViewById(R.id.imageButton2);

       // rewardedAd = createAndLoadRewardedAd();

        rewardButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {

                View.OnClickListener positiveListener;
                View.OnClickListener negativeListener;

                positiveListener = new View.OnClickListener() {
                    public void onClick(View v) {

                        if(rewardedAdCount==0) {

                            readyAd = true;

                            rewardedAdCount++;

                            rewardedAd = createAndLoadRewardedAd();

                            rewardDialog.dismiss();

                            loadingDialog = new IncorrectDialog(QuizMainActivity.this,"광고 로딩 중 입니다.");
                            loadingDialog.callFunction(answerEdit);

                        }else{
                            rewardDialog.dismiss();
                            loadingDialog = new IncorrectDialog(QuizMainActivity.this,"광고 로딩 중 입니다.");
                            loadingDialog.callFunction(answerEdit);


                        }
                    }
                };

                negativeListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        readyAd = false;
                        rewardDialog.dismiss();
                    }
                };

                rewardDialog = new HintDialog(QuizMainActivity.this, positiveListener, negativeListener);
                rewardDialog.callFunction("보상광고를 보시겠습니까?", rewardPoint.toString());

            }
        });
    }
    public RewardedAd createAndLoadRewardedAd() {

       final RewardedAd rerewardedAd = new RewardedAd(this,
                "ca-app-pub-2016735761572848/2448282052");

            RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
                @Override
                public void onRewardedAdLoaded() {
                    // Ad successfully loaded.

                    if (readyAd == true) {
                        Log.d("readyAd", "true");
                        Activity activityContext = activity;
                        RewardedAdCallback adCallback = new RewardedAdCallback() {
                            public void onRewardedAdOpened() {

                                // Ad opened.
                            }

                            public void onRewardedAdClosed() {
                                readyAd = false;
                                rewardedAdCount = 0;

                                rewardDialog.dismiss();

                                if( loadingDialog != null ) {
                                    loadingDialog.dismiss();
                                }

                                // Ad closed.
                            }

                            public void onUserEarnedReward(@NonNull RewardItem reward) {
                                // User earned reward.

                                gameDao.updatePoint(GameStatus.user.getPoint() + rewardPoint);

                                GameStatus.user = gameDao.getUser();
                                Integer point = new Integer(GameStatus.user.getPoint());
                                pointText.setText(point.toString());

                                hintButtonConfirm();

                            }

                            public void onRewardedAdFailedToShow(int errorCode) {
                                // Ad failed to display
                            }
                        };
                        rewardedAd.show(activityContext, adCallback);
                    } else {
                        Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                    }
                }

                @Override
                public void onRewardedAdFailedToLoad(int errorCode) {
                    Log.d("TAG", "The rewarded ad failed.");
                   // readyAd = true;
                  //  rewardedAd = createAndLoadRewardedAd();
                    rewardedAdCount = 0;

                    loadingDialog.dismiss();

                    failDialog = new IncorrectDialog(QuizMainActivity.this,"인터넷 연결을 확인해주세요");
                    failDialog.callFunction(answerEdit);
                }
            };

        rerewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rerewardedAd;
    }

    public void setUp(int nextMovNum){
        gameDao.updateCurrMovNum(nextMovNum);

        if(nextMovNum > 500){

            return;
        }

        movie = gameDao.getMovie(nextMovNum);

        GameStatus.user= gameDao.getUser();
        Integer point = new Integer(GameStatus.user.getPoint());
        pointText.setText(point.toString());

        backButton();
        printQuiz();
        confirmBtn();
        hintButton();
    }

    public void printQuiz(){
        // 단계 출력
        TextView stepText = findViewById(R.id.stepText);
        stepText.setText(movie.getStage() + "단계 / " + movie.getStep() + "번");
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

        final Integer answerPoint  = new Integer(GameStatus.movAnswerPoint);

        confirmBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 정답이면..
                if(answerEdit.getText().toString().trim().equals(movie.getMovName().trim())){
                    CorrectDialog correctDialog = new CorrectDialog(QuizMainActivity.this);

                    if(GameStatus.user.getCurrMovNum() == GameStatus.user.getDoneMovNum()+1) {  // 진행 중인 문제를 맞추면
                        GameStatus.user.setDoneMovNum(GameStatus.user.getDoneMovNum()+1);

                        gameDao.updateDoneMovNum(GameStatus.user.getDoneMovNum());    // 완료 된 번호 db업데이트

                        gameDao.updatePoint( GameStatus.user.getPoint() + answerPoint );    // 포인트 업데이트

                        nextQuiz(GameStatus.user.getNextMovNum()); // 다음 문제 출력

                        correctDialog.callFunction(answerPoint.toString()); // 포인트 다이얼로그 출력

                        GameStatus.user = gameDao.getUser();

                    }else { // 이전에 정답을 맞춘 문제이면..
                        GameStatus.user.setCurrMovNum(GameStatus.user.getCurrMovNum()+1);

                        nextQuiz(GameStatus.user.getCurrMovNum());

                        gameDao.updateCurrMovNum(GameStatus.user.getCurrMovNum());    // 완료 된 번호 db업데이트

                        correctDialog.callFunction("0");

                    }
                }else{  // 틀렸으면..
                    IncorrectDialog incorrectDialog = new IncorrectDialog(QuizMainActivity.this,"틀렸습니다~!");
                    incorrectDialog.callFunction(answerEdit);
                }

            }
        });
    }

    DefaultDialog lastQuizeDialog;

    public void nextQuiz(int nextMovNum){

        if(nextMovNum > 500){
            View.OnClickListener positiveListener;

            positiveListener = new View.OnClickListener() {
                public void onClick(View v) {
                    lastQuizeDialog.dismiss();
                    onBackPressed();
                }
            };

            lastQuizeDialog = new DefaultDialog(QuizMainActivity.this, positiveListener);
            lastQuizeDialog.callFunction();


        }else{
            setUp(nextMovNum);
        }
    }

    public void hintButtonConfirm(){
        if(hintText1.getText() == "" && GameStatus.user.getPoint() >= hintPoint1){
            hintBtn1.setBackground(this.getResources().getDrawable(R.drawable.btn_bg));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hintBtn1.setForeground(this.getResources().getDrawable(R.drawable.talkbox_icon_white_resize_point));
            }
        }else {
            hintBtn1.setBackground(this.getResources().getDrawable(R.drawable.disabled_btn_bg));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hintBtn1.setForeground(this.getResources().getDrawable(R.drawable.talkbox_icon_white_resize));
            }
        }
        if(hintText2.getText() == "" && GameStatus.user.getPoint() >= hintPoint2){
            hintBtn2.setBackground(this.getResources().getDrawable(R.drawable.btn_bg));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hintBtn2.setForeground(this.getResources().getDrawable(R.drawable.actor_icon_white_resize_point));
            }
        }else {
            hintBtn2.setBackground(this.getResources().getDrawable(R.drawable.disabled_btn_bg));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hintBtn2.setForeground(this.getResources().getDrawable(R.drawable.actor_icon_white_resize));
            }
        }
        if(hintText3.getText() == "" && GameStatus.user.getPoint() < hintPoint3){
            hintBtn3.setBackground(this.getResources().getDrawable(R.drawable.disabled_btn_bg));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hintBtn3.setForeground(this.getResources().getDrawable(R.drawable.picture_icon_white_resize_point));
            }
        }else {
            hintBtn3.setBackground(this.getResources().getDrawable(R.drawable.btn_bg));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hintBtn3.setForeground(this.getResources().getDrawable(R.drawable.picture_icon_white_resize_point));
            }
        }
        if(hintText4.getText() == "" && GameStatus.user.getPoint() >= hintPoint4){
            hintBtn4.setBackground(this.getResources().getDrawable(R.drawable.btn_bg));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hintBtn4.setForeground(this.getResources().getDrawable(R.drawable.movie_icon_white_resize_point));
            }
        }else {
            hintBtn4.setBackground(this.getResources().getDrawable(R.drawable.disabled_btn_bg));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hintBtn4.setForeground(this.getResources().getDrawable(R.drawable.movie_icon_white_resize));
            }
        }
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

        hintButtonConfirm();

        final boolean[] imageHintBool = {false};
        // 힌트1
        hintBtn1.setOnClickListener(new Button.OnClickListener() {
            HintDialog hintDialog;
            @Override
            public void onClick(View view) {

                View.OnClickListener positiveListener;
                View.OnClickListener negativeListener;

                positiveListener = new View.OnClickListener() {
                    public void onClick(View v) {

                        GameStatus.user = gameDao.getUser();
                        if(GameStatus.user.getPoint() >= hintPoint1){

                            gameDao.updatePoint( GameStatus.user.getPoint() - hintPoint1 );

                            GameStatus.user= gameDao.getUser();
                            Integer point = new Integer(GameStatus.user.getPoint());
                            pointText.setText(point.toString());

                            hintText1.setText( movie.getMovScript() );
                            hintButtonConfirm();

                            hintDialog.dismiss();

                        }else{
                            hintDialog.dismiss();
                        }

                    }
                };

                negativeListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        hintDialog.dismiss();
                    }
                };

                if( hintText1.getText() == "" && GameStatus.user.getPoint() >= hintPoint1) {
                    hintDialog = new HintDialog(QuizMainActivity.this, positiveListener, negativeListener);
                    hintDialog.callFunction("명대사힌트를 보시겠습니까?", hintPoint1.toString());
                }else{

                }
            }
        });

        // 힌트2
        hintBtn2.setOnClickListener(new Button.OnClickListener() {
            HintDialog hintDialog;
            @Override
            public void onClick(View view) {

                View.OnClickListener positiveListener;
                View.OnClickListener negativeListener;

                positiveListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        GameStatus.user = gameDao.getUser();
                        if(GameStatus.user.getPoint() >= hintPoint2){

                            gameDao.updatePoint( GameStatus.user.getPoint() - hintPoint2 );

                            GameStatus.user= gameDao.getUser();
                            Integer point = new Integer(GameStatus.user.getPoint());
                            pointText.setText(point.toString());

                            hintText2.setText(movie.getMovActor());
                            hintButtonConfirm();
                            hintDialog.dismiss();

                        }else{
                            hintDialog.dismiss();
                        }

                    }
                };

                negativeListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        hintDialog.dismiss();
                    }
                };
                if( hintText2.getText() == "" && GameStatus.user.getPoint() >= hintPoint2 ) {
                    hintDialog = new HintDialog(QuizMainActivity.this, positiveListener, negativeListener);
                    hintDialog.callFunction("출연자힌트를 보시겠습니까?", hintPoint2.toString());
                }
            }
        });

        // 힌트3
        hintBtn3.setOnClickListener(new Button.OnClickListener() {
            HintDialog hintDialog;
            @Override
            public void onClick(View view) {

                View.OnClickListener positiveListener;
                View.OnClickListener negativeListener;

                positiveListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        GameStatus.user = gameDao.getUser();
                        if(GameStatus.user.getPoint() >= hintPoint3){

                            gameDao.updatePoint( GameStatus.user.getPoint() - hintPoint3 );
                            imageHintBool[0] = true;
                            GameStatus.user= gameDao.getUser();
                            Integer point = new Integer(GameStatus.user.getPoint());
                            pointText.setText(point.toString());

                            hintDialog.dismiss();

                            HintImageDialog hintImageDialog = new HintImageDialog(QuizMainActivity.this);
                            hintImageDialog.callFunction(movie.getMovImgPath());
                            hintText3.setText(movie.getMovImgPath());
                            hintButtonConfirm();
                        }else{
                            hintDialog.dismiss();
                        }

                    }
                };

                negativeListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        hintDialog.dismiss();
                    }
                };
                if( hintText3.getText() == "" && GameStatus.user.getPoint() >= hintPoint3 ) {
                    hintDialog = new HintDialog(QuizMainActivity.this, positiveListener, negativeListener);
                    hintDialog.callFunction("사진힌트를 보시겠습니까?", hintPoint3.toString());

                }else if(imageHintBool[0]){
                    HintImageDialog hintImageDialog = new HintImageDialog(QuizMainActivity.this);
                    hintImageDialog.callFunction(movie.getMovImgPath());
                    hintText3.setText(movie.getMovImgPath());
                }
            }
        });

        // 힌트4
        hintBtn4.setOnClickListener(new Button.OnClickListener() {
            HintDialog hintDialog;
            @Override
            public void onClick(View view) {

                View.OnClickListener positiveListener;
                View.OnClickListener negativeListener;

                positiveListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        GameStatus.user = gameDao.getUser();
                        if(GameStatus.user.getPoint() >= hintPoint4){

                            gameDao.updatePoint( GameStatus.user.getPoint() - hintPoint4 );

                            GameStatus.user= gameDao.getUser();
                            Integer point = new Integer( GameStatus.user.getPoint());
                            pointText.setText(point.toString());

                            hintText4.setText(movie.getMovName());
                            hintButtonConfirm();
                            hintDialog.dismiss();

                        }else{
                            hintDialog.dismiss();
                        }

                    }
                };

                negativeListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        hintDialog.dismiss();
                    }
                };
                if( hintText4.getText() == "" && GameStatus.user.getPoint() >= hintPoint4) {
                    hintDialog = new HintDialog(QuizMainActivity.this, positiveListener, negativeListener);
                    hintDialog.callFunction("정답을 보시겠습니까?", hintPoint4.toString());
                }
            }
        });
    }

    // 뒤로가기 버튼
    public void backButton(){
        LinearLayout btn = findViewById(R.id.backButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // 초성 함수
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