package com.framework.net;


import com.framework.app.AppConstants;
import com.framework.domain.response.BaseResult;
import com.framework.utils.Enums;
import com.page.address.AddressResult;
import com.page.community.details.model.RepairDetailResult;
import com.page.community.eventdetails.model.EventDetailsResult;
import com.page.community.eventlist.model.EventListResult;
import com.page.community.quickpain.model.QpDetailResult;
import com.page.community.quickpain.model.ScommentsReault;
import com.page.community.serve.model.ComplainResult;
import com.page.community.serve.model.PhoneResult;
import com.page.community.serve.model.RepairResult;
import com.page.community.serve.model.SerDetailResult;
import com.page.community.serve.model.ServeResult;
import com.page.community.signup.model.SignUpResult;
import com.page.home.model.ContactResult;
import com.page.home.model.LinksResult;
import com.page.home.model.NoticeResult;
import com.page.home.model.QpListResult;
import com.page.home.model.ShopRecResult;
import com.page.news.FengcaiResult;
import com.page.news.NewsResult;
import com.page.pay.ProductPayResult;
import com.page.pay.WeChatPayResult;
import com.page.store.home.model.ClassifyResult;
import com.page.store.collect.model.CollectResult;
import com.page.store.home.model.FoodRecResult;
import com.page.store.orderaffirm.activity.SubmitResult;
import com.page.store.orderaffirm.model.DefaultAddressResult;
import com.page.store.orderdetails.model.OrderDetailResult;
import com.page.store.orderlist.model.OrderListResult;
import com.page.store.prodetails.model.PDResult;
import com.page.store.prodetails.model.PEResult;
import com.page.uc.bean.BuidingsResult;
import com.page.uc.bean.DistrictsResult;
import com.page.uc.bean.LoginResult;
import com.page.uc.bean.NickNameResult;
import com.page.uc.bean.RegiserResult;
import com.page.uc.bean.RoomsResult;
import com.page.uc.bean.UnitsResult;
import com.page.uc.bean.UpdateMyPortraitResult;
import com.page.uc.payfee.model.OweListResult;
import com.page.uc.payfee.model.ubmitWuyeFeeResult;
import com.page.uc.payfee.model.FeeListResult;
import com.page.uc.payfee.model.FeeMonthResult;
import com.page.uc.payfee.model.WaitFeeResult;
import com.page.update.CheckVersionResult;

import java.io.Serializable;

/**
 * @author zexu
 */
public enum ServiceMap implements Enums.IType {
    CHECK_VERSION(AppConstants.UC_URL + "/checkVersion.do", CheckVersionResult.class),
    FENGCAI("/fengcai", FengcaiResult.class),
    news("/news", NewsResult.class),
    getCategorys("/category", ClassifyResult.class),//商品分类
    getProduct("/product", PDResult.class),//商品分类详情
    getLinks("/links", LinksResult.class),
    OPENGATE("/opengate", BaseResult.class), //获取通讯录联系人

    checkVersion("/checkVersion", BaseResult.class),
    alipayPayProduct("/alipayPayProduct", ProductPayResult.class),
    alipayPayWuyeFee("/alipayPayWuyeFee", ProductPayResult.class),
    alipayPayRepair("/alipayPayRepair", ProductPayResult.class),
    wechatPayProduct("/wechatPayProduct", WeChatPayResult.class),//微信商城
    wechatPayWuyeFee("/wechatPayWuyeFee", WeChatPayResult.class),//微信物业缴费
    wechatPayRepair("/wechatPayRepair", WeChatPayResult.class),//微信维修缴费
    getAddresses("/getAddresses", AddressResult.class),
    submitAddress("/submitAddress", BaseResult.class),
    updateAddress("/updateAddress", BaseResult.class),
    deleteAddress("/deleteAddress", BaseResult.class),
    setDefaultAddress("/setDefaultAddress", BaseResult.class),
    getDistricts("/getDistricts", DistrictsResult.class),//获取小区列表
    getBuildings("/getBuildings", BuidingsResult.class),//获取栋号列表
    getUnits("/getUnits", UnitsResult.class),//获取单元列表
    getRooms("/getRooms", RoomsResult.class),//获取单元列表
    getVerificationCode("/getVerificationCode", BaseResult.class),
    quickRegister("/quickRegister", RegiserResult.class),
    customerLogin("/customerLogin", LoginResult.class),
    customerLogout("/customerLogout", BaseResult.class),
    updateNickname("/updateNickname", NickNameResult.class),
    UPDATE_MY_PROTRAIT("/updateMyPortrait", UpdateMyPortraitResult.class, ServiceMap.NET_TASKTYPE_FILE),
    uploadPic("/uploadPic", UpdateMyPortraitResult.class, ServiceMap.NET_TASKTYPE_FILE),

