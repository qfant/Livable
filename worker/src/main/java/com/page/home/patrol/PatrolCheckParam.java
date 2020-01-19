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
    public int checkId;
    public int placeId;
    public String pic;
    public String pic2;
    public String pic3;
    public int repairChooseValue;
    public ArrayList<CheckParam> itemValues;

    public static class CheckParam extends BaseParam {
        public int id;
        public int value;
        public String remark;
    }
}
