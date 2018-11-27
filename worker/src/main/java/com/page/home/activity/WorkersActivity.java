package com.page.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.detail.DetailResult.DetailData;
import com.page.login.UCUtils;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class WorkersActivity extends BaseActivity {
    private ListView listView;
    private WorkersAdapter adapter;
    private DetailData detailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        setTitleBar("工人列表", true);
        detailData = (DetailData) myBundle.getSerializable(DetailData.TAG);
        listView = (ListView) findViewById(R.id.list);
        adapter = new WorkersAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        WorkersParam patrolListParam = new WorkersParam();
        patrolListParam.company_id = UCUtils.getInstance().getUserInfo().districtid;
        Request.startRequest(patrolListParam, ServiceMap.getWorkers, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        super.onItemClick(adapterView, view, i, l);
        WorkersResult.WorkersItem item = adapter.getItem(i);
        AssignRepairOrderParam patrolListParam = new AssignRepairOrderParam();
        patrolListParam.orderId = detailData.id;
        patrolListParam.workerId = item.id;
        Request.startRequest(patrolListParam, ServiceMap.assignRepairOrder, mHandler, Request.RequestFeature.BLOCK);

    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.getWorkers) {
            if (param.result.bstatus.code == 0) {
                WorkersResult result = (WorkersResult) param.result;
                adapter.setData(result.data.workerList);
            }
        } else if (param.key == ServiceMap.assignRepairOrder) {
            showToast(param.result.bstatus.des);
            if (param.result.bstatus.code == 0) {
                qBackToActivity(MainActivity.class, null);
            }
        }
        return super.onMsgSearchComplete(param);
    }

}

