package com.page.home.patrol;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;

import java.util.ArrayList;
import java.util.List;

import static com.page.home.patrol.PatrolCheckOrdersResult.*;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolCheckOrdersActivity extends BaseActivity {
    private ListView listView;
    private PatrolCheckOrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        setTitleBar("巡查记录", true);
        listView = (ListView) findViewById(R.id.list);
        adapter = new PatrolCheckOrdersAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        PatrolListParam patrolListParam = new PatrolListParam();
        Request.startRequest(patrolListParam, ServiceMap.checkOrderList, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        super.onItemClick(adapterView, view, i, l);
        CheckOrder item = adapter.getItem(i);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CheckOrder.TAG, item);
        qStartActivity(PatrolHistoryDetailActivity.class, bundle);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.checkOrderList) {
            if (param.result.bstatus.code == 0) {
                PatrolCheckOrdersResult result = (PatrolCheckOrdersResult) param.result;
                adapter.setData(result.data.orderListResult);
            }
        }
        return super.onMsgSearchComplete(param);
    }

}

