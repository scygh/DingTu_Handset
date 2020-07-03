package com.scy.dingtu_handset.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scy.dingtu_handset.R;
import com.scy.dingtu_handset.app.utils.FragmentUtils;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.task.MainTask;
import com.scy.dingtu_handset.app.task.TaskParams;
import com.scy.dingtu_handset.di.component.DaggerMainComponent;
import com.scy.dingtu_handset.mvp.contract.MainContract;
import com.scy.dingtu_handset.mvp.presenter.MainPresenter;
import com.scy.dingtu_handset.mvp.ui.fragment.HomeFragment;
import com.scy.dingtu_handset.mvp.ui.fragment.MineFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.scy.dingtu_handset.app.api.AppConstant.ACTIVITY_FRAGMENT_REPLACE;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/08/2019 09:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.main_frame)
    FrameLayout mMainFrame;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private List<Fragment> mFragments;
    private int mReplace = 0;

    private OnTabSelectListener mOnTabSelectListener = tabId -> {
        switch (tabId) {
            case R.id.tab_home:
                mReplace = 0;
                break;
            case R.id.tab_mine:
                boolean setup = (boolean) SpUtils.get(MainActivity.this, AppConstant.Api.BLOCK_SETUP,true);
                if (setup){
                    mReplace = 1;
                }
                break;
        }
        FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
    };
    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.getRole();
        HomeFragment homeFragment;
        MineFragment mineFragment;

        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            mineFragment = MineFragment.newInstance();
        } else {
            mReplace = savedInstanceState.getInt(ACTIVITY_FRAGMENT_REPLACE);
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm, HomeFragment.class);
            mineFragment = (MineFragment) FragmentUtils.findFragment(fm, MineFragment.class);

        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(mineFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);

        //updateApk();

    }
    private void updateApk() {
        TaskParams params = new TaskParams();
        MainTask.UpdateTask dbTask = new MainTask.UpdateTask(this, false);
        dbTask.execute(params);
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
