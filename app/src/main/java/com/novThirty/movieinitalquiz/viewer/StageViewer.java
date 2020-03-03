package com.novThirty.movieinitalquiz.viewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.novThirty.movieinitalquiz.R;
import com.novThirty.movieinitalquiz.model.GameInfo;
import com.novThirty.movieinitalquiz.model.Movie;

public class StageViewer extends LinearLayout {
    ImageButton stageBtn;

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

        stageBtn = (ImageButton) findViewById(R.id.stageBtn);
        stageBtn.setLayoutParams(new LinearLayout.LayoutParams(450, 500));
    }

    public void setItem(Movie movie){
        //if(진행한 스테이지){
            stageBtn.setImageResource(R.drawable.ic_lock_stage);
        //}else{
            //stageBtn.setImageResource(R.drawable.ic_open_stage);
        //}
    }
}
