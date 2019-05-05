package com.framework.app;


import android.os.Build;

import com.qfant.wuye.BuildConfig;


/**
 * 常量
 *
 * @author zexu
 */
public class AppConstants {

//	public final static String COMMON_URL = "http://123.59.33.179:8080/bang2/customer";

    public static final boolean DEBUG = BuildConfig.DEBUG;
//    public final static String COMMON_URL = DEBUG ? "http://139.224.113.86:8081/yjwy/customer" : "http://47.96.183.3:8080/yjwy/customer";
    public final static String COMMON_URL =  "http://47.96.183.3:8080/yjwy/customer";

    //    e1c8d16f2c0be7fc5b2decdb724423df
    public static final String APP_ID = "wxb4f5998bd885e481";
    public static final String AppSgin = "3e1157097a95a2f93c0548bdaf7fbcca";
}
