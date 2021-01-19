package com.scy.dingtu_handset.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.scy.dingtu_handset.app.entity.BaseResponseAddisOK;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.RoleTo;
import com.scy.dingtu_handset.mvp.contract.MainContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getRole() {
        String userId = (String) SpUtils.get(mApplication, AppConstant.Api.USERID, "");
        if (userId.isEmpty()) return;
        Log.e(TAG, "getRole: ----------------------------");
        mModel.getRole(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponseAddisOK<RoleTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponseAddisOK<RoleTo> roleToBaseResponse) {
                        if (roleToBaseResponse.isSuccess()) {
                            ArrayList<String> modules = new ArrayList<>();
                            modules.clear();
                            modules.addAll(roleToBaseResponse.getContent().getModules());
                            SpUtils.put(mApplication, AppConstant.Api.BLOCK_USER, false);
                            SpUtils.put(mApplication, AppConstant.Api.BLOCK_REPORT, false);
                            SpUtils.put(mApplication, AppConstant.Api.BLOCK_SETUP, false);

                            for (String str : modules) {
                                if (str.equals("00000000-0000-0000-0000-000000000002")) {//【用户管理】
                                    SpUtils.put(mApplication, AppConstant.Api.BLOCK_USER, true);

                                } else if (str.equals("00000000-0000-0000-0000-000000000004")) {//【报表中心】
                                    SpUtils.put(mApplication, AppConstant.Api.BLOCK_REPORT, true);

                                } else if (str.equals("00000000-0000-0000-0000-000000000005")) {//【设置】
                                    SpUtils.put(mApplication, AppConstant.Api.BLOCK_SETUP, true);

                                }
                            }

                        }
                    }
                });
    }
}
