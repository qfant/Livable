package com.page.home.patrol;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolParentListResult extends BaseResult {
    public PatrolListData data;

    public static class PatrolListData implements BaseData {
        public List<PatrolItem> patrolItemList;

    }

    public static class PatrolItem implements Serializable {

        public String name;
        public String updateTime;

        public static final String TAG = PatrolItem.class.getSimpleName();
    }
}
