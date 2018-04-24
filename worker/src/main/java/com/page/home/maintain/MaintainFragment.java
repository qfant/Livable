package com.page.home.maintain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.activity.BaseFragment;
import com.haolb.client.R;
import com.page.uc.UserInfoActivity;

/**
 * Created by chenxi.cui on 2018/4/23.
 */

public class MaintainFragment extends BaseFragment {
    private View llSendMaintain;
    private View llHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.pub_fragment_maintain_layout);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitleBar("发起维修", false, "个人中心", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qStartActivity(UserInfoActivity.class);
            }
        });
        llSendMaintain = getView().findViewById(R.id.ll_send_maintain);
        llHistory = getView().findViewById(R.id.ll_history);
        llSendMaintain.setOnClickListener(this);
        llHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.equals(llHistory)) {
            qStartActivity(MaintainHistoryActivity.class);
        } else if (v.equals(llSendMaintain)) {
            qStartActivity(MaintainSendActivity.class);
        }
    }
}
