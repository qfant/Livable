package com.page.news;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/22.
 */

public class FengcaiResult extends BaseResult {
    public FengcaiData data;

    public static class FengcaiData implements Serializable {
        public List<FengcaiBean> fengcaiResult;
    }

    public static class FengcaiBean implements Serializable {
        public int id;
        public String title;
        public String intro;
        public String district_id;
    }
}
