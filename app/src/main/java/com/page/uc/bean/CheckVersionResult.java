package com.page.uc.bean;

import com.framework.domain.response.BaseResult;
import com.framework.domain.response.UpgradeInfo;
import com.framework.net.ServiceMap;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2018/1/22.
 */

public class CheckVersionResult extends BaseResult {
    public CheckVersionData data;

    public static class CheckVersionData implements Serializable {


        public UpgradeInfo upgradeInfo;
    }
}
