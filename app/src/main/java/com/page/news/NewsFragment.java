package com.page.news;

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
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.store.home.model.ClassifyResult;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.prodetails.activity.ProDetailsActivity;
import com.page.store.prodetails.model.PEParam;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2018/1/17.
 */

public class NewsFragment extends BaseFragment implements SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_nav_list)
    RecyclerView rvNavList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    private Unbinder unbinder;
    private MultiAdapter<FengcaiResult.FengcaiBean> multiAdapter;
    private NewsResult result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.pub_activity_news_layout);
        unbinder = ButterKnife.bind(this, view);
        refreshLayout.setOnRefreshListener(this);
        setRvNavList();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitleBar("小区新闻", false);
        evaluate(1);
    }

    @Override
    public void onRefresh(int index) {
        evaluate(1);
    }

    @Override
    public void onLoad(int index) {

    }

    private void evaluate(int pager) {
        PEParam param = new PEParam();
        param.pageNo = pager;
        param.pageSize = 100;
        Request.startRequest(param, pager, ServiceMap.news, mHandler, Request.RequestFeature.BLOCK);
    }

    private void setRvNavList() {
        multiAdapter = new MultiAdapter<FengcaiResult.FengcaiBean>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new NewsHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_news_item_layout, parent, false));
            }
        });
        rvNavList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNavList.addItemDecoration(new LineDecoration(getContext()));
        rvNavList.setAdapter(multiAdapter);
        multiAdapter.setOnItemClickListener(new OnItemClickListener<FengcaiResult.FengcaiBean>() {
            @Override
            public void onItemClickListener(View view, FengcaiResult.FengcaiBean data, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("title", data.title);
                bundle.putString("info", data.intro);
                qStartActivity(NewsDetailActivity.class, bundle);
            }
        });
    }


    @Override
    public void onNetEnd(NetworkParam param) {
        super.onNetEnd(param);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.news) {
            result = (NewsResult) param.result;
            multiAdapter.setData(result.data.newResult);
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
