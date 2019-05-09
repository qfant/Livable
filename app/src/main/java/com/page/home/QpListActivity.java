package com.page.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.framework.activity.BaseActivity;
import com.page.home.activity.QpListFragment;
import com.page.store.home.fragment.ClassifyFragment;
import com.qfant.wuye.R;

public class QpListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_qplist_layout);
        FragmentTransaction fragmentTransaction = getContext().getSupportFragmentManager().beginTransaction();
        ClassifyFragment fragment = new ClassifyFragment();
        fragmentTransaction.add(R.id.ll_content, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
