package com.page.uc.payfee.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.activity.BaseFragment;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.uc.payfee.holder.FeeMonthHolder;
import com.page.uc.payfee.holder.OweHolder;
import com.page.uc.payfee.model.FeeListParam;
import com.page.uc.payfee.model.FeeMonthResult;
import com.page.uc.payfee.model.OweListResult;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by shucheng.qu on 2017/9/16.
 */

public class OweFragment extends BaseFragment implements SwipRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    Unbinder unbinder;
    private MultiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_feelist_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        startRequest(1);
    }

    private void startRequest(int pager) {
        FeeListParam param = new FeeListParam();
        param.pageNo = pager;
        Request.startRequest(param, pager, ServiceMap.getMyArrearageList, mHandler);
    }

    private void setListView() {
        adapter = new MultiAdapter(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new OweHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_fragment_owe_item_layout, parent, false));
            }
        });

        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.addItemDecoration(new LineDecoration(getContext(), LineDecoration.VERTICAL_LIST, R.drawable.pub_gray_line_5));
        rvList.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getMyArrearageList) {
            OweListResult result = (OweListResult) param.result;
            if (result != null &&  !ArrayUtils.isEmpty(result.data.arrearages)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.arrearages);
                } else {
                    adapter.addData(result.data.arrearages);
                }
            } else {
                if ((int) param.ext == 1) {
                    showToast("没有数据");
                } else {
                    showToast("没有更多了");
                }
            }
            refreshLayout.setRefreshing(false);
        }
        return false;
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
