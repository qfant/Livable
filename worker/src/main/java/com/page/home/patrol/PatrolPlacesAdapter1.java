package com.page.home.patrol;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haolb.client.R;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

class PatrolPlacesAdapter1 extends BaseQuickAdapter<PatrolPlacesResult.Patrol,BaseViewHolder> {


    public PatrolPlacesAdapter1(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolPlacesResult.Patrol item) {
        helper.setText(R.id.text_name, item.name);
        helper.getView(R.id.text_time).setVisibility(View.GONE);

    }
}
