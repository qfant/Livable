package com.page.home.patrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolPlacesActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, SwipRefreshLayout.OnRefreshListener {
    private PatrolPlacesAdapter1 adapter;
    @BindView(R.id.list)
    RecyclerView listView;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    @BindView(R.id.input_search)
    EditText inputSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        setTitleBar("手动选择", true);
        ButterKnife.bind(this);
        adapter = new PatrolPlacesAdapter1(R.layout.pub_patrol_list_item_view);
        listView.addItemDecoration(new LineDecoration(this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
        startRequest(1,"");
    }

    private void startRequest(int page,String keyword) {
        PatrolPlacesParam param = new PatrolPlacesParam();
        param.pageNo = page;
        param.keyword=keyword;
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.getProjectPlaces, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.getProjectPlaces, mHandler);
        }

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
                if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.placesList)) {
                    if ((int) param.ext == 1) {
                        adapter.setNewData(result.data.placesList);
                    } else {
                        adapter.addData(result.data.placesList);
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
        PatrolPlacesResult.Patrol item = (PatrolPlacesResult.Patrol) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PatrolPlacesResult.Patrol.TAG, item);
        qStartActivity(PatrolListActivity.class, bundle);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1,"");
    }

    @Override
    public void onLoad(int index) {
        startRequest(index++,"");
    }
    @OnClick(R.id.text_search)
    public void onViewClicked() {
        String s = inputSearch.getText().toString();
        if (TextUtils.isEmpty(s)) {
            return;
        }
        startRequest(1, s);
    }
}

