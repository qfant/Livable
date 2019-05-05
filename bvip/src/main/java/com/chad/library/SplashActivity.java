package com.chad.library;

import android.os.Bundle;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import co.bxvip.android.commonlib.http.ext.Ku;
import co.bxvip.sdk.ui.BxStartActivityImpl;
import okhttp3.OkHttpClient;

public class SplashActivity extends BxStartActivityImpl {

    private boolean isStartActivity;

    @Override
    public void toYourMainActivity() {
        if (isStartActivity) {
            finish();
            return;
        }
        isStartActivity = true;
//        boolean startActivity = RePlugin.startActivity(this,
//                RePlugin.createIntent("com.xiaoyong.gjp",
//                        "io.dcloud.PandoraEntryActivity"));
//        if (startActivity) {
//            finish();
//        } else {
//            Toast.makeText(this, "启动失败", Toast.LENGTH_LONG).show();
//        }

//        startActivity(new Intent(this, WebActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ku.Companion.setMaxTryCount(1);
        OkHttpClient build = Ku.Companion.getKClient().newBuilder().connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS).build();
        Ku.Companion.setClient(build);

    }

    @Override
    public boolean hideLoadingShow() {
//        log("hideLoadingShow=" + super.hideLoadingShow());
        return false;
    }

    @Override
    public boolean hideVersionShow() {
//        log("hideVersionShow=" + super.hideVersionShow());
        return true;
    }

    @Override
    public boolean noNetworkJumpYourActivity() {
        return super.noNetworkJumpYourActivity();
    }

    @Override
    public boolean notCheckPermission() {
        log("notCheckPermission=" + super.notCheckPermission());
        return super.notCheckPermission();
    }

    void log(String msg) {
        Log.d("SlashActivity", msg);
    }

}

