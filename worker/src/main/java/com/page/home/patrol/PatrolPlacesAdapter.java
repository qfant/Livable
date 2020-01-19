package com.page.home.patrol;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framework.adapter.utils.QSimpleAdapter;
import com.haolb.client.R;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

class PatrolPlacesAdapter extends QSimpleAdapter<PatrolPlacesResult.Patrol> {
    public PatrolPlacesAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.pub_patrol_list_item_view, parent, false);
    }

    @Override
    protected void bindView(View view, Context context, PatrolPlacesResult.Patrol item, int position) {
        TextView textName = (TextView) view.findViewById(R.id.text_name);
        TextView textTime = (TextView) view.findViewById(R.id.text_time);
        textName.setText(item.name);
        textTime.setText(item.serialnum);
        textTime.setVisibility(View.GONE);
    }
}
