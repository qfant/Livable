package com.page.home.patrol;

import com.framework.domain.param.BaseParam;

import java.util.ArrayList;

/**
 * Created by chenxi.cui on 2018/4/25.
 * checkId
 * placeId
 * pic
 * itemValues
 * itemValues.id
 * itemValues.value
 * itemValues.remark
 */

public class PatrolCheckParam extends BaseParam {
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
