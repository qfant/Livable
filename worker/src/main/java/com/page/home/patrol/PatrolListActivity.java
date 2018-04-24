package com.page.home.patrol;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.ServiceMap;
import com.haolb.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolListActivity extends BaseActivity {
    private ListView listView;
    private PatrolListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        setTitleBar("巡查站点", true);
        listView = (ListView) findViewById(R.id.list);
        adapter = new PatrolListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        List<PatrolListResult.PatrolItem> patrolItems = new ArrayList<>();
        PatrolListResult.PatrolItem item = new PatrolListResult.PatrolItem();
        item.name = "世纪城1号泵";
        item.updateTime = "2015-12-12 12:12:12";
        patrolItems.add(item);
        patrolItems.add(item);
        patrolItems.add(item);
        adapter.setData(patrolItems);
//        PatrolListParam patrolListParam = new PatrolListParam();
//        Request.startRequest(patrolListParam, ServiceMap.patrolList, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        super.onItemClick(adapterView, view, i, l);
        PatrolListResult.PatrolItem item = adapter.getItem(i);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PatrolListResult.PatrolItem.TAG, item);
        qStartActivity(PatrolDetailActivity.class, bundle);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.patrolList) {
            if (param.result.bstatus.code == 0) {
                PatrolListResult result = (PatrolListResult) param.result;
                adapter.setData(result.data.patrolItemList);
            }
        }
        return super.onMsgSearchComplete(param);
    }

}

