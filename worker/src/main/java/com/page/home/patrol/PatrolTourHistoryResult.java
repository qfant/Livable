package com.page.home.patrol;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolTourHistoryResult extends BaseResult {
    public TourHistoryData data;

    public static class TourHistoryData implements BaseData {
        public List<TourHistoryItem> patrolList;

    }

    public static class TourHistoryItem implements Serializable {

        public static final String TAG = TourHistoryItem.class.getSimpleName();
        public String placename;
        public String createtime;
        public String qrcode;
        public int id;
    }
}
