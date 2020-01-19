package com.page.home.patrol;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haolb.client.R;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolCheckOrdersAdapter extends BaseQuickAdapter<PatrolCheckOrdersResult.CheckOrder,BaseViewHolder> {
    @Override
    protected void convert(BaseViewHolder helper, PatrolCheckOrdersResult.CheckOrder item) {
        helper.setText(R.id.text_name, item.placename);
//        helper.getView(R.id.text_time).setVisibility(View.GONE);
        helper.setText(R.id.text_time, item.createtime);
    }
    public PatrolCheckOrdersAdapter(int context) {
        super(context);
    }
//
//    @Override
//    protected View newView(Context context, ViewGroup parent) {
//        return inflate(R.layout.pub_patrol_list_item_view, parent, false);
//    }
//
//    @Override
//    protected void bindView(View view, Context context, CheckOrder item, int position) {
//        TextView textName = (TextView) view.findViewById(R.id.text_name);
//        TextView textTime = (TextView) view.findViewById(R.id.text_time);
//        textName.setText(item.placename + "/" + item.checkname+"/"+item.createtime);
////        textTime.setText(item.qrcode);
//    }
}
