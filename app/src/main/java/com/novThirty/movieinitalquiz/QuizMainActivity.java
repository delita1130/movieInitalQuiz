package com.novThirty.movieinitalquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    private User user;

    private RewardedAd rewardedAd;
    private Activity activity;
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

        int openStage = (int)Math.floor(GameStatus.user.getDoneMovNum() / GameStatus.numOfstepPerStage);

        /*if(openStage >= Integer.parseInt(clickStage)){
            setUp(GameStatus.user.getNextMovNum());
        }else{*/
            setUp(Integer.parseInt(clickFirstMovNum));
        /*}*/

        //gameDao.updateDoneMovNum(0);
        setUp(GameStatus.user.getDoneMovNum()+1);

        activity = this;

        rewardButton = findViewById(R.id.imageButton2);

        rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                rewardedAd = createAndLoadRewardedAd();
            }
        };
        rewardedAd.loadAd(adRequest, adLoadCallback);

        rewardButton.setOnClickListener(new Button.OnClickListener() {
            HintDialog rewardDialog;
            final Integer rewardPoint  = new Integer(GameStatus.movRewardPoint);
            @Override
            public void onClick(View view) {

                View.OnClickListener positiveListener;
                View.OnClickListener negativeListener;

                positiveListener = new View.OnClickListener() {
                    public void onClick(View v) {

                        if (rewardedAd.isLoaded()) {
                            Activity activityContext = activity ;
                            RewardedAdCallback adCallback = new RewardedAdCallback() {
                                public void onRewardedAdOpened() {
                                    // Ad opened.
                                }

                                public void onRewardedAdClosed() {
                                    rewardedAd = createAndLoadRewardedAd();
                                    rewardDialog.dismiss();
                                    // Ad closed.
                                }

                                public void onUserEarnedReward(@NonNull RewardItem reward) {
                                    // User earned reward.


                                    gameDao.updatePoint( user.getPoint() + rewardPoint );

                                    user= gameDao.getUser();
                                    Integer point = new Integer(user.getPoint());
                                    pointText.setText(point.toString());
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
                };

                negativeListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        rewardDialog.dismiss();
                    }
                };

                rewardDialog = new HintDialog(QuizMainActivity.this, positiveListener, negativeListener);
                rewardDialog.callFunction("보상광고를 보시겠습니까?", rewardPoint.toString());

            }
        });
    }
    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rerewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                rewardedAd = createAndLoadRewardedAd();
            }
        };
        rerewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rerewardedAd;
    }
    public void setUp(int nextMovNum){
        movie = gameDao.getMovie(nextMovNum);

        user= gameDao.getUser();
        Integer point = new Integer(user.getPoint());
        pointText.setText(point.toString());

        // 현재 진행 중인 문제 번호
        GameStatus.user.setCurrMovNum(nextMovNum);

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
                correctDialog.callFunction(answerPoint.toString());

                gameDao.updatePoint( user.getPoint() + answerPoint );

                GameStatus.user.setDoneMovNum(GameStatus.user.getDoneMovNum()+1);

                gameDao.updateDoneMovNum(GameStatus.user.getDoneMovNum());    // 완료 된 번호 db업데이트 후 다음 문제를 갖고 온다.

                setUp(GameStatus.user.getNextMovNum()); // 다음 문제 출력등 셋업한다.
            }else{  // 틀렸으면..
                IncorrectDialog incorrectDialog = new IncorrectDialog(QuizMainActivity.this);
                incorrectDialog.callFunction(answerEdit);
            }
            }
        });
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

        final Integer hintPoint1 = new Integer(GameStatus.movScriptHintPoint);
        final Integer hintPoint2 = new Integer(GameStatus.movActorHintPoint);
        final Integer hintPoint3 = new Integer(GameStatus.movImgHintPoint);
        final Integer hintPoint4 = new Integer(GameStatus.movNameHintPoint);

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
                        user = gameDao.getUser();
                        if(user.getPoint() >= hintPoint1){

                            gameDao.updatePoint( user.getPoint() - hintPoint1 );

                            user= gameDao.getUser();
                            Integer point = new Integer(user.getPoint());
                            pointText.setText(point.toString());

                            hintText1.setText( movie.getMovScript() );
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
                if( hintText1.getText() == "" ) {
                    hintDialog = new HintDialog(QuizMainActivity.this, positiveListener, negativeListener);
                    hintDialog.callFunction("명대사힌트를 보시겠습니까?", hintPoint1.toString());
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
                        user = gameDao.getUser();
                        if(user.getPoint() >= hintPoint2){

                            gameDao.updatePoint( user.getPoint() - hintPoint2 );

                            user= gameDao.getUser();
                            Integer point = new Integer(user.getPoint());
                            pointText.setText(point.toString());

                            hintText2.setText(movie.getMovActor());
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
                if( hintText2.getText() == "" ) {
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
                        user = gameDao.getUser();
                        if(user.getPoint() >= hintPoint3){

                            gameDao.updatePoint( user.getPoint() - hintPoint3 );
                            imageHintBool[0] = true;
                            user= gameDao.getUser();
                            Integer point = new Integer(user.getPoint());
                            pointText.setText(point.toString());

                            hintDialog.dismiss();

                            HintImageDialog hintImageDialog = new HintImageDialog(QuizMainActivity.this);
                            hintImageDialog.callFunction(movie.getMovImgPath());
                            hintText3.setText(movie.getMovImgPath());

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
                if( hintText3.getText() == "" ) {
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
                        user = gameDao.getUser();
                        if(user.getPoint() >= hintPoint4){

                            gameDao.updatePoint( user.getPoint() - hintPoint4 );

                            user= gameDao.getUser();
                            Integer point = new Integer(user.getPoint());
                            pointText.setText(point.toString());

                            hintText4.setText(movie.getMovName());
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
                if( hintText4.getText() == "" ) {
                    hintDialog = new HintDialog(QuizMainActivity.this, positiveListener, negativeListener);
                    hintDialog.callFunction("정답을 보시겠습니까?", hintPoint4.toString());
                }
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