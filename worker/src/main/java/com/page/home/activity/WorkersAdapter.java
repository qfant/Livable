package com.page.home.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framework.adapter.utils.QSimpleAdapter;
import com.haolb.client.R;
import com.page.home.activity.WorkersResult.WorkersItem;
import com.page.home.patrol.PatrolCheckOrdersResult.CheckOrder;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class WorkersAdapter extends QSimpleAdapter<WorkersItem> {
    public WorkersAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.pub_patrol_list_item_view, parent, false);
    }

    @Override
    protected void bindView(View view, Context context, WorkersItem item, int position) {
        TextView textName = (TextView) view.findViewById(R.id.text_name);
        TextView textTime = (TextView) view.findViewById(R.id.text_time);
        textName.setText(item.name);
        textTime.setText(item.phone);
//        textTime.setText(item.qrcode);
    }
}
