package com.page.home.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.utils.QLog;
import com.framework.view.tab.TabLayout;
import com.haolb.client.R;
import com.page.home.activity.HomeFragment;
import com.page.uc.UserInfoActivity;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class OrderFragment extends MainTabFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.pub_order_mian_layout);
        tabLayout = (TabLayout) view.findViewById(R.id.tl_tab);
        viewPage = (ViewPager) view.findViewById(R.id.viewPage);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitleBar("维修订单", false, "个人中心", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qStartActivity(UserInfoActivity.class);
            }
        });
        addTab("待接单", HomeFragment.class, myBundle, R.string.icon_font_home);
        addTab("已接单", HomeFragment.class, myBundle, R.string.icon_font_home);
        addTab("已完成", HomeFragment.class, myBundle, R.string.icon_font_home);
        onPostCreate();
    }

    @Override
    public void onResume() {

        QLog.v("OrderFragment", "onResume" );
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        QLog.v("OrderFragment", "onHiddenChanged" +hidden);
    }
}
