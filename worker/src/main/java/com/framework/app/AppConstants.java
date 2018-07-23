package com.framework.app;


import com.haolb.client.BuildConfig;

/**
 * 常量
 *
 * @author zexu
 */
public class AppConstants {

    public static final boolean DEBUG = false;
    public final static String COMMON_URL = DEBUG ? "http://139.224.113.86:8081/yjwy/worker" : "http://47.96.183.3:8080/yjwy/worker";
    public final static String UC_URL = DEBUG ? "http://139.224.113.86:8081/yjwy/customer" : "http://47.96.183.3:8080/yjwy/customer";

}
