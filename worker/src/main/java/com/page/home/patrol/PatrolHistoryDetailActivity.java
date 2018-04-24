package com.page.home.patrol;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolHistoryDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_patrol_history_detaill);
        setTitleBar("巡查项目详情", true);
    }
}
