package com.page.home.activity;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class WorkersResult extends BaseResult {
    public WorkersData data;

    public static class WorkersData implements BaseData {
        public List<WorkersItem> workerList;

    }

    public static class WorkersItem implements Serializable {
        public static final String TAG = WorkersItem.class.getSimpleName();
        public String name;
        public int id;
        public String phone;
    }
}
