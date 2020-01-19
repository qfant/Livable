package com.page.store.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.framework.activity.BaseFragment;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.LineDecoration;
import com.framework.view.sivin.Banner;
import com.framework.view.sivin.BannerAdapter;
import com.page.home.activity.WebActivity;
import com.page.home.model.LinksResult;
import com.page.store.home.holder.NavHolder;
import com.page.store.home.holder.ProHolder;
import com.page.store.home.model.ClassifyResult;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.page.store.prodetails.activity.ProDetailsActivity;
import com.qfant.wuye.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.page.community.serve.activity.ServeActivity.TITLE;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class ClassifyFragment extends BaseFragment implements View.OnTouchListener {

    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.rv_nav_list)
    RecyclerView rvNavList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.xspbanner)
    Banner banner;
    Unbinder unbinder;
    private MultiAdapter multiAdapter;
    private MultiAdapter parentAdapter;
    private ClassifyResult result;
    private BannerAdapter bannerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.pub_activity_classify_layout);
        unbinder = ButterKnife.bind(this, view);
        view.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitleBar("商城", false);
        setLeftListView();
        setRightListView();
        startRequest();
        getLinks();
        setVideoView();
        setBanner();
    }

    private void setVideoView() {
        Uri mUri = Uri.parse("android.resource://" + this.getContext().getPackageName() + "/" + R.raw.aa);
        videoView.setVideoURI(mUri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                // TODO Auto-generated method stub
                mPlayer.start();
                mPlayer.setLooping(true);
            }
        });
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startActivity(new Intent(getContext(), PlayerActivity.class));
                }
                return false;
            }
        });
    }
    private void setBanner() {
        ArrayList<LinksResult.Data.Links> arrayList = new ArrayList<>();
        bannerAdapter = new BannerAdapter<LinksResult.Data.Links>(arrayList) {
            @Override
            protected void bindTips(TextView tv, LinksResult.Data.Links bannerModel) {
//                tv.setText(bannerModel.getTips());
            }

            @Override
            public void bindImage(ImageView imageView, LinksResult.Data.Links bannerModel) {
                ImageLoad.loadPlaceholder(getContext(), bannerModel.imgurl, imageView);
            }

        };
        banner.setBannerAdapter(bannerAdapter);
        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    LinksResult.Data.Links links = (LinksResult.Data.Links) bannerAdapter.getmDataList().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString(TITLE, links.title);
                    bundle.putString(WebActivity.URL, links.link);
                    qStartActivity(WebActivity.class, bundle);
                } catch (Exception e) {

                }
            }
        });
    }
    @Override
    public void onResume() {
        videoView.start();
        super.onResume();
    }

    private void startRequest() {
        Request.startRequest(new BaseParam(), ServiceMap.getCategorys, mHandler, Request.RequestFeature.BLOCK);
    }
    private void getLinks() {
        Request.startRequest(new BaseParam(), ServiceMap.getLinks, mHandler, Request.RequestFeature.BLOCK);
    }


    private void setLeftListView() {
        parentAdapter = new MultiAdapter<ClassifyResult.Data.Datas>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new NavHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_classify_left_item_layout, parent, false));
            }
        });

        rvNavList.setHasFixedSize(true);
        rvNavList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNavList.addItemDecoration(new LineDecoration(getContext(), LineDecoration.VERTICAL_LIST, R.drawable.pub_white_line));
        rvNavList.setAdapter(parentAdapter);
        parentAdapter.setOnItemClickListener(new OnItemClickListener<ClassifyResult.Data.Datas>() {
            @Override
            public void onItemClickListener(View view, ClassifyResult.Data.Datas data, int position) {
                for (ClassifyResult.Data.Datas item : (ArrayList<ClassifyResult.Data.Datas>) parentAdapter.getData()) {
                    item.isSelect = item == data;
                }
                multiAdapter.setData(data.products);
                parentAdapter.notifyDataSetChanged();
            }
        });

    }

    private void setRightListView() {

        multiAdapter = new MultiAdapter<CommitOrderParam.Product>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ProHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_classify_right_item_layout, parent, false));
            }
        });
        rvList.setLayoutManager(new GridLayoutManager(getContext(),2));
//        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
//        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setAdapter(multiAdapter);
        multiAdapter.setOnItemClickListener(new OnItemClickListener<ClassifyResult.Data.Datas.Produts>() {
            @Override
            public void onItemClickListener(View view, ClassifyResult.Data.Datas.Produts data, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString(ClassifyListActivity.CATEGORYID, data.id);
//                qStartActivity(ClassifyListActivity.class, bundle);

                Bundle bundle = new Bundle();
                bundle.putString(ProDetailsActivity.ID, data.id);
                qStartActivity(ProDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getCategorys) {
            result = (ClassifyResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.categoryResult) && !ArrayUtils.isEmpty(result.data.categoryResult.get(0).products)) {
                ClassifyResult.Data.Datas datas = result.data.categoryResult.get(0);
                datas.isSelect = true;
                parentAdapter.setData(result.data.categoryResult);
                multiAdapter.setData(result.data.categoryResult.get(0).products);
            }
        }else if (param.key == ServiceMap.getLinks) {
            LinksResult linksResult = (LinksResult) param.result;
            if (linksResult != null && linksResult.data != null && linksResult.data.links != null) {
                updateBanner(linksResult.data.links);
            }

        }
        return false;
    }
    private void updateBanner(List<LinksResult.Data.Links> links) {
        bannerAdapter.setImages(links);
        banner.notifyDataHasChanged();
    }

    /**
     * @param v
     * @param event
     * @return 防止击穿
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onBackPressed() {
        FragmentTransaction fragmentTransaction = getContext().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.back_left_in_show
                , R.anim.back_right_out_dismiss
                , R.anim.back_left_in_show
                , R.anim.back_right_out_dismiss);
        fragmentTransaction.remove(this);
        fragmentTransaction.commitAllowingStateLoss();
        return true;
    }
}
