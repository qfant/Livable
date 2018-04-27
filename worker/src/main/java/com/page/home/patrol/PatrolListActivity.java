package com.page.home.patrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.activity.MainActivity;
import com.page.home.patrol.PatrolPlacesResult.Patrol;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolListActivity extends BaseActivity {
    private ListView listView;
    private PatrolListAdapter adapter;
    private Patrol patrol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        patrol = (Patrol) myBundle.getSerializable(Patrol.TAG);
        if (patrol == null) {
            finish();
            return;
        }
        setTitleBar("巡查站点", true);
        listView = (ListView) findViewById(R.id.list);
        adapter = new PatrolListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        PatrolListParam patrolListParam = new PatrolListParam();
        patrolListParam.qrcode = patrol.qrcode;
//        Request.startRequest(patrolListParam, ServiceMap.getProjectChecks, mHandler, Request.RequestFeature.BLOCK);
        Request.startRequest(patrolListParam, ServiceMap.getProjectChecksByQrcode, mHandler, Request.RequestFeature.BLOCK);
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
        if (param.key == ServiceMap.getProjectChecks || param.key == ServiceMap.getProjectChecksByQrcode) {
            if (param.result.bstatus.code == 0) {
                PatrolListResult result = (PatrolListResult) param.result;
                adapter.setData(result.data.checkList);
            }else {
                new AlertDialog.Builder(this).setTitle("").setMessage(param.result.bstatus.des)
                        .setNegativeButton("返回首页", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                qBackToActivity(MainActivity.class, null);
                            }
                        }).show();
            }
        }
        return super.onMsgSearchComplete(param);
    }

}

