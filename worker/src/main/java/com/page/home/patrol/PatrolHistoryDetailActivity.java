package com.page.home.patrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolHistoryDetailActivity extends BaseActivity {
    private PatrolListResult.PatrolItem patrolItem;
    private TextView textTitle;
    private LinearLayout llContain;
    private Button btnSubmit;
    private List<PatrolDetailResult.CheckItem> mCheckItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_patrol_detail_layout);
        setTitleBar("巡查项目详情", true);
        patrolItem = (PatrolListResult.PatrolItem) myBundle.getSerializable(PatrolListResult.PatrolItem.TAG);
        textTitle = (TextView) findViewById(R.id.text_title);
        llContain = (LinearLayout) findViewById(R.id.ll_contain);
        textTitle.setText(patrolItem.name + "检查项目");
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        requestData();
    }

    private void requestData() {
        PatrolDetailParam patrolListParam = new PatrolDetailParam();
        patrolListParam.checkId = patrolItem.id;
        Request.startRequest(patrolListParam, ServiceMap.getProjectCheckItems, mHandler, Request.RequestFeature.BLOCK);
    }

    private void submit() {
        PatrolCheckParam patrolCheckParam = new PatrolCheckParam();
        patrolCheckParam.checkId = patrolItem.id;
//        patrolCheckParam.placeId = patrolItem.id;
        patrolCheckParam.itemValues = new ArrayList<>();
        boolean isCanSubmit = true;
        for (PatrolDetailResult.CheckItem item : mCheckItemsList) {
            PatrolCheckParam.CheckParam checkParam = new PatrolCheckParam.CheckParam();
            checkParam.id = item.id;
            checkParam.value = item.isCheck;
            patrolCheckParam.itemValues.add(checkParam);
            if (item.isCheck) {
                checkParam.remark = item.remark;
            }
            isCanSubmit = item.isCheck || (!item.isCheck && !TextUtils.isEmpty(item.remark));
            if (!isCanSubmit) {
                break;
            }
        }
        if (!isCanSubmit) {
            Toast.makeText(this, "请完成所有项目检查", Toast.LENGTH_LONG).show();
            return;
        }

        Request.startRequest(patrolCheckParam, ServiceMap.submitCheck, mHandler, Request.RequestFeature.BLOCK);
    }

    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.getProjectCheckItems) {
            if (param.result.bstatus.code == 0) {
                PatrolDetailResult result = (PatrolDetailResult) param.result;
                setData(result.data.checkItemsList);
            }
        } else if (param.key == ServiceMap.submitCheck) {
//            showToast(param.result.bstatus.des);
            if (param.result.bstatus.code == 0) {
                new AlertDialog.Builder(this).setTitle("").setMessage(param.result.bstatus.des)
                        .setNegativeButton("返回首页", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                qBackToActivity(MainActivity.class, null);
                            }
                        }).show();

            }
        }
        return super.onMsgSearchComplete(param);
    }

    private void setData(List<PatrolDetailResult.CheckItem> checkItemsList) {
        this.mCheckItemsList = checkItemsList;
        llContain.removeAllViews();
        for (final PatrolDetailResult.CheckItem item : checkItemsList) {
            View view = LinearLayout.inflate(this, R.layout.pub_patrol_detail_item_view, null);
            TextView textName = (TextView) view.findViewById(R.id.text_name);
            SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.switch_compat);
            final EditText editText = (EditText) view.findViewById(R.id.edit_compat);
            editText.setVisibility(View.VISIBLE);
            editText.setHint("备注");
            item.isCheck = false;
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        editText.setVisibility(View.VISIBLE);
                    } else {
                        editText.setVisibility(View.GONE);
                    }
                    item.isCheck = isChecked;
                }
            });
            switchCompat.setChecked(false);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    item.remark = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            textName.setText(item.name);
            llContain.addView(view);
        }
    }

}