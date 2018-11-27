package com.page.community.serve.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.framework.activity.BaseFragment;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.IFView;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.community.complain.ComplainActivity;
import com.page.community.serve.holder.ComplainHolder;
import com.page.community.serve.model.ComplainParam;
import com.page.community.serve.model.ComplainResult;
import com.page.community.serve.model.ComplainResult.Data.ComplainList;
import com.page.community.serve.model.DelComplainParam;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by shucheng.qu on 2017/11/6.
 */

public class ComplainFragment extends BaseFragment implements SwipRefreshLayout.OnRefreshListener, OnItemClickListener<ComplainList> {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_add_qp)
    IFView tvAddQp;
    @BindView(R.id.ll_add_qp)
    LinearLayout llAddQp;
    private MultiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.pub_fragment_complain_layout, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void startRequest(int pager) {
        ComplainParam param = new ComplainParam();
        param.pageNo = pager;
        Request.startRequest(param, pager, ServiceMap.getMyComplainList, mHandler);
    }

    public void delComplain(String id) {
        DelComplainParam param = new DelComplainParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.deleteComplain, mHandler, Request.RequestFeature.BLOCK);
    }

    private void setListView() {
        adapter = new MultiAdapter(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ComplainHolder(ComplainFragment.this, mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_fragment_complain_item_layout, parent, false));
            }
        });

        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        adapter.setOnItemClickListener(this);
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
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getMyComplainList) {
            ComplainResult result = (ComplainResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.complainList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.complainList);
                } else {
                    adapter.addData(result.data.complainList);
                }
            } else {
                if ((int) param.ext == 1) {
//                    showToast("没有数据");
                } else {
                    showToast("没有更多了");
                }
            }
            refreshLayout.setRefreshing(false);

        } else if (param.key == ServiceMap.deleteComplain) {
            if (param.result.bstatus.code == 0) {
                startRequest(1);
            }
            showToast(param.result.bstatus.des);
        }
        return super.onMsgSearchComplete(param);

    }

    @OnClick(R.id.ll_add_qp)
    public void onViewClicked() {
        qStartActivity(ComplainActivity.class);
    }

    @Override
    public void onItemClickListener(View view, ComplainList data, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        qStartActivity(ComplainActivity.class, bundle);
    }
}
