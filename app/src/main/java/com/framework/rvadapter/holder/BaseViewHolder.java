package com.framework.rvadapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by shucheng.qu on 2016/12/15.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private View mConvertView;
    public Context mContext;
    private List<T> mDatas;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
    }

//    public BaseViewHolder(Context context, ViewGroup pare) {
//        this(context, LayoutInflater.from(context).inflate(getLayoutResId(),pare,false));
//    }

    public abstract void onBindViewHolder(BaseViewHolder holder, T data, int position);

//    public int getLayoutResId() {
//
//        return 0;
//    }

    public View getConvertView() {
        return mConvertView;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

}
