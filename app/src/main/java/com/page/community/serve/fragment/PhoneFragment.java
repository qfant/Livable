package com.page.community.serve.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framework.activity.BaseFragment;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.DateFormatUtils;
import com.framework.utils.html.HtmlUtils;
import com.page.community.serve.model.PhoneResult;
import com.page.uc.payfee.model.WaitFeeQueryParam;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by shucheng.qu on 2017/11/6.
 */

public class PhoneFragment extends BaseFragment {

    @BindView(R.id.tv_text_content)
    TextView tvTextContent;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_phone_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startRequest();
    }

    private void startRequest() {
        Request.startRequest(new BaseParam(), ServiceMap.getDistrictContact, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getDistrictContact) {
            PhoneResult result = (PhoneResult) param.result;
            if (result != null && result.data != null && !TextUtils.isEmpty(result.data.info)) {
                HtmlUtils.getHtml(getContext(), tvTextContent, result.data.info);
//                Spanned spanned = Html.fromHtml(result.data.info);
//                tvTextContent.setText(spanned);

            } else {
                showToast("请刷新重试~");
            }

        }
        return super.onMsgSearchComplete(param);

    }
}
