package com.page.home.maintain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.utils.BusinessUtils;
import com.framework.view.ListDialog;
import com.haolb.client.R;
import com.page.detail.AddView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class MaintainSendActivity extends BaseActivity {

    @BindView(R.id.addView)
    AddView addView;
    @BindView(R.id.et_intro)
    EditText etIntro;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.text_xiaoqu)
    TextView tvXiaoqu;
    @BindView(R.id.text_type)
    TextView tvType;
    private int weixiType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_applyfor_layout);
        ButterKnife.bind(this);
        setTitleBar("申请维修", true);
        addView.setAddNumber(1);
    }

    private void startRequest() {

        String[] imageUrls = addView.getImageUrls();
        String intro = etIntro.getText().toString();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String xiaoqu = tvXiaoqu.getText().toString().trim();

        if (weixiType == -1) {
            showToast("选择维修类型");
            return;
        }
        if (TextUtils.isEmpty(intro)) {
            showToast("问题描述不能为空");
            return;
        }
        if (TextUtils.isEmpty(xiaoqu)) {
            showToast("小区不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("联系电话不能为空");
            return;
        }
        if (!BusinessUtils.checkPhoneNumber(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            showToast("详细地址不能为空");
            return;
        }

        ApplyForParam param = new ApplyForParam();
        param.pic = imageUrls[0];
        param.intro = intro;
        param.repairtype = weixiType;
        param.phone = phone;
        param.address = address;
        Request.startRequest(param, ServiceMap.submitRepair, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.uploadPic) {
            addView.onMsgSearchComplete(param);
        } else if (param.key == ServiceMap.submitRepair) {
            if (param.result.bstatus.code == 0) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("refresh", true);
                qBackForResult(RESULT_OK, bundle);
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.getDistricts) {
            DistrictsResult result = (DistrictsResult) param.result;
            List<String> list = new ArrayList<>();
            list.add("xxx");
            list.add("xxx");
            list.add("xxx");
            setDistricts(list);
        }
        return false;
    }

    private void setDistricts(final List<String> list) {
//        ListDialog listDialig = new ListDialog(getContext(), R.style.list_dialog_style);
//        listDialig.create();
//        listDialig.setListView(list);
//        listDialig.show();
//        listDialig.setOnCellClick(new ListDialog.OnCellClick() {
//            @Override
//            public void onCellClick(String baseParam, int pos) {
//                tvXiaoqu.setText(baseParam);
//            }
//        });
        int size = list.size();
        final String[] array = (String[]) list.toArray(new String[size]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvXiaoqu.setText(array[which]);
            }
        });
        builder.show();
    }

    @OnClick({R.id.tv_commit, R.id.text_type, R.id.text_xiaoqu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                startRequest();
                break;
            case R.id.text_type:
                final String[] arr = {"居家报修", "小区报修", "小区卫生", "小区绿化", "小区安全"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvType.setText(arr[which]);
                        weixiType = which + 1;
                    }
                });
                builder.show();
                break;
            case R.id.text_xiaoqu:
                reqXQ();
                List<String> list = new ArrayList<>();
                list.add("xxx");
                list.add("xxx");
                list.add("xxx");
                setDistricts(list);
                break;
        }

    }

    public void reqXQ() {
        Request.startRequest(new BaseParam(), ServiceMap.CHECK_VERSION, mHandler, Request.RequestFeature.BLOCK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addView.onActivityResult(requestCode, resultCode, data);
    }
}
