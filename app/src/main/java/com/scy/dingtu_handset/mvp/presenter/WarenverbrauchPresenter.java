package com.scy.dingtu_handset.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.scy.dingtu_handset.app.entity.BaseResponseAddisOK;
import com.scy.dingtu_handset.app.entity.DeviceReadCardRequest;
import com.scy.dingtu_handset.app.entity.EMGoodsTo2;
import com.scy.dingtu_handset.app.utils.RxUtils;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.EMGoodsTypeTo;
import com.scy.dingtu_handset.app.entity.KeySwitchTo;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
import com.scy.dingtu_handset.app.entity.SimpleExpenseParam;
import com.scy.dingtu_handset.app.entity.SimpleExpenseTo;
import com.scy.dingtu_handset.mvp.contract.WarenverbrauchContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/28/2019 11:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WarenverbrauchPresenter extends BasePresenter<WarenverbrauchContract.Model, WarenverbrauchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WarenverbrauchPresenter(WarenverbrauchContract.Model model, WarenverbrauchContract.View rootView) {
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
     * descirption: 获取详情页
     */
    public void getEmGoodsDetail(String type) {
        mModel.findEMGoods(1, 20, type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<EMGoodsTo2>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<EMGoodsTo2> listBaseResponse) {
                        if (listBaseResponse != null && listBaseResponse.getStatusCode() == 200) {
                            mRootView.onEMGoodsDetailGet(listBaseResponse.getContent().getRows());
                        }
                    }
                });
    }

    /**
     * descirption: 获得类别列表
     */
    public void setList() {
        mModel.findEMGoodsType("1")
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<EMGoodsTypeTo>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<EMGoodsTypeTo>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            if (listBaseResponse.getContent() != null) {
                                mRootView.onPagers(listBaseResponse.getContent());
                            }
                        }
                    }
                });
    }

    public void deviceReadCard(int company, int id, DeviceReadCardRequest deviceReadCardRequest) {
        mModel.deviceReadCard(company, id, deviceReadCardRequest)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponseAddisOK<DeviceReadCardResponse>>(mErrorHandler) {
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onFailed();
                    }

                    @Override
                    public void onNext(BaseResponseAddisOK<DeviceReadCardResponse> readCardToBaseResponse) {
                        if (readCardToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(readCardToBaseResponse.getMessage());
                        } else {
                            if (readCardToBaseResponse.isSuccess())
                                if (readCardToBaseResponse.getContent() != null)
                                    mRootView.onReadCard(readCardToBaseResponse.getContent());
                        }
                    }
                });
    }

    public void getPaySgetPayKeySwitch2() {
        String _device = (String) SpUtils.get(mApplication, AppConstant.Receipt.NO, "");
        int id = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);
        mModel.getEMDevice(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<KeySwitchTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<KeySwitchTo> keySwitchToBaseResponse) {
                        if (keySwitchToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(keySwitchToBaseResponse.getMessage());
                        } else {
                            if (keySwitchToBaseResponse.isSuccess())
                                if (keySwitchToBaseResponse.getContent() != null)
                                    mRootView.creatBill2(keySwitchToBaseResponse.getContent().isKeyEnabled());
                            Log.e(TAG, "onNext: " + keySwitchToBaseResponse.getContent().isKeyEnabled());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void createSimpleExpense(int companyCode, int deviceID, SimpleExpenseParam param) {
        mModel.createSimpleExpense(companyCode, deviceID, param)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<SimpleExpenseTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<SimpleExpenseTo> simpleExpenseToBaseResponse) {
                        if (simpleExpenseToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(simpleExpenseToBaseResponse.getMessage());
                        } else {
                            if (simpleExpenseToBaseResponse.isSuccess())
                                if (simpleExpenseToBaseResponse.getContent() != null)
                                    mRootView.creatSuccess(simpleExpenseToBaseResponse.getContent());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
