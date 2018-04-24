package com.page.home.maintain;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.adapter.utils.QSimpleAdapter;
import com.framework.utils.cache.ImageLoader;
import com.haolb.client.R;
import com.page.home.maintain.MaintainHistoryListResult.MaintainItem;
import com.page.home.maintain.RepairResult.Data.RepairList;
import com.page.home.patrol.PatrolListResult.PatrolItem;

import butterknife.BindView;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class MaintainHistoryListAdapter extends QSimpleAdapter<RepairList> {
    public MaintainHistoryListAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.pub_activity_maintain_item_layout, parent, false);
    }

    @Override
    protected void bindView(View view, Context context, RepairList data, int position) {

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_state = (TextView) view.findViewById(R.id.tv_state);
        ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);
        title.setText(data.intro);
        tv_content.setText(data.address);
        tv_state.setText(data.statusCN);
        ImageLoader.getInstance(mContext).loadImage(data.pic, iv_image);

    }
}
