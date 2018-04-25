package com.page.home.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.activity.BaseFragment;
import com.framework.utils.QLog;
import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabLayout;
import com.haolb.client.R;
import com.page.home.activity.HomeFragment;

import java.util.ArrayList;
import java.util.List;

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
        final HomeFragment[] fragments = new HomeFragment[3];
        fragments[0] = getHomeFragment(0);
        fragments[1] = getHomeFragment(1);
        fragments[2] = getHomeFragment(2);
        final boolean[] fragmentsUpdateFlag = {false, false, false};
        final FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(this.getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                HomeFragment fragment = fragments[position % fragments.length];
                return fragment;
            }
            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //得到缓存的fragment
                Fragment fragment = (Fragment) super.instantiateItem(container,
                        position);
//得到tag，这点很重要
                String fragmentTag = fragment.getTag();
                if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
//如果这个fragment需要更新

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//移除旧的fragment
                    ft.remove(fragment);
//换成新的fragment
                    fragment = fragments[position % fragments.length];
//添加新fragment时必须用前面获得的tag，这点很重要
                    ft.add(container.getId(), fragment, fragmentTag);
                    ft.attach(fragment);
                    ft.commit();
                    fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
                }


                return fragment;
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        };
        viewPage.setAdapter(pagerAdapter);
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(final int position) {
                tabLayout.setCurrentTab(position);
//                viewPage.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                HomeFragment homeFragment = fragments.get(position);
//                                homeFragment.onShow();
//                            }
//                        });
//                    }
//                }, 1000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setCurrentTab(0);
    }

    @Nullable
    private HomeFragment getHomeFragment(int position) {
        HomeFragment fragment = null;
        try {
            TabItem tabItem = mTabs.get(position);
            fragment = (HomeFragment) tabItem.tagFragmentClz.newInstance();
            Bundle bundle = new Bundle();
            QLog.e("bundle   ::::::     ", position + "");
            bundle.putInt("type", position);
            fragment.setArguments(bundle);
            fragment.onHiddenChanged(true);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void onTabClick(TabItem tabItem) {
        int index = mTabs.indexOf(tabItem);
        viewPage.setCurrentItem(index);
    }
}
