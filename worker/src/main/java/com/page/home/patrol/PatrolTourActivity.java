package com.page.home.patrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.activity.MainActivity;
import com.page.home.patrol.PatrolTourResult.TourItem;

import java.util.ArrayList;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolTourActivity extends BaseActivity {
    private static final String TAG = PatrolTourActivity.class.getSimpleName();
    private ListView listView;
    private PatrolTourAdapter adapter;
    private LocationClient mLocationClient;
    private MyBDLocationListener mBDLocationListener;
    private double latitude;
    private double longitude;
    private String qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_patrol_list_layout);
        setTitleBar("巡更详情", true);
        qrcode = myBundle.getString("qrcode");
        qrcode = "015E8B83AE6EA4A4";
        listView = (ListView) findViewById(R.id.list);
        adapter = new PatrolTourAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        requestData();
        // 声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);
        startLocation();
    }

    /**
     * 获得所在位置经纬度及详细地址
     */
    public void startLocation() {
        // 声明定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // 取消监听函数
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
    }

    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                String address = location.getAddrStr();
                Log.i(TAG, "address:" + address + " latitude:" + latitude
                        + " longitude:" + longitude + "---");
                if (mLocationClient.isStarted()) {
                    // 获得位置之后停止定位
                    mLocationClient.stop();
                }
            }
        }
    }

    private void requestData() {
        PatrolTourParam patrolListParam = new PatrolTourParam();
        patrolListParam.qrcode = qrcode;
        Request.startRequest(patrolListParam, ServiceMap.getPlaceDetail, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        super.onItemClick(adapterView, view, i, l);
        TourItem item = adapter.getItem(i);
        PatrolSubmitPatrolParam patrolListParam = new PatrolSubmitPatrolParam();
        patrolListParam.qrcode = qrcode;
        patrolListParam.longitude = longitude;
        patrolListParam.latitude = latitude;
        Request.startRequest(patrolListParam, ServiceMap.submitPatrol, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.getPlaceDetail) {
            if (param.result.bstatus.code == 0) {
                PatrolTourResult result = (PatrolTourResult) param.result;
                result.data.placeResult = new ArrayList<>();
                TourItem tourItem = new TourItem();
                result.data.placeResult.add(tourItem);
                tourItem.name = result.data.name;
                tourItem.serialnum = result.data.serialnum;
                adapter.setData(result.data.placeResult);
            }
        } else if (param.key == ServiceMap.submitPatrol) {

            if (param.result.bstatus.code == 0) {
                new AlertDialog.Builder(this).setTitle("").setMessage(param.result.bstatus.des)
                        .setNegativeButton("返回首页", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                qBackToActivity(MainActivity.class, null);
                            }
                        }).show();

            }else {
                showToast(param.result.bstatus.des);
            }
        }
        return super.onMsgSearchComplete(param);
    }

}

