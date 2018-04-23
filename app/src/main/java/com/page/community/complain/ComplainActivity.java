package com.page.community.complain;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.html.ZoomImageView;
import com.framework.utils.imageload.ImageLoad;
import com.framework.utils.viewutils.ViewUtils;
import com.framework.view.AddView;
import com.page.community.complain.model.ComplainParam;
import com.page.community.serve.model.ComplainResult.Data.ComplainList;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/11/6.
 */

public class ComplainActivity extends BaseActivity {


    public static String URL = "url";
    public static String ID = "id";

    @BindView(R.id.addView)
    AddView addView;
    @BindView(R.id.et_complain)
    EditText etComplain;
    @BindView(R.id.ll_complain)
    LinearLayout llComplain;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.ll_reply)
    LinearLayout llReply;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    private ComplainList data;

    private PopupWindow popupWindow;
    //需要放大的图片
    private ZoomImageView tecent_chat_image;
    //加载中的进度条
    private ProgressBar image_scale_progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_complain_layout);
        ButterKnife.bind(this);
        setTitleBar("投诉", true);
        data = (ComplainList) myBundle.getSerializable("data");
        if (data != null) {
            updataView(data);
        }
        addView.setAddNumber(new String[]{(data != null ? data.pic : "")});
        initPopWindow();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putSerializable("data", data);
    }

    private void initPopWindow() {
        View popView = LayoutInflater.from(getContext()).inflate(R.layout.pub_zoom_popwindow_layout, null);
        tecent_chat_image = (ZoomImageView) popView.findViewById(R.id.image_scale_image);
        image_scale_progress = (ProgressBar) popView.findViewById(R.id.image_scale_progress);

        popView.findViewById(R.id.image_scale_rll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x50000000);
        popupWindow.setBackgroundDrawable(dw);
    }

    private void updataView(final ComplainList data) {
        addView.setClickable(false);
        addView.setVisibility(View.GONE);
        ivImage.setVisibility(View.VISIBLE);
        ImageLoad.loadPlaceholder(getContext(), data.pic, ivImage);
        tvCommit.setVisibility(View.GONE);
        etComplain.setEnabled(false);
        etComplain.setText(TextUtils.isEmpty(data.content) ? " " : data.content);
        ViewUtils.setOrGone(llReply, !TextUtils.isEmpty(data.reply));
        tvReply.setText(data.reply);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_scale_progress.setVisibility(View.GONE);
                popupWindow.showAtLocation(addView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                ImageLoad.loadPlaceholder(getContext(), data.pic, tecent_chat_image);
            }
        });
    }

    private void startRequest() {
        String complain = etComplain.getText().toString().trim();
        if (TextUtils.isEmpty(complain)) {
            showToast("投诉内容不能为空~");
            return;
        }
        String[] imageUrls = addView.getImageUrls();
        if (TextUtils.isEmpty(imageUrls[0])) {
            showToast("请上传照片~");
            return;
        }
        ComplainParam param = new ComplainParam();
        param.content = complain;
        param.pic = imageUrls[0];
        Request.startRequest(param, ServiceMap.submitComplain, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.submitComplain) {
            if (param.result.bstatus.code == 0) {
                finish();
            }
            showToast(param.result.bstatus.des);
        } else if (param.key == ServiceMap.uploadPic) {
            addView.onMsgSearchComplete(param);
        }
        return super.onMsgSearchComplete(param);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addView.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        startRequest();
    }
}
