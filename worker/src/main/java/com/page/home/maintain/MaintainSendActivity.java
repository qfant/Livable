package com.page.home.maintain;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class MaintainSendActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_maintain_send_layout);
        setTitleBar("发起维修", true);
    }
}
