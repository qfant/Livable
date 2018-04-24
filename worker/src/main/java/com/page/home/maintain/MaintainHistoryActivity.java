package com.page.home.maintain;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.maintain.MaintainHistoryListResult.MaintainItem;
import com.page.home.patrol.PatrolHistoryDetailActivity;
import com.page.home.patrol.PatrolListAdapter;
import com.page.home.patrol.PatrolListResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class MaintainHistoryActivity extends BaseActivity {
    private ListView listView;
    private MaintainHistoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        setTitleBar("维修记录", true);
        listView = (ListView) findViewById(R.id.list);
        adapter = new MaintainHistoryListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        List<MaintainItem> patrolItems = new ArrayList<>();
        MaintainItem item = new MaintainItem();
        item.name = "世纪城1号泵";
        item.updateTime = "2015-12-12 12:12:12";
        patrolItems.add(item);
        patrolItems.add(item);
        patrolItems.add(item);
        adapter.setData(patrolItems);
//        PatrolListParam patrolListParam = new PatrolListParam();
//        Request.startRequest(patrolListParam, ServiceMap.maintainHistory, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        super.onItemClick(adapterView, view, i, l);
        MaintainItem item = adapter.getItem(i);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MaintainItem.TAG, item);
        qStartActivity(MaintainHistoryDetailActivity.class, bundle);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.patrolList) {
            if (param.result.bstatus.code == 0) {
                MaintainHistoryListResult result = (MaintainHistoryListResult) param.result;
                adapter.setData(result.data.maintainItems);
            }
        }
        return super.onMsgSearchComplete(param);
    }

}

