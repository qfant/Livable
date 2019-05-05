package com.page.splash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.igexin.sdk.PushManager;
import com.page.home.activity.MainActivity;

import java.util.concurrent.TimeUnit;

import co.bxvip.android.commonlib.http.ext.Ku;
import co.bxvip.sdk.ui.BxStartActivityImpl;
import okhttp3.OkHttpClient;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class SplashActivity1 extends BxStartActivityImpl {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ku.Companion.setMaxTryCount(1);
        OkHttpClient build = Ku.Companion.getKClient().newBuilder().connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS).build();
        Ku.Companion.setClient(build);
        PushManager.getInstance().initialize(this.getApplication(), com.qfant.wuye.push.PushService.class);
    }

    @Override
    public void toYourMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
