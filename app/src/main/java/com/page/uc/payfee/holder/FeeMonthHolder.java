package com.page.uc.payfee.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.BusinessUtils;
import com.framework.utils.DateFormatUtils;
import com.framework.utils.TextViewUtils;
import com.page.uc.payfee.model.FeeMonthResult.Data.DatasX;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/9/17.
 */

public class FeeMonthHolder extends BaseViewHolder<DatasX> {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    public FeeMonthHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_fragment_feemonth_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, DatasX data, int position) {
        if (data == null) return;
        tvTime.setText(String.format("%s - %s", DateFormatUtils.format(data.startdate, "yyyy.MM.dd"), DateFormatUtils.format(data.enddate, "yyyy.MM.dd")));
        String format = String.format("%s %s元", data.type == 1 ? "在线支付" : "后台收费", BusinessUtils.formatDouble2String(data.price));
        int color = mContext.getResources().getColor(R.color.pub_color_yellow);
        CharSequence charSequence = TextViewUtils.genericColorfulText(format, color, BusinessUtils.formatDouble2String(data.price));
        tvPrice.setText(charSequence);
    }

}
