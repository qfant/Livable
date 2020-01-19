package com.page.home.patrol;

import com.framework.domain.param.BaseParam;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolListParam extends BaseParam {
//    public int placeId;
    public String qrcode;
    public int pageNo = 1;
    public int pageSize = 20;
}
