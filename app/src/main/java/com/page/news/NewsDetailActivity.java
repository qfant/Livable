package com.page.news;

import android.os.Bundle;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2018/1/17.
 */

public class NewsDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail_layout);
        String title = myBundle.getString("title");
        String info = myBundle.getString("info");
        setTitleBar(title, true);
        TextView tvContent = (TextView) findViewById(R.id.tv_text_content);
        tvContent.setText(info);

    }
}
