package com.page.home.maintain;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class MaintainHistoryDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_maintain_history_detaill_layout);
        setTitleBar("维修详情", true);
    }
}
