package com.page.home.maintain;

import com.framework.domain.param.BaseParam;
import com.page.login.UCUtils;

/**
 * Created by shucheng.qu on 2017/9/1.
 */

public class ApplyForParam extends BaseParam {

    public String pic;
    public String intro;
    public String address;
    public String phone;
    public String type = UCUtils.getInstance().getUserInfo().type;


}
