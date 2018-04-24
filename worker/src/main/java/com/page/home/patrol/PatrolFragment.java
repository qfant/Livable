package com.page.home.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.framework.activity.BaseFragment;
import com.haolb.client.R;
import com.page.uc.UserInfoActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by chenxi.cui on 2018/4/23.
 */

public class PatrolFragment extends BaseFragment {
    public static final int REQUEST_CODE = 0x01;
    private View llScan;
    private View llOperation;
    private View llHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.pub_fragment_patrol_layout);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitleBar("站点巡查", false, "个人中心", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qStartActivity(UserInfoActivity.class);
            }
        });
        llScan = getView().findViewById(R.id.ll_scan);
        llOperation = getView().findViewById(R.id.ll_operation);
        llHistory = getView().findViewById(R.id.ll_history);
        llScan.setOnClickListener(this);
        llOperation.setOnClickListener(this);
        llHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.equals(llScan)) {
            Intent intent = new Intent(getContext(), CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (v.equals(llOperation)) {
            qStartActivity(PatrolParentListActivity.class);
        } else if (v.equals(llHistory)) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                    qStartActivity(PatrolListActivity.class);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
