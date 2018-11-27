package com.page.news;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/1/17.
 */

class NewsHolder extends BaseViewHolder<FengcaiResult.FengcaiBean> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_intro)
    TextView tvIntro;

    public NewsHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_news_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, FengcaiResult.FengcaiBean data, int position) {
        tvTitle.setText(data.title);
        tvIntro.setText(data.intro);
    }
}
