package com.page.home.patrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.page.detail.AddView;
import com.page.home.activity.MainActivity;
import com.page.home.patrol.PatrolCheckParam.CheckParam;
import com.page.home.patrol.PatrolDetailResult.CheckItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolDetailActivity extends BaseActivity {
    private PatrolListResult.PatrolItem patrolItem;
    private TextView textTitle;
    private LinearLayout llContain;
    private Button btnSubmit;
    private List<CheckItem> mCheckItemsList;
    private AddView addView;
    private PatrolDetailResult result;
    @BindView(R.id.repair_text_type_select)
    TextView repairType;
    private int repairChooseValue = -1;
    private int isException = 1;//是否有异常 1 否
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_patrol_detail_layout);
//        setTitleBar("巡查详情", true);
        patrolItem = (PatrolListResult.PatrolItem) myBundle.getSerializable(PatrolListResult.PatrolItem.TAG);
        textTitle = (TextView) findViewById(R.id.text_title);
        addView = (AddView) findViewById(R.id.addView);
        llContain = (LinearLayout) findViewById(R.id.ll_contain);
        setTitleBar(patrolItem.name + "检查项目", true);
        textTitle.setVisibility(View.GONE);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        requestData();
        ButterKnife.bind(this);
        addView.setAddNumber(3);
    }

    private void requestData() {
        PatrolDetailParam patrolListParam = new PatrolDetailParam();
        patrolListParam.checkId = patrolItem.id;
        Request.startRequest(patrolListParam, ServiceMap.getProjectCheckItems, mHandler, Request.RequestFeature.BLOCK);
    }

    private void submit() {
        if (result == null || result.data == null || result.data.checkItemsList == null) {
            return;
        }
        if (isException==0 && repairChooseValue == -1 ) {
            showToast("选择维修类型");
            return;
        }
        PatrolCheckParam patrolCheckParam = new PatrolCheckParam();
        patrolCheckParam.checkId = result.data.checkId;
        patrolCheckParam.placeId = result.data.placeId;
        patrolCheckParam.repairChooseValue=repairChooseValue;
//        patrolCheckParam.placeId = patrolItem.id;
        patrolCheckParam.itemValues = new ArrayList<>();
        boolean isCanSubmit = true;
        for (CheckItem item : mCheckItemsList) {
            CheckParam checkParam = new CheckParam();
            checkParam.id = item.id;
            checkParam.value = item.isCheck ? 1 : 0;
            patrolCheckParam.itemValues.add(checkParam);
            if (!item.isCheck) {
                checkParam.remark = item.remark;
            }
            isCanSubmit = item.isCheck || (!item.isCheck && !TextUtils.isEmpty(item.remark));
            if (!isCanSubmit) {
                break;
            }
        }
        String[] imageUrls = addView.getImageUrls();
        if (imageUrls != null && imageUrls.length > 0 && TextUtils.isEmpty(imageUrls[0])) {
            showToast("请拍照上传");
            return;
        }
        patrolCheckParam.pic = imageUrls[0];
        patrolCheckParam.pic2 = imageUrls[1];
        patrolCheckParam.pic3 = imageUrls[2];
        if (!isCanSubmit) {
            Toast.makeText(this, "请完成所有项目检查", Toast.LENGTH_LONG).show();
            return;
        }

        Request.startRequest(patrolCheckParam, ServiceMap.submitCheck, mHandler, Request.RequestFeature.BLOCK);
    }

    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.uploadPic) {
            addView.onMsgSearchComplete(param);
        } else if (param.key == ServiceMap.getProjectCheckItems) {
            result = (PatrolDetailResult) param.result;
            if (param.result.bstatus.code == 0) {
                if (result.data.checkItemsList != null) {
                    setData(result.data.checkItemsList);
                } else {
                    showToast(result.bstatus.des);
                }
            }
        } else if (param.key == ServiceMap.submitCheck) {
            if (param.result.bstatus.code == 0) {
                new AlertDialog.Builder(this).setTitle("").setMessage(param.result.bstatus.des)
                        .setNegativeButton("返回首页", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                qBackToActivity(MainActivity.class, null);
                            }
                        }).show();

            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return super.onMsgSearchComplete(param);
    }

    private void setData(List<CheckItem> checkItemsList) {
        this.mCheckItemsList = checkItemsList;
        llContain.removeAllViews();
        for (final CheckItem item : checkItemsList) {
            View view = LinearLayout.inflate(this, R.layout.pub_patrol_detail_item_view, null);
            TextView textName = (TextView) view.findViewById(R.id.text_name);
            final CheckBox switchCompat = (CheckBox) view.findViewById(R.id.switch_compat);
            final CheckBox switchCompat1 = (CheckBox) view.findViewById(R.id.switch_compat1);
            final EditText editText = (EditText) view.findViewById(R.id.edit_compat);
            editText.setVisibility(View.VISIBLE);
            editText.setHint("异常备注");
            item.isCheck = false;
            editText.setVisibility(View.GONE);
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        editText.setVisibility(View.GONE);
                    } else {
                        isException=0;
                        editText.setVisibility(View.VISIBLE);
                    }
                    item.isCheck = isChecked;
                    switchCompat1.setChecked(!isChecked);
                }
            });
            switchCompat1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    switchCompat.setChecked(!isChecked);
                    if (isChecked) {
                        isException=0;
                        editText.setVisibility(View.VISIBLE);
                    } else {
                        editText.setVisibility(View.GONE);
                    }
                }
            });

//            switchCompat.setChecked(true);
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

            textName.setTextColor((item.color!=null && item.color!="")?Color.parseColor(item.color):Color.parseColor("#ff0000"));
            llContain.addView(view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addView.onActivityResult(requestCode, resultCode, data);
    }
    @OnClick({R.id.repair_text_type_select})
    public void onClick(View view) {
        final String[] arr = {"居家报修", "小区报修", "小区卫生", "小区绿化", "小区安全"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                repairType.setText(arr[which]);
                repairChooseValue = which + 1;
            }
        });
        builder.show();

    }
}