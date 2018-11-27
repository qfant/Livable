package com.page.community.serve.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2017/11/6.
 */

public class PhoneResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public String info;
    }
}
