package com.page.home.maintain;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.maintain.RepairResult.Data.RepairList;
import com.page.home.maintain.details.activity.DetailsActivity;

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
        List<RepairList> patrolItems = new ArrayList<>();
        RepairList item = new RepairList();
        item.intro = "xxxx";
        item.address = "xxxx";
        item.statusCN = "xxxx";
        item.pic = "xxxx";
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
        RepairList item = adapter.getItem(i);
        Bundle bundle = new Bundle();
        bundle.putString("id", item.id);
        qStartActivity(DetailsActivity.class, bundle);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.getProjectPlaces) {
            if (param.result.bstatus.code == 0) {
                RepairResult result = (RepairResult) param.result;
                adapter.setData(result.data.repairList);
            }
        }
        return super.onMsgSearchComplete(param);
    }

}

