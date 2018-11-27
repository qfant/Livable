package com.page.uc.payfee.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.BusinessUtils;
import com.framework.utils.DateFormatUtils;
import com.framework.utils.TextViewUtils;
import com.page.uc.payfee.model.OweListResult.Data.Arrearages;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/9/17.
 */

public class OweHolder extends BaseViewHolder<Arrearages> {

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public OweHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_fragment_owe_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Arrearages data, int position) {
        if (data == null) return;
        tvTime.setText(String.format("%s - %s", DateFormatUtils.format(data.startdate, "yyyy.MM.dd"), DateFormatUtils.format(data.enddate, "yyyy.MM.dd")));
        String format = String.format("%s %s元", "欠费金额", BusinessUtils.formatDouble2String(data.monthfee));
        int color = mContext.getResources().getColor(R.color.pub_color_yellow);
        CharSequence charSequence = TextViewUtils.genericColorfulText(format, color, BusinessUtils.formatDouble2String(data.monthfee));
        tvPrice.setText(charSequence);
    }

}
