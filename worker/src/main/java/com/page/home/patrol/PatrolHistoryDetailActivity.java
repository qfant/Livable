package com.page.home.patrol;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.patrol.PatrolCheckOrdersResult.CheckOrder;
import com.page.home.patrol.PatrolHistoryDetailResult.HistoryDetail;

import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolHistoryDetailActivity extends BaseActivity {
    private CheckOrder patrolItem;
    private TextView textTitle;
    private TextView textCreatetime;
    private LinearLayout llContain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_patrol_history_detail_layout);
        setTitleBar("巡查项目详情", true);
        patrolItem = (CheckOrder) myBundle.getSerializable(CheckOrder.TAG);
        textTitle = (TextView) findViewById(R.id.text_title);
        textCreatetime = (TextView) findViewById(R.id.text_createtime);
        llContain = (LinearLayout) findViewById(R.id.ll_contain);
        textTitle.setText(patrolItem.placename + "/" + patrolItem.checkname + "检查项目");
        requestData();
    }

    private void requestData() {
        PatrolHistoryDetailParam patrolListParam = new PatrolHistoryDetailParam();
        patrolListParam.recordId = patrolItem.id;
        Request.startRequest(patrolListParam, ServiceMap.getRecordDetail, mHandler, Request.RequestFeature.BLOCK);
    }


    public boolean onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceMap.getRecordDetail) {
            if (param.result.bstatus.code == 0) {
                PatrolHistoryDetailResult result = (PatrolHistoryDetailResult) param.result;
                setData(result.data.RecordList);
            }
        }
        return super.onMsgSearchComplete(param);
    }

    private void setData(List<HistoryDetail> checkItemsList) {
//        this.mCheckItemsList = RecordList;
        llContain.removeAllViews();
        if (checkItemsList == null) {
            return;
        }
        for (final HistoryDetail item : checkItemsList) {
            View view = LinearLayout.inflate(this, R.layout.pub_patrol_history_detail_item_view, null);
            TextView textName = (TextView) view.findViewById(R.id.text_name);
            TextView textCreatetime = (TextView) view.findViewById(R.id.text_createtime);
            TextView text2 = (TextView) view.findViewById(R.id.text2);
            TextView text3 = (TextView) view.findViewById(R.id.edit_compat);
            textName.setText(item.itemName);
            textCreatetime.setText(item.createtime);
            text2.setText(item.chooseValue ? "正常" : "异常");
            if (item.chooseValue) {
                text3.setVisibility(View.GONE);
                text2.setTextColor(getResources().getColor(R.color.pub_color_blue));
            } else {
                text2.setTextColor(getResources().getColor(R.color.pub_color_red));
                text3.setText("异常备注:" + item.remark);
                text3.setVisibility(View.VISIBLE);
            }
            llContain.addView(view);
        }
    }

}
