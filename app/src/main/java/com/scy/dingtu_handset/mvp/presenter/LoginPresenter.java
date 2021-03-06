package com.scy.dingtu_handset.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.UserInfoTo;
import com.scy.dingtu_handset.mvp.contract.LoginContract;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/08/2019 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
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

    /**
    * descirption: 登录获取用户信息，并且去拿卡密码（NFC密码）
    */
    public void login(String username, String password) {
        mModel.login(username, password, "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseResponse<UserInfoTo>>() {
                    @Override
                    public void onError(Throwable t) {
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<UserInfoTo> userInfoToBaseResponse) {
                        if (userInfoToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(userInfoToBaseResponse.getMessage());
                        } else {
                            if (userInfoToBaseResponse.isSuccess()) {
                                if (userInfoToBaseResponse.getContent() != null) {
                                    SpUtils.put(mApplication, AppConstant.Api.TOKEN, userInfoToBaseResponse.getContent().getAccessToken());
                                    SpUtils.put(mApplication, AppConstant.Api.USERID, userInfoToBaseResponse.getContent().getUserId());
                                    getCardPwd(userInfoToBaseResponse.getContent());
                                }
                            }
                        }

                    }
                });
    }

    /**
    * descirption:去拿卡密码（NFC密码），并触发回调
    */
    private void getCardPwd(UserInfoTo userInfoTo) {
        mModel.getCardPassword()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(stringBaseResponse.getMessage());
                        } else {
                            if (stringBaseResponse.isSuccess()) {
                                if (!TextUtils.isEmpty(stringBaseResponse.getMessage())) {
                                    SpUtils.put(mApplication, AppConstant.NFC.NFC_KEY, stringBaseResponse.getContent());
                                    mRootView.onLogin(userInfoTo);
                                }

                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
