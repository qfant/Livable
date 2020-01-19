package com.page.home.maintain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
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
import com.page.home.maintain.RepairResult.Data.RepairList;
import com.page.home.maintain.details.activity.DetailsActivity;
import com.page.home.patrol.PatrolListAdapter1;
import com.page.home.patrol.PatrolListParam;
import com.page.home.patrol.PatrolListResult;
import com.page.home.patrol.PatrolPlacesResult;
import com.page.home.patrol.UnPatrolListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class UnPatrolActivity extends BaseActivity implements SwipRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    private UnPatrolListAdapter adapter;
    @BindView(R.id.unPatrolList)
    RecyclerView unPatrolList;
    @BindView(R.id.unPatrolRefreshLayout)
    SwipRefreshLayout srlDownRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_un_patrol);
        ButterKnife.bind(this);
        setTitleBar("未按时巡查设备", true);
        adapter = new UnPatrolListAdapter(R.layout.pub_un_patrol_list_item_view);
        unPatrolList.addItemDecoration(new LineDecoration(this));
        unPatrolList.setLayoutManager(new LinearLayoutManager(getContext()));
        unPatrolList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
        startRequest(1);
    }
    private void startRequest(int page) {
        PatrolListParam param = new PatrolListParam();
        param.pageNo = page;
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.unPatrolList, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.unPatrolList, mHandler);
        }
    }
//    private void requestData() {
//        MaintainHistoryParam patrolListParam = new MaintainHistoryParam();
//        Request.startRequest(patrolListParam, ServiceMap.unPatrolList, mHandler, Request.RequestFeature.BLOCK);
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        super.onItemClick(adapterView, view, i, l);
//        RepairList item = adapter.getItem(i);
//        Bundle bundle = new Bundle();
//        bundle.putString("id", item.id);
//        qStartActivity(DetailsActivity.class, bundle);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.unPatrolList) {
            if (param.result.bstatus.code == 0) {
                PatrolListResult result = (PatrolListResult) param.result;
                if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.checkList)) {
                    if ((int) param.ext == 1) {
                        adapter.setNewData(result.data.checkList);
                    } else {
                        adapter.addData(result.data.checkList);
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

    }

    @Override
    public void onRefresh(int index) {

    }

    @Override
    public void onLoad(int index) {
        startRequest(index++);
    }
}

