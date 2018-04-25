package com.page.home.patrol;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolDetailResult extends BaseResult {
    public PPatrolDetailtData data;

    public static class PPatrolDetailtData implements BaseData {
        public List<CheckItem> checkItemsList;

    }

    public static class CheckItem implements Serializable {
        public static final String TAG = CheckItem.class.getSimpleName();
        public String name;
        public int id;
        public boolean isCheck;
        public String remark;
    }
}
