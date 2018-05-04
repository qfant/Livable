package com.page.home.maintain;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class DistrictsResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<DistrictItem> districts;

        public static class DistrictItem implements Serializable {
            public int id;
            public String name;
        }
    }
}
