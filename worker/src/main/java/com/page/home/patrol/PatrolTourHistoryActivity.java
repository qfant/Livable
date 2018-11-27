package com.page.home.patrol;

import android.os.Bundle;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolTourHistoryActivity extends BaseActivity {
    private ListView listView;
    private PatrolTourHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        setTitleBar("巡更打点记录", true);
        listView = (ListView) findViewById(R.id.list);
        adapter = new PatrolTourHistoryAdapter(this);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        PatrolTourHistoryParam patrolListParam = new PatrolTourHistoryParam();
        Request.startRequest(patrolListParam, ServiceMap.patrolList, mHandler, Request.RequestFeature.BLOCK);
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        super.onItemClick(adapterView, view, i, l);
//        PatrolTourResult.TourItem item = adapter.getItem(i);
//        PatrolSubmitPatrolParam patrolListParam = new PatrolSubmitPatrolParam();
//        patrolListParam.qrcode = item.qrcode;
//        Request.startRequest(patrolListParam, ServiceMap.submitPatrol, mHandler, Request.RequestFeature.BLOCK);
//    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.patrolList) {
            if (param.result.bstatus.code == 0) {
                PatrolTourHistoryResult result = (PatrolTourHistoryResult) param.result;
                adapter.setData(result.data.patrolList);
            }
        }
        return super.onMsgSearchComplete(param);
    }

}

