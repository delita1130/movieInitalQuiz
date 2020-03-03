package com.novThirty.movieinitalquiz.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.novThirty.movieinitalquiz.model.Movie;
import com.novThirty.movieinitalquiz.viewer.StageViewer;

import java.util.List;

public class StageAdapter extends BaseAdapter {
    List<Movie> items;
    Context mContext;

    public StageAdapter(Context mContext, List<Movie> items) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(Movie gameInfo){
        items.add(gameInfo);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        StageViewer stageViewer = new StageViewer(mContext);
        stageViewer.setItem(items.get(i));

        //ivIcon.setLayoutParams(new GridView.LayoutParams(
        //(int)mContext.getResources().getDimension(R.dimen.widthImageGridview),
        //(int)mContext.getResources().getDimension(R.dimen.heihgtImageGridview)));

        return stageViewer;
    }

}
