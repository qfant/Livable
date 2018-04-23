package com.page.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.framework.activity.BaseFragment;
import com.framework.activity.FragmentBackHelper;
import com.framework.app.MainApplication;
import com.framework.domain.param.BaseParam;
import com.framework.domain.response.UpgradeInfo;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.utils.ShopCarUtils;
import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabLayout;
import com.framework.view.tab.TabView;
import com.page.store.home.fragment.ShopHomeFragment;
import com.page.store.orderdetails.activity.OrderDetailsActivity;
import com.page.uc.UpdateParam;
import com.page.uc.bean.CheckVersionResult;
import com.qfant.wuye.R;
import com.page.community.quickpai.activity.AddQPaiActivity;
import com.page.uc.UserCenterFragment;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class MainActivity extends MainTabActivity {

    public static final String REFRESH_TAB_ACTION = "com.qfant.wuye.refreshtab";

    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    private boolean mIsExit;
    private TabNumberReceiver tabNumberReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_mian_layout);
        ButterKnife.bind(this);
        tabLayout = tlTab;
        addTab("主页", HomeFragment.class, myBundle, R.string.icon_font_home);
        addTab("商城", ShopHomeFragment.class, myBundle, R.string.icon_font_shopping);
        addTab("随手拍", QpListFragment.class, myBundle, R.string.icon_font_camera);
        addTab("购物车", ShoppingCartFragment.class, myBundle, R.string.icon_font_buy_car);
        addTab("我的", UserCenterFragment.class, myBundle, R.string.icon_font_my);
        onPostCreate();
        tabNumberReceiver = new TabNumberReceiver();
        IntentFilter filter = new IntentFilter(REFRESH_TAB_ACTION);
        registerReceiver(tabNumberReceiver, filter);


    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null && intent.getExtras() != null) {
            String aGoto = intent.getExtras().getString("goto");
            if (TextUtils.equals(aGoto, "orderDetail")) {
                String id = intent.getExtras().getString("id");
                Bundle bundle = new Bundle();
                bundle.putString(OrderDetailsActivity.ID, "" + id);
                qBackToActivity(OrderDetailsActivity.class, bundle);
            }
        }
    }


    @Override
    public void onTabClick(TabItem tabItem) {
//        if ("商城".equals(tabItem.text) || "购物车".equals(tabItem.text)) {
//            showToast("暂未开放，敬请期待！");
//        } else {
            super.onTabClick(tabItem);
//        }
    }


    @Override
    public void onBackPressed() {
        if (!FragmentBackHelper.onBackPressed(this)) {
            exitBy2Click();
        }
    }

    public void exitBy2Click() {
        Timer tExit;
        if (!mIsExit) {
            mIsExit = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    mIsExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(tabNumberReceiver);
    }

    public class TabNumberReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                TabView shopCarTab = (TabView) tabLayout.getChildAt(3);
                shopCarTab.setNumber(ShopCarUtils.getInstance().getShopCarSize());
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.CHECK_VERSION) {
            final CheckVersionResult checkVersionResult = (CheckVersionResult) param.result;
            if (checkVersionResult.bstatus.code == 0) {
                if (checkVersionResult.data != null
                        && checkVersionResult.data.upgradeInfo != null) {
                    updateDialog(checkVersionResult.data.upgradeInfo);
                }

            } else {
                showToast(param.result.bstatus.des);
            }
        }

        return super.onMsgSearchComplete(param);
    }

    private void updateDialog(final UpgradeInfo upgradeInfo) {
        AlertDialog.Builder dialog = null;
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(!upgradeInfo.force);
            dialog.setTitle("更新提示");
            dialog.setMessage("最新版本："
                    + upgradeInfo.nversion
                    + "\n"
                    + upgradeInfo.upgradeNote);
            dialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog,
                                    int which) {
                    Uri uri = Uri
                            .parse(upgradeInfo.upgradeAddress[0].url);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                    dialog.dismiss();
                }

            });
        }
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        UpdateParam updateParam = new UpdateParam();
//        updateParam.versionCode = MainApplication.versionCode;
        Request.startRequest(new BaseParam(), ServiceMap.CHECK_VERSION, mHandler, Request.RequestFeature.CANCELABLE);
        sendBroadcast(new Intent(REFRESH_TAB_ACTION));
    }


}
