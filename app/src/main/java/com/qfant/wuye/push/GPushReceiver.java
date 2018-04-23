/*
 * Copyright (C) 2013 Qunar.Inc All rights reserved.
 */
package com.qfant.wuye.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.igexin.sdk.PushConsts;
import com.page.home.activity.MainActivity;
import com.page.home.activity.WebActivity;


/**
 * 推送流程简介: <hr> step.1: 个推sdk获取到clientId (个推有自己的生成条件和生成规则，详情查询个推文档)<br> step.2: Qunar使用clientId和gid进行绑定，发送给后端<br>
 * step.3: 业务服务器通过gid推送消息给hotdog,hotdog使用gid找到对应的clientId，推送至客户端<br> step.4: 客户端收到push的消息体(title,content,id),并弹出通知.<br>
 * step.5: 用户点击通知，进入 {@link },发送请求.<br> step.6: 请求成功，进入scheme对应的界面，请求失败，进入首页。<br>
 */
public class GPushReceiver extends BroadcastReceiver {

    private static final String TAG = GPushReceiver.class.getSimpleName();

    public static final String ACTION_NOTIFY = "com.wuye.notify";

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_TYPE = "type";
    public static final String KEY_UE_DATA = "ue_data";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (ACTION_NOTIFY.equals(action)) {
            String UEData = PushUtils.getStringByIntent(intent, KEY_UE_DATA);
            String id = PushUtils.getStringByIntent(intent, KEY_ID);
            String url = PushUtils.getStringByIntent(intent, KEY_URL);
            String title = PushUtils.getStringByIntent(intent, KEY_TITLE);

            if (!TextUtils.isEmpty(url)) {
                //当url不为空的时候，直接跳转, proType 0 是jump proType 1 是wap
                dealUri(context, getCodeFromUri(url), url,title);
            }
        }
    }

    /**
     * return 0 是native页面 return 1 是web页面
     */
    public int getCodeFromUri(String url) {
        try {
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                return 1;
            } else {
                return 0;
            }
        } catch (Throwable error) {
            if (url.startsWith("http") || url.startsWith("https")) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void dealUri(Context context, int type, String url, String title) {
        //proType 0 jump - native
        Intent newIntent;
        if (type == 0) {
            try {
                newIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                /**
                 * 这里为什么要使用 {@link Intent.FLAG_ACTIVITY_CLEAR_TOP}？
                 * 因为如果不使用这个标识，当你在应用内时，点击通知不会有任何反映，因为startActivity并没有真正的去启动这个Activity.
                 */
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.putExtra("noQuitConfirm", false);
                context.startActivity(newIntent);
            } catch (Exception e) {
                newIntent = new Intent(context, MainActivity.class);
                /**
                 * 这里为什么要使用 {@link Intent.FLAG_ACTIVITY_CLEAR_TOP}？
                 * 因为如果不使用这个标识，当你在应用内时，点击通知不会有任何反映，因为startActivity并没有真正的去启动这个Activity.
                 */
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(newIntent);
            }
            //proType 1 wap - webActivity
        } else if (type == 1) {
            try {
                newIntent = new Intent(context, WebActivity.class);
                /**
                 * 这里为什么要使用 {@link Intent.FLAG_ACTIVITY_CLEAR_TOP}？
                 * 因为如果不使用这个标识，当你在应用内时，点击通知不会有任何反映，因为startActivity并没有真正的去启动这个Activity.
                 */
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.putExtra(WebActivity.URL, url);
                newIntent.putExtra(WebActivity.TITLE, title);
                context.startActivity(newIntent);
            } catch (Exception e) {
                newIntent = new Intent(context, MainActivity.class);
                /**
                 * 这里为什么要使用 {@link Intent.FLAG_ACTIVITY_CLEAR_TOP}？
                 * 因为如果不使用这个标识，当你在应用内时，点击通知不会有任何反映，因为startActivity并没有真正的去启动这个Activity.
                 */
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(newIntent);
            }
        }
    }
}
