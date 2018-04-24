package com.page.home.maintain;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class MaintainHistoryListResult extends BaseResult {
    public MaintainHistoryListData data;

    public static class MaintainHistoryListData implements BaseData {
        public List<MaintainItem> maintainItems;
    }

    public static class MaintainItem implements Serializable {
        public String name;
        public String updateTime;
        public static final String TAG = MaintainItem.class.getSimpleName();
    }
}
