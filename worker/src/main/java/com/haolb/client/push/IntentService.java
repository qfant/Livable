package com.haolb.client.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.framework.utils.QLog;
import com.haolb.client.R;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

//import com.framework.utils.viewutils.CheckUtils;
//import com.haolb.worker.R;
//import com.taobao.weex.devtools.common.LogUtil;

/**
 * Created by shucheng.qu on 2017/11/1.
 */

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */

public class IntentService extends GTIntentService {
    @Override
    public void onReceiveServicePid(Context context, int i) {

    }

    @Override
    public void onReceiveClientId(Context context, String s) {

    }

    /**
     * 处理透传消息
     *
     * @param context
     * @param gtTransmitMessage
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {

        byte[] payload = gtTransmitMessage.getPayload();
        if (payload != null && payload.length > 0) {
            String payloadStr = new String(payload);
            QLog.d("qushucheng", payloadStr);
            JSONObject extras = PushUtils.getJsonObject(payloadStr);
            String id = PushUtils.getJsonString(extras, GPushReceiver.KEY_ID);
            String url = PushUtils.getJsonString(extras, GPushReceiver.KEY_URL);
            String title = PushUtils.getJsonString(extras, GPushReceiver.KEY_TITLE);
            String content = PushUtils.getJsonString(extras, GPushReceiver.KEY_CONTENT);
            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(content)) {
                //不弹出通知了，因为没有内容可以展示了
            } else {
                Intent intent = new Intent(GPushReceiver.ACTION_NOTIFY);
                if (!TextUtils.isEmpty(id)) {
                    intent.putExtra(GPushReceiver.KEY_ID, id);
                }
                if (!TextUtils.isEmpty(url)) {
                    intent.putExtra(GPushReceiver.KEY_URL, url);
                }
                if (!TextUtils.isEmpty(title)) {
                    intent.putExtra(GPushReceiver.KEY_TITLE, title);
                }
                intent.putExtra(GPushReceiver.KEY_UE_DATA, payloadStr);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                int rcode = (int) System.currentTimeMillis();
//                 设置icon
                builder.setSmallIcon(Build.VERSION.SDK_INT > 18 ? R.drawable.push_small : R.drawable.push)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.push))
                        // 设置title
                        .setContentTitle(title)
                        // 设置内容
                        .setContentText(content)
                        .setTicker(content)
                        .setPriority(Integer.MAX_VALUE)
                        .setVibrate(new long[]{0, 200, 200, 300})
                        // 设置自动取消
                        .setAutoCancel(true)
//                        .setSound(Uri.parse("android.resource://" + QApplication.getContext().getPackageName() + "/" + R.raw.push_ring))
                        .setShowWhen(true)
                        // ticker
                        .setTicker(title)
                        // 设置触发事件
                        .setContentIntent(PendingIntent.getBroadcast(context, rcode, intent, PendingIntent.FLAG_ONE_SHOT));
                Notification notification = builder.build();
                notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;
                NotificationManager systemService = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                assert systemService != null;
                systemService.notify(rcode, notification);
            }
        }
    }

    /**
     * cid 离线上线通知
     *
     * @param context
     * @param b
     */
    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

        QLog.d("离线上线通知  :::  " + b);
    }

    /**
     * 各种事件处理回执
     *
     * @param context
     * @param gtCmdMessage
     */
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }
}
