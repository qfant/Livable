package com.page.home.patrol;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haolb.client.R;
/**
 * Created by chenxi.cui on 2018/4/24.
 */

class PatrolTourHistoryAdapter extends BaseQuickAdapter<PatrolTourHistoryResult.TourHistoryItem,BaseViewHolder> {
    public PatrolTourHistoryAdapter(int context) {
        super(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatrolTourHistoryResult.TourHistoryItem item) {
        helper.setText(R.id.text_name, item.placename);
//        helper.getView(R.id.text_time).setVisibility(View.GONE);
        helper.setText(R.id.text_time, item.createtime);
    }

//    @Override
//    protected View newView(Context context, ViewGroup parent) {
//        return inflate(R.layout.pub_patrol_list_item_view, parent, false);
//    }
//
//    @Override
//    protected void bindView(View view, Context context, TourHistoryItem item, int position) {
//        TextView textName = (TextView) view.findViewById(R.id.text_name);
//        TextView textTime = (TextView) view.findViewById(R.id.text_time);
//        TextView if_arr = (TextView) view.findViewById(R.id.if_arr);
//        textName.setText(item.placename);
//        textTime.setText(item.createtime);
//        if_arr.setVisibility(View.GONE);
////        textTime.setVisibility(View.GONE);
//    }
}
