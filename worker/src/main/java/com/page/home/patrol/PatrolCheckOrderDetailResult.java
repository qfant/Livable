package com.page.home.patrol;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolCheckOrderDetailResult extends BaseResult {
    public HistoryDetailData data;

    public static class HistoryDetailData implements BaseData {
        public List<HistoryDetail> RecordList;

    }

    public static class HistoryDetail implements Serializable {
        public static final String TAG = HistoryDetail.class.getSimpleName();
        public String itemName;
        public int id;
        public boolean chooseValue;
        public String remark;
        public String checkname;
        public String createtime;
    }
}
