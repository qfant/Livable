package com.page.uc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.BusinessUtils;
import com.framework.utils.QLog;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.page.home.activity.MainActivity;
import com.qfant.wuye.R;

import com.page.uc.bean.LoginParam;
import com.page.uc.bean.LoginResult;
import com.page.uc.bean.LoginSendCodeParam;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/6/1.
 */

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.tiet_username)
    TextInputEditText tietUsername;
    @BindView(R.id.til_username)
    TextInputLayout tilUsername;
    @BindView(R.id.tiet_password)
    TextInputEditText tietPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.text_send_code)
    TextView textSendCode;
    @BindView(R.id.text_login)
    TextView textLogin;
    @BindView(R.id.tv_rig)
    TextView tvRig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_login_layout);
        ButterKnife.bind(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction("xxxxx");
        this.registerReceiver(imReceiver, intentFilter);
    }
    private BroadcastReceiver imReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            QLog.d(TAG, "action:" + action);
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                QLog.d(TAG,"nihao网络状态发生变化!!");
            }
        }
    };

    @OnClick({R.id.text_send_code, R.id.text_login, R.id.tv_rig})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_send_code:
                sendCode();
                break;
            case R.id.text_login:
                sendLogin();
                break;
            case R.id.tv_rig:
                qStartActivity(SelectComActivity.class);
                break;
        }
    }


    public static class LinkParam extends BaseParam {
        public int type = 1;
    }

    public void getLink() {
        Request.startRequest(new LinkParam(), ServiceMap.getLinks, mHandler);
    }

    public void checkUpdate() {
        Request.startRequest(new UpdateParam(), ServiceMap.checkVersion, mHandler);
    }

    private void sendLogin() {

        String phone = tilUsername.getEditText().getText().toString();
        String psw = tilPassword.getEditText().getText().toString();
        if (TextUtils.isEmpty(psw)) {
            showToast("验证码有误");
        }

        LoginParam loginParam = new LoginParam();
        loginParam.code = psw;
        loginParam.phone = phone;
        loginParam.clientid = PushManager.getInstance().getClientid(getContext());
        Request.startRequest(loginParam, ServiceMap.customerLogin, mHandler, Request.RequestFeature.BLOCK);
    }

    private void sendCode() {
        String phone = tilUsername.getEditText().getText().toString();
        if (!BusinessUtils.checkPhoneNumber(phone)) {
            showToast("你输入的手机号有误");
            return;
        }
        LoginSendCodeParam loginSendCodeParam = new LoginSendCodeParam();
        loginSendCodeParam.phone = phone;
        Request.startRequest(loginSendCodeParam, ServiceMap.getVerificationCode, mHandler, Request.RequestFeature.BLOCK);
    }

    public void buildSendCodeText() {
        textSendCode.setEnabled(false);
        textLogin.setEnabled(true);

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textSendCode.setText(millisUntilFinished / 1000 + "S");
            }

            @Override
            public void onFinish() {
                textSendCode.setText("重新发送");
                textSendCode.setEnabled(true);
            }
        }.start();
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getVerificationCode) {
            if (param.result.bstatus.code == 0) {
                buildSendCodeText();
            }
        } else if (param.key == ServiceMap.customerLogin) {
            if (param.result.bstatus.code == 0) {
                //登录成功
                LoginResult result = (LoginResult) param.result;
                UCUtils.getInstance().saveUserInfo(result.data);
                PushManager.getInstance().bindAlias(getContext(), result.data.phone);
                Tag tag = new Tag();
                tag.setName("DISTRICT_" + UCUtils.getInstance().getUserInfo().districtid);
                int tagID = PushManager.getInstance().setTag(getContext(), new Tag[]{tag}, "DISTRICT");
                QLog.e("qushucheng", "个推设置tag ：：： " + tagID);
                Intent intent = new Intent();
                intent.setClass(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                qStartActivity(intent);

                finish();
            } else {
                showToast(param.result.bstatus.des);
            }

        }
        return super.onMsgSearchComplete(param);
    }


}
