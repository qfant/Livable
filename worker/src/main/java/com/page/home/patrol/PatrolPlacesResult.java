package com.page.home.patrol;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/4/24.
 */

public class PatrolPlacesResult extends BaseResult {
    public PatrolListData data;

    public static class PatrolListData implements BaseData {
        public List<Patrol> placesList;

    }

    public static class Patrol implements Serializable {

        public String name;
        public String serialnum;
        public static final String TAG = Patrol.class.getSimpleName();
        public String qrcode;
        public int id;
    }
}
