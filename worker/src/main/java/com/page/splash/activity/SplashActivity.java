package com.page.splash.activity;

import android.app.ApplicationErrorReport;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.framework.activity.BaseActivity;
import com.haolb.client.push.PushService;
import com.igexin.sdk.PushManager;
import com.page.home.activity.MainActivity;
import com.page.login.UCUtils;
import com.page.login.activity.LoginActivity;
import static com.igexin.sdk.GTServiceManager.context;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PushManager.getInstance().initialize(getContext(), PushService.class);
        }catch (Exception e){
            e.printStackTrace();
        }
//        qStartActivity(LoginActivity.class);

        startMainActivity();
    }

    private void startMainActivity() {
        if (UCUtils.getInstance().isLogin()) {
            Intent intent = new Intent();
            intent.setClass(getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            qStartActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setClass(getContext(), LoginActivity.class);
            qStartActivity(intent);
        }
        finish();
    }
}
