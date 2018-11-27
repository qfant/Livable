package com.page.community.serve.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/11/6.
 */

public class ComplainResult extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<ComplainList> complainList;

        public static class ComplainList implements Serializable {
            public String id;
            public String pic;
            public String createtime;
            public int status;
            public String content;
            public String reply;
        }
    }
}
