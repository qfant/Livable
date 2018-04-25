package com.page.home.patrol;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolListResult extends BaseResult {
    public PatrolListData data;

    public static class PatrolListData implements BaseData {
        public List<PatrolItem> checkList;
    }

    public static class PatrolItem implements Serializable {
        public String id;
        public String name;
//        public String qrcode;//编号
//        public String qrcode;//二维码
        public static final String TAG = PatrolItem.class.getSimpleName();
    }
}
