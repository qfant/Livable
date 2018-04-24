package com.page.home.patrol;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolDetailActivity extends BaseActivity {
    private PatrolListResult.PatrolItem patrolItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_patrol_detail_layout);
        setTitleBar("巡查详情", true);
        patrolItem = (PatrolListResult.PatrolItem) myBundle.getSerializable(PatrolListResult.PatrolItem.TAG);
    }
}