    getWaters("/getWaters", ServeResult.class),//送水
    getWaterDetail("/getWaterDetail", SerDetailResult.class),//送水
    getHouses("/getHouses", ServeResult.class),//家政
    getHouseDetail("/getHouseDetail", SerDetailResult.class),//家政
    getWashes("/getWashes", ServeResult.class),//洗衣
    getMerchants("/getMerchants", ServeResult.class),//周边
    getDistrictContact("/getDistrictContact", PhoneResult.class),//phone
    getMyComplainList("/getMyComplainList", ComplainResult.class),//投诉列表
    deleteComplain("/deleteComplain", BaseResult.class),//投诉删除
    submitComplain("/submitComplain", BaseResult.class),//投诉保存
    contact("/contact", ContactResult.class),//
    getWashDetail("/getWashDetail", SerDetailResult.class),//洗衣
    getMerchantDetail("/getMerchantDetail", SerDetailResult.class),//洗衣
    getActivityList("/getActivityList", EventListResult.class),//首页活动列表
    getMyActivityList("/getMyActivityList", EventListResult.class),//我的活动列表
    getActivity("/getActivity", EventDetailsResult.class),//活动详情
    submitActivity("/submitActivity", BaseResult.class),//添加活动
    joinActivity("/joinActivity", BaseResult.class),//参与活动
    updateActivity("/updateActivity", BaseResult.class),//活动修改
    canceljoinActivity("/canceljoinActivity", BaseResult.class),//取消参与活动
    getActivityJoinerList("/getActivityJoinerList", SignUpResult.class),//获取活动参与人
    submitSnapshot("/submitSnapshot", BaseResult.class),//发布随手拍
    getMySnapshots("/getMySnapshots", QpListResult.class),
    deleteSnapshot("/deleteSnapshot", BaseResult.class),//删除
    getSnapshots("/getSnapshots", QpListResult.class),
    getSnapshot("/getSnapshot", QpDetailResult.class),//详情
    updateSnapshot("/updateSnapshot", QpDetailResult.class),//编辑
    scomment("/scomment", BaseResult.class),//评论
    scomments("/scomments", ScommentsReault.class),//评论列表
    deleteScomment("/deleteScomment", BaseResult.class),//评论del
    submitRepair("/submitRepair", BaseResult.class),//add维修
    getRepair("/getRepair", RepairDetailResult.class),//维修detail
    evaluateRepair("/evaluateRepair", BaseResult.class),//维修评价
    getMyRepairs("/getMyRepairs", RepairResult.class),//修list

    getNews("/getCategorys", ClassifyResult.class),//商品分类
    submitOrder("/submitOrder", SubmitResult.class),//提交订单
    getMyOrders("/getMyOrders", OrderListResult.class),//订单list
    getOrder("/getOrder", OrderDetailResult.class),//订单详情
    cancelOrder("/cancelOrder", BaseResult.class),//订单
    updateOrderConfirm("/orderEvaluate", BaseResult.class),//订单
    orderEvaluate("/updateOrderConfirm", BaseResult.class),//订单
    orderRefund("/orderRefund", BaseResult.class),//订单
    fav("/fav", BaseResult.class),//收藏or取消
    getFavList("/getFavList", CollectResult.class),//收藏list
    getDefaultAddress("/getDefaultAddress", DefaultAddressResult.class),//默认收货地址
    validStorage("/validStorage", BaseResult.class),//验证库存
    pcomments("/pcomments", PEResult.class),//商品评论list
    getNoticeList("/getNoticeList", NoticeResult.class),//首页公告
    getRecommendCategorys("/getRecommendCategorys", FoodRecResult.class),//超时首页视频推荐
    getParticularProducts("/getParticularProducts", ShopRecResult.class),//首页
    getMyWuyeFees("/getMyWuyeFees", WaitFeeResult.class),//物业代缴费
    submitWuyeFee("/submitWuyeFee", ubmitWuyeFeeResult.class),//物业代缴费
    wuyeFeeMonths("/wuyeFeeMonths", FeeMonthResult.class),//物业代缴费
    wuyeFeeOrders("/wuyeFeeOrders", FeeListResult.class),//物业代缴费
    getMyArrearageList("/getMyArrearageList", OweListResult.class),//物业欠费
    ;


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
