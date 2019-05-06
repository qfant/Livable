package com.page.partymanger;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.partymanger.MeetingInfoResult.MeetingInfoItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class MeetingInfoActivity extends BaseActivity implements OnItemClickListener<MeetingInfoItem>, SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    private MultiAdapter<MeetingInfoItem> adapter;
    private int meetingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_manager_list_layout);
        ButterKnife.bind(this);
        meetingId = myBundle.getInt("meetingId");
        setTitleBar("会议内容", true);
        setListView();
        startRequest(1);
    }

    private void setListView() {
        adapter = new MultiAdapter<MeetingInfoItem>(getContext()).addTypeView(new ITypeView<MeetingInfoItem>() {
            @Override
            public boolean isForViewType(MeetingInfoItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new MeetingInfoViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_meeting_info_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
    }


    private void startRequest(int page) {
        MeetingInfoParam param = new MeetingInfoParam();
        param.meetingid = meetingId;
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.meetingStatementList, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.meetingStatementList, mHandler);
        }
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.meetingStatementList) {
            MeetingInfoResult result = (MeetingInfoResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.meetingRecordList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.meetingRecordList);
                } else {
                    adapter.addData(result.data.meetingRecordList);
                }
            } else {
                showToast("没有更多了");
            }
            srlDownRefresh.setRefreshing(false);
        }
        return false;
    }

    @Override
    public void onItemClickListener(View view, MeetingInfoItem data, int position) {
//        Bundle bundle = new Bundle();
//        bundle.putInt("id", data.id);
//        qStartActivity(MeetingDetailActivity.class, bundle);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }

}
