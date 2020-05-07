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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StageAdapter extends BaseAdapter {
    private List<Movie> mItemList;
    private Context mContext;
    private LayoutInflater mInflater;
    private ViewHolder mHolder;

    private class ViewHolder {
        ImageButton item;
    }

    public StageAdapter(Context mContext, List<Movie> items) {
        this.mItemList = items;
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(mItemList.size()>0) {
            return mItemList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_stage, parent, false);
            mHolder = new ViewHolder();
            mHolder.item = convertView.findViewById(R.id.stageBtn);
            mHolder.item.setLayoutParams(new LinearLayout.LayoutParams(450, 500));

            convertView.setTag(mHolder);
        }else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        // 완료 번호가 문제의 번호보다 크거나 같으면 stage을 열어 둔다.
        // 첫번째 stage는 무조건 연다. 처음 시작하면 완료번호가 0이기 때문에 열어 둔다.
        //5 >= 6
        final boolean flag = GameStatus.user.getDoneMovNum() >= mItemList.get(i).getMovNum();

       /* Log.d("test :: open : ", flag + "");
        Log.d("test :: 루프 번호 : ", i + "");
        Log.d("test :: 문제 시작 번호 : ", mItemList.get(i).getMovNum() + "");
        Log.d("test :: 완료 번호 : ", GameStatus.user.getDoneMovNum() + "");
        Log.d("test :: 스테이지 번호 : ", mItemList.get(i).getStage() + "");*/

        if(flag) {
            mHolder.item.setImageResource(R.drawable.ic_open_stage);
        }else {
            mHolder.item.setImageResource(R.drawable.ic_lock_stage);
        }

        mHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag) {
                    Intent intent = new Intent(mContext, QuizMainActivity.class);
                    intent.putExtra("clickStage", mItemList.get(i).getStage());
                    intent.putExtra("clickFirstMovNum", mItemList.get(i).getMovNum() + "");

                    v.getContext().startActivity(intent);
                }

                Log.d("test :: 선택 ", "" + mItemList.get(i).getMovNum());
            }
        });

        return convertView;
    }
}
