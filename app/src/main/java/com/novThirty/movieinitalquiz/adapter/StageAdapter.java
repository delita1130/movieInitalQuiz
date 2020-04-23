package com.novThirty.movieinitalquiz.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.novThirty.movieinitalquiz.QuizMainActivity;
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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.item_stage, viewGroup, false);
        }

        ImageButton stageBtn = view.findViewById(R.id.stageBtn);
        stageBtn.setLayoutParams(new LinearLayout.LayoutParams(450, 500));

        User user = GameStatus.user;

        // 완료 번호가 문제의 번호보다 크거나 같으면 stage을 열어 둔다.
        // 첫번째 stage는 무조건 연다. 처음 시작하면 완료번호가 0이기 때문에 열어 둔다.

        final boolean flag = user.getDoneMovNum() + GameStatus.numOfstepPerStage + 1 >= items.get(i).getMovNum();

        Log.d("test :: 루프 번호 : ", i + "");
        Log.d("test :: 문제 번호 : ", items.get(i).getMovNum() + "");
        Log.d("test :: 완료 번호 : ", user.getDoneMovNum() + 1 + "");
        Log.d("test :: 스테이지 번호 : ", items.get(i).getStage() + "");

        if(flag) {
            stageBtn.setImageResource(R.drawable.ic_open_stage);
        }else {
            stageBtn.setImageResource(R.drawable.ic_lock_stage);
        }

        stageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag) {
                    Intent intent = new Intent(mContext, QuizMainActivity.class);
                    v.getContext().startActivity(intent);
                }
            }
        });

        return view;
    }
}
