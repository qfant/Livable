package com.qfant.wuye.jpush;

import android.content.Context;
import android.os.Bundle;

import co.bxvip.sdk.jpush.BxJPushReceiverImpl;

public class JPushReceiver extends BxJPushReceiverImpl {
    @Override
    public boolean isGoToReleaseMain() {
        return false;
    }

    @Override
    public void toGoYouMain(Context context, Bundle bundle) {

    }
}

