package com.page.home.activity;

import com.framework.domain.param.BaseParam;

import java.util.ArrayList;


public class AssignRepairParam extends BaseParam {
    public String checkId;
    public String placeId;
    public String pic;
    public ArrayList<CheckParam> itemValues;

    public static class CheckParam extends BaseParam {
        public int id;
        public boolean value;
        public String remark;
    }
}
