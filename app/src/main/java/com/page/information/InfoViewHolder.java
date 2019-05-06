package com.page.information;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.view.CircleImageView;
import com.page.information.InfoPlatformResult.InfoItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class InfoViewHolder extends BaseViewHolder<InfoItem> {

    @BindView(R.id.image_pic)
    CircleImageView imagePic;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;

    public InfoViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_info_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, InfoItem data, int position) {
        text1.setText("企业: " + data.name);
        text2.setText("电话：" + data.phone);
        text3.setText("地址：" + data.address);
        text2.setTag(data.phone);
//        ImageLoad.loadPlaceholder(mContext, data.headpic, imagePic);
    }

    @OnClick(R.id.text_2)
    public void onViewClicked() {
        String  phone = (String) text2.getTag();
        ((BaseActivity)mContext).processAgentPhoneCall(phone);
    }
}
