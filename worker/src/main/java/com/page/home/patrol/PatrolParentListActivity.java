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

public class PatrolParentListActivity extends BaseActivity {
    private ListView listView;
    private PatrolParentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        setTitleBar("手动选择", true);
        listView = (ListView) findViewById(R.id.list);
        adapter = new PatrolParentListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        List<PatrolParentListResult.PatrolItem> patrolItems = new ArrayList<>();
        PatrolParentListResult.PatrolItem item = new PatrolParentListResult.PatrolItem();
        item.name = "1号配电房";
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
        PatrolParentListResult.PatrolItem item = adapter.getItem(i);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PatrolParentListResult.PatrolItem.TAG, item);
        qStartActivity(PatrolListActivity.class, bundle);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.patrolParentList) {
            if (param.result.bstatus.code == 0) {
                PatrolParentListResult result = (PatrolParentListResult) param.result;
                adapter.setData(result.data.patrolItemList);
            }
        }
        return super.onMsgSearchComplete(param);
    }

}

