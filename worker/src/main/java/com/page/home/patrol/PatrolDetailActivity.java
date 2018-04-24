package com.page.home.patrol;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolDetailActivity extends BaseActivity {
    private PatrolListResult.PatrolItem patrolItem;
    private TextView textTitle;
    private LinearLayout llContain;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_patrol_detail_layout);
        setTitleBar("巡查详情", true);
        patrolItem = (PatrolListResult.PatrolItem) myBundle.getSerializable(PatrolListResult.PatrolItem.TAG);
        textTitle = (TextView) findViewById(R.id.text_title);
        llContain = (LinearLayout) findViewById(R.id.ll_contain);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        setData();
        submit();
    }

    private void submit() {

    }

    private void setData() {
        llContain.removeAllViews();
        View view = LinearLayout.inflate(this, R.layout.pub_patrol_detail_item_view, null);
        TextView textName = (TextView) view.findViewById(R.id.text_name);
        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.switch_compat);
        EditText editText = (EditText) view.findViewById(R.id.edit_compat);
        llContain.addView(view);
    }

}
