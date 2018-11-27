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

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolPlacesActivity extends BaseActivity {
    private ListView listView;
    private PatrolPlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        setTitleBar("手动选择", true);
        listView = (ListView) findViewById(R.id.list);
        adapter = new PatrolPlacesAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        PatrolPlacesParam patrolListParam = new PatrolPlacesParam();
        Request.startRequest(patrolListParam, ServiceMap.getProjectPlaces, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        super.onItemClick(adapterView, view, i, l);
        PatrolPlacesResult.Patrol item = adapter.getItem(i);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PatrolPlacesResult.Patrol.TAG, item);
        qStartActivity(PatrolListActivity.class, bundle);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.getProjectPlaces) {
            if (param.result.bstatus.code == 0) {
                PatrolPlacesResult result = (PatrolPlacesResult) param.result;
                adapter.setData(result.data.placesList);
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

