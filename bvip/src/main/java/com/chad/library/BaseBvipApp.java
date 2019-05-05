package com.chad.library;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.qfant.client.R;
import co.bxvip.sdk.BuildConfig;
import co.bxvip.sdk.BxRePluginAppLicationMakeImpl;

abstract public class BaseBvipApp extends BxRePluginAppLicationMakeImpl {

    @Override
    public void initJPushYouNeed() {
//        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);                         // 初始化 JPush
//        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(this, R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
//        builder.layoutIconDrawable = R.mipmap.logo;
//        builder.developerArg0 = "developerArg2";
//        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    @Override
    public void initRePluginYourNeed() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
