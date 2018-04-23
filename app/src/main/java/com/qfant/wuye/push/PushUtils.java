package com.qfant.wuye.push;

import android.content.Intent;

import com.alibaba.fastjson.JSONObject;
import com.framework.utils.QLog;

/**
 * Created by chaos flight_on 14-8-25.
 */
public class PushUtils {

	public static String getJsonString(JSONObject data, String key) {
		try {
			return data.getString(key);
		} catch (Throwable t) {
			QLog.e(t);
			return null;
		}
	}

	public static int getJsonInt(JSONObject data, String key) {
		try {
			return data.getInteger(key);
		} catch (Throwable t) {
			QLog.e(t);
			return -1;
		}
	}

	public static JSONObject getJsonObject(String json) {
		try {
			return JSONObject.parseObject(json);
		} catch (Throwable t) {
			QLog.e(t);
			return null;
		}
	}

	public static String getStringByIntent(Intent intent, String key) {
		try {
			return intent.getStringExtra(key);
		} catch (Throwable t) {
			QLog.e(t);
			return null;
		}
	}
}
