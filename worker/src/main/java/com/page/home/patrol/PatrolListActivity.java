package com.page.home.patrol;

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
import com.page.home.patrol.PatrolPlacesResult.Patrol;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolListActivity extends BaseActivity implements SwipRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    private PatrolListAdapter1 adapter;
    private Patrol patrol;
    @BindView(R.id.list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout1);
        ButterKnife.bind(this);
        patrol = (Patrol) myBundle.getSerializable(Patrol.TAG);
        if (patrol == null) {
            finish();
            return;
        }
        setTitleBar("巡查站点", true);
        adapter = new PatrolListAdapter1(R.layout.pub_patrol_list_item_view);
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
        startRequest(1);
    }
    private void startRequest(int page) {
        PatrolListParam param = new PatrolListParam();
        param.qrcode = patrol.serialnum;
        param.pageNo = page;
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.getProjectChecksByQrcode, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.getProjectChecksByQrcode, mHandler);
        }
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
    public void onRefresh(int index) {
         startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(index++);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PatrolListResult.PatrolItem item = (PatrolListResult.PatrolItem) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PatrolListResult.PatrolItem.TAG, item);
        qStartActivity(PatrolDetailActivity.class, bundle);
    }
}

