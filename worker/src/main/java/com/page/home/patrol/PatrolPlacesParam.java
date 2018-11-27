package com.page.home.patrol;

import com.framework.domain.param.BaseParam;
import com.page.login.UCUtils;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolPlacesParam extends BaseParam {
    public String type = UCUtils.getInstance().getUserInfo().type;
    public int pageNo = 1;
    public int pageSize = 20;
    public String keyword;
}
