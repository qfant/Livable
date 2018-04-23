package com.page.uc.payfee.model;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/17.
 */

public class OweListResult extends BaseResult {


    public Data data;

    public static class Data {
        public int totalNum;
        public double totalPrice;
        public List<Arrearages> arrearages;

        public static class Arrearages {
            public String id;
            public String month;
            public String startdate;
            public String enddate;
            public String roomid;
            public double monthfee;

        }
    }
}
