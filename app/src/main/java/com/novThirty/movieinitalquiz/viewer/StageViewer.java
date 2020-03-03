package com.novThirty.movieinitalquiz.viewer;

import android.content.Context;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.novThirty.movieinitalquiz.R;
import com.novThirty.movieinitalquiz.config.GameStatus;
import com.novThirty.movieinitalquiz.model.Movie;
import com.novThirty.movieinitalquiz.model.User;

public class StageViewer extends LinearLayout {
    ImageButton stageBtn;
    String TAG = "StageViewer";

    public StageViewer(Context mContext) {
        super(mContext);

        init(mContext);
    }

    public StageViewer(Context mContext, @Nullable AttributeSet attrs) {
        super(mContext, attrs);

        init(mContext);
    }

    public void init(Context mContext){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_stage,this,true);

        stageBtn = findViewById(R.id.stageBtn);
        stageBtn.setLayoutParams(new LinearLayout.LayoutParams(450, 500));
    }

    public void setItem(Movie movie){
        User user = GameStatus.user;

        Log.i(TAG, movie.getMovNum() + ", " + user.getCurrMovNum() + "");

        // 최초 user의 currMovNum은 1 이여야 함.
        if(user.getCurrMovNum() >= movie.getMovNum()){
            stageBtn.setImageResource(R.drawable.ic_open_stage);
        }else{
            stageBtn.setImageResource(R.drawable.ic_lock_stage);
        }
    }
}
