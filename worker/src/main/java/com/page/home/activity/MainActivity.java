package com.page.home.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.framework.domain.param.BaseParam;
import com.framework.domain.response.UpgradeInfo;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.view.tabb.TabLayout;
import com.haolb.client.R;
import com.page.home.maintain.MaintainFragment;
import com.page.home.patrol.PatrolFragment;
import com.page.home.order.OrderFragment;
import com.page.login.UCUtils;
import com.page.login.activity.LoginActivity;
import com.page.update.CheckVersionResult;
import com.page.update.UpdateParam;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class MainActivity extends MainTabActivity {

    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.tv_right)
    TextView tv_right;
    private boolean mIsExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_mian_layout);
        ButterKnife.bind(this);
        tabLayout = tlTab;
        addTab("维修订单", OrderFragment.class, myBundle, R.string.icon_font_tab_order);
        addTab("发起工单", MaintainFragment.class, myBundle, R.string.icon_font_tab_maintain);
        addTab("站点巡查", PatrolFragment.class, myBundle, R.string.icon_font_tab_patrol);
        onPostCreate();
        checkVersion();
    }

    private void checkVersion() {
        Request.startRequest(new BaseParam(), ServiceMap.CHECK_VERSION, mHandler);
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
        if (TextUtils.isEmpty(UCUtils.getInstance().getUserInfo().token)) {
            qStartActivity(LoginActivity.class);
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!onBackPressedWithFragment()) {
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

    boolean onBackPressedWithFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
            if (!ArrayUtils.isEmpty(fragments)) {
                for (Fragment fragment : fragments) {
                    if (fragment == null) {
                        return false;
                    }
//                    if (fragment.isVisible()) {
//                        FragmentOnBackListener backListener = (FragmentOnBackListener) fragment;
//                        if (backListener.onBackPressed()) {
//                            return true;
//                        }
//                    }
                }
            }
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
