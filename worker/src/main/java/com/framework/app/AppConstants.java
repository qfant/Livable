package com.framework.app;


import com.haolb.client.BuildConfig;

/**
 * 常量
 *
 * @author zexu
 */
public class AppConstants {

    //	public final static String COMMON_URL = "http://123.59.33.179:8080/bang2/customer";
//    public final static String COMMON_URL = "http://112.123.63.69:8081/yjwy/worker";
//    public final static String COMMON_URL = "http://118.31.1.26:8081/yjwy/worker";
//    public final static String COMMON_URL = "http://115.28.235.86:8081/yjwy/customer";

//    public final static String COMMON_URL = "http://47.96.183.3:8080/yjwy/worker";
    public final static String COMMON_URL = "http://139.224.113.86:8081/yjwy/worker";


    public static final boolean DEBUG = true;

    public final static String UC_URL = DEBUG ? "http://139.224.113.86:8081/yjwy/customer" : "http://47.96.183.3:8080/yjwy/customer";

}
