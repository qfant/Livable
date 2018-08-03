package com.page.home.patrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.haolb.client.R;
import com.page.home.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolTourHistoryActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, SwipRefreshLayout.OnRefreshListener{
    private PatrolTourHistoryAdapter adapter;
    @BindView(R.id.list)
    RecyclerView listView;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout3);
        setTitleBar("巡更打点记录", true);
        ButterKnife.bind(this);
//        listView = (ListView) findViewById(R.id.list);
        adapter = new PatrolTourHistoryAdapter(R.layout.pub_patrol_list_item_view);
        listView.addItemDecoration(new LineDecoration(this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
        startRequest(1);
    }

    private void requestData() {
        PatrolTourHistoryParam patrolListParam = new PatrolTourHistoryParam();
        Request.startRequest(patrolListParam, ServiceMap.patrolList, mHandler, Request.RequestFeature.BLOCK);
    }
    private void startRequest(int page) {
        PatrolTourHistoryParam param = new PatrolTourHistoryParam();
        param.pageNo = page;
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.patrolList, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.patrolList, mHandler);
        }

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
//        super.onMsgSearchComplete(param);
//        if (param.key == ServiceMap.patrolList) {
//            if (param.result.bstatus.code == 0) {
//                PatrolTourHistoryResult result = (PatrolTourHistoryResult) param.result;
//                adapter.setData(result.data.patrolList);
//            }
//        }
//        return super.onMsgSearchComplete(param);
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.patrolList) {
            if (param.result.bstatus.code == 0) {
                PatrolTourHistoryResult result = (PatrolTourHistoryResult) param.result;
                if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.patrolList)) {
                    if ((int) param.ext == 1) {
                        adapter.setNewData(result.data.patrolList);
                    } else {
                        adapter.addData(result.data.patrolList);
                    }
                } else {
                    showToast("没有更多了");
                }
                srlDownRefresh.setRefreshing(false);
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        PatrolTourHistoryResult.TourHistoryItem item = (PatrolTourHistoryResult.TourHistoryItem) adapter.getItem(position);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(PatrolTourHistoryResult.TourHistoryItem.TAG, item);
//        qStartActivity(PatrolHistoryDetailActivity.class, bundle);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(index++);
    }
}

