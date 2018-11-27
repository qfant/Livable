package com.page.home.patrol;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolTourResult extends BaseResult {
    public PatrolTourData data;

    public static class PatrolTourData implements BaseData {
        public List<TourItem> placeResult;
        public String name;
        public String serialnum;
        public int type;
    }

    public static class TourItem implements Serializable {

        public String name;
        public String serialnum;
        public static final String TAG = TourItem.class.getSimpleName();
        public String qrcode;
        public int id;
    }
}
