package com.framework.net;


import com.framework.app.AppConstants;
import com.framework.domain.response.BaseResult;
import com.framework.utils.Enums;
import com.page.detail.DetailResult;
import com.page.detail.UpdateMyPortraitResult;
import com.page.home.maintain.MaintainHistoryListResult;
import com.page.home.maintain.details.model.RepairDetailResult;
import com.page.home.patrol.PatrolListResult;
import com.page.home.patrol.PatrolParentListResult;
import com.page.home.WorkerRepairResult;
import com.page.login.activity.LoginResult;
import com.page.update.CheckVersionResult;

/**
 * @author zexu
 */
public enum ServiceMap implements Enums.IType {
    evaluateRepair("/evaluateRepair", BaseResult.class),//维修评价
    getWorkerRepairs("/getWorkerRepairs.do", WorkerRepairResult.class), //获取通讯录联系人
    CHECK_VERSION(AppConstants.UC_URL + "/checkVersion.do", CheckVersionResult.class),
    getVerificationCode("/getVerificationCode.do", BaseResult.class),
    customerLogin("/workerLogin.do", LoginResult.class),
    receiveRepair("/receiveRepair.do", BaseResult.class),
    getRepairClient("/getRepair.do", RepairDetailResult.class),
    getRepair("/getRepair.do", DetailResult.class),
    startRepair("/startRepair.do", BaseResult.class),
    endRepair("/endRepair.do", BaseResult.class),
    uploadPic("/uploadPic.do", UpdateMyPortraitResult.class, ServiceMap.NET_TASKTYPE_FILE),
    patrolList("/patrolList.do", PatrolListResult.class),
    submitRepair("/submitRepair", BaseResult.class),//add维修
    patrolParentList("/patrolList.do", PatrolParentListResult.class),;

    private final String mType;
    private final Class<? extends BaseResult> mClazz;
    private final int mTaskType;
    private final static int NET_TASK_START = 0;
    public final static int NET_TASKTYPE_CONTROL = NET_TASK_START;
    public final static int NET_TASKTYPE_FILE = NET_TASKTYPE_CONTROL + 1;
    public final static int NET_TASKTYPE_ALL = NET_TASKTYPE_FILE + 1;

    ServiceMap(String type, Class<? extends BaseResult> clazz) {
        this(type, clazz, NET_TASKTYPE_CONTROL);
    }

    ServiceMap(String type, Class<? extends BaseResult> clazz, int taskType) {
        this.mType = type;
        this.mClazz = clazz;
        this.mTaskType = taskType;
    }

    /**
     * 创建接口对应的resp的Result的对象
     *
     * @return AbsResult或其子类的对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseResult> T newResult() throws IllegalAccessException, InstantiationException {
        return (T) getClazz().newInstance();
    }

    @Override
    public String getDesc() {
        return mType;
    }

    public Class<? extends BaseResult> getClazz() {
        return mClazz;
    }

    @Override
    public int getCode() {
        return mTaskType;
    }
}
