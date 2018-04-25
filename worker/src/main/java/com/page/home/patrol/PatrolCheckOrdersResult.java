package com.page.home.patrol;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolCheckOrdersResult extends BaseResult {
    public PatrolCheckOrdersData data;

    public static class PatrolCheckOrdersData implements BaseData {
        public List<CheckOrder> orderListResult;

    }

    public static class CheckOrder implements Serializable {
        public static final String TAG = CheckOrder.class.getSimpleName();
        public String placename;
        public int id;
        public String checkname;
    }
}
