package com.page.community.serve.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.DateFormatUtils;
import com.framework.utils.cache.ImageLoader;
import com.page.community.serve.fragment.ComplainFragment;
import com.page.community.serve.model.ComplainResult.Data.ComplainList;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/11/6.
 */

public class ComplainHolder extends BaseViewHolder<ComplainList> {


    private final ComplainFragment fragment;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private ComplainList data;

    public ComplainHolder(ComplainFragment complainFragment, Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_fragment_complain_item_layout;
        this.fragment = complainFragment;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, ComplainList data, int position) {
        if (data == null) return;
        this.data = data;
        ImageLoader.getInstance(mContext).loadImage(data.pic, ivImage);
        tvContent.setText(TextUtils.isEmpty(data.content) ? "" : data.content);
        String time = DateFormatUtils.format(data.createtime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
        String state = data.status == 0 ? "已提交" : "已收到";
        tvTime.setText(time);
        tvState.setText(state);
    }

    @OnClick(R.id.iv_del)
    public void onViewClicked() {
        if (fragment != null && data != null) {
            fragment.delComplain(data.id);
        }
    }
}
