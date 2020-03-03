package com.novThirty.movieinitalquiz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.novThirty.movieinitalquiz.R;
import com.novThirty.movieinitalquiz.config.GameStatus;
import com.novThirty.movieinitalquiz.model.Movie;
import com.novThirty.movieinitalquiz.model.User;

import java.util.List;

public class StageAdapter extends BaseAdapter {
    List<Movie> items;
    Context mContext;
    private LayoutInflater mInflater;


    public StageAdapter(Context mContext, List<Movie> items) {
        this.items = items;
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(Movie movie){
        items.add(movie);
    }

    @Override
    public Movie getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.item_stage, viewGroup, false);

        ImageButton stageBtn = view.findViewById(R.id.stageBtn);
        stageBtn.setLayoutParams(new LinearLayout.LayoutParams(450, 500));

        User user = GameStatus.user;

        final boolean flag = user.getCurrMovNum() >= items.get(i).getMovNum();

        // 최초 user의 currMovNum은 1 이여야 함.
        if(flag){
            stageBtn.setImageResource(R.drawable.ic_open_stage);
        }else{
            stageBtn.setImageResource(R.drawable.ic_lock_stage);
        }


        stageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag)
                Log.d("test ::", items.get(i).getMovNum() + ";;");
            }
        });

        return view;
    }
}
