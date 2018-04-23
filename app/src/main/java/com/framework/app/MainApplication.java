/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.igexin.sdk.PushManager;
import com.qfant.wuye.push.IntentService;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainApplication extends MultiDexApplication {

    public static Context applicationContext;
    private static MainApplication instance;
    private static Typeface iconFont;
    public String versionName;
    public static int versionCode;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        try {
            String pkName = this.getPackageName();
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(
                    pkName, 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
// 将该app注册到微信
        msgApi.registerApp(AppConstants.APP_ID);
        PushManager.getInstance().initialize(this, com.qfant.wuye.push.PushService.class);
        PushManager.getInstance().registerPushIntentService(this, IntentService.class);

        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
        CrashReport.initCrashReport(context, "174237abbe", false, strategy);
// 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
// CrashReport.initCrashReport(context, strategy);

    }

    public static MainApplication getInstance() {
        return instance;
    }


    public static Typeface getIconFont() {

        if (iconFont == null) {
            synchronized (Typeface.class) {
                if (iconFont == null) {
                    iconFont = Typeface.createFromAsset(applicationContext.getAssets(), "iconfont/iconfont.ttf");
                }
            }
        }
        return iconFont;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
