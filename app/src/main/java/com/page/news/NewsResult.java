package com.page.news;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/22.
 */

public class NewsResult extends BaseResult {
    public NewsData data;

    public static class NewsData implements Serializable {
        public List<FengcaiResult.FengcaiBean> newResult;
    }

}
