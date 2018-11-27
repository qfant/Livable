package com.page.home.patrol;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haolb.client.R;
import com.page.home.patrol.PatrolListResult.PatrolItem;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolListAdapter1 extends BaseQuickAdapter<PatrolItem, BaseViewHolder> {
    public PatrolListAdapter1(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper,PatrolItem item) {
        helper.setText(R.id.text_name, item.name);
    }
}