package com.page.home.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.framework.activity.BaseActivity;
import com.framework.activity.BaseFragment;
import com.framework.utils.QLog;
import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabLayout;
import com.haolb.client.R;
import com.page.home.activity.HomeFragment;

import java.util.ArrayList;

/**
 * Created by shucheng.qu on 2017/5/31.
 */

public class MainTabFragment extends BaseFragment implements TabLayout.OnTabClickListener {

    protected final ArrayList<TabItem> mTabs = new ArrayList<TabItem>();

    //    @BindView(R.id.tl_tab)
    TabLayout tabLayout;
    ViewPager viewPage;


    protected void addTab(String text, Class<? extends BaseFragment> clss, Bundle bundle, int... icon) {
        TabItem tabItem = new TabItem(text, icon, clss, bundle);
        if (!mTabs.contains(tabItem)) {
            mTabs.add(tabItem);
        }
    }

    protected void onPostCreate() {
        tabLayout.initData(mTabs, this);
        viewPage.setAdapter(new FragmentPagerAdapter(getContext().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                HomeFragment fragment = null;
                try {
                    TabItem tabItem = mTabs.get(position);
                    fragment = (HomeFragment) tabItem.tagFragmentClz.newInstance();
                    Bundle bundle = new Bundle();
                    QLog.e("bundle   ::::::     ", position + "");
                    bundle.putInt("type", position);
                    fragment.setArguments(bundle);
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        });
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setCurrentTab(0);
    }

    @Override
    public void onTabClick(TabItem tabItem) {
        int index = mTabs.indexOf(tabItem);
        viewPage.setCurrentItem(index);
    }
}
