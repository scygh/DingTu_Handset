package com.scy.dingtu_handset.mvp.presenter;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.BaseResponseAddisOK;
import com.scy.dingtu_handset.app.entity.CardInfoTo;
import com.scy.dingtu_handset.app.entity.DeviceReadCardRequest;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
import com.scy.dingtu_handset.app.entity.MoneyParam;
import com.scy.dingtu_handset.app.entity.RefundRequest;
import com.scy.dingtu_handset.app.entity.RefundResponse;
import com.scy.dingtu_handset.app.entity.UserGetTo;
import com.scy.dingtu_handset.app.utils.AudioUtils;
import com.scy.dingtu_handset.app.utils.RxUtils;
import com.scy.dingtu_handset.mvp.contract.RechargeContract;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RechargePresenter extends BasePresenter<RechargeContract.Model, RechargeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RechargePresenter(RechargeContract.Model model, RechargeContract.View rootView) {
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

    public void deviceReadCard(int company, int id, DeviceReadCardRequest readCardRequest) {
        mModel.deviceReadCard(company, id, readCardRequest)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponseAddisOK<DeviceReadCardResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override public void onNext(BaseResponseAddisOK<DeviceReadCardResponse> readCardToBaseResponse) {
                        if (readCardToBaseResponse.getStatusCode()!=200){
                            mRootView.showMessage(readCardToBaseResponse.getMessage());
                        }else {
                            if (readCardToBaseResponse.isSuccess())
                                if (readCardToBaseResponse.getContent() != null)
                                    mRootView.onReadCard(readCardToBaseResponse.getContent());
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

    public void getCardInfo(int number) {
        mModel.getByNumber(number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<CardInfoTo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<CardInfoTo> cardInfoToBaseResponse) {
                        if (cardInfoToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(cardInfoToBaseResponse.getMessage());
                        } else {
                            if (cardInfoToBaseResponse.isSuccess())
                                if (cardInfoToBaseResponse.getContent() != null)
                                    mRootView.onCardInfo(cardInfoToBaseResponse.getContent());
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

    public void onDonate(int id, double d) {
        mModel.getDonate(id, d)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<Double>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<Double> doubleBaseResponse) {
                        if (doubleBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(doubleBaseResponse.getMessage());
                        } else {
                            if (doubleBaseResponse.isSuccess())
                                mRootView.onDonate(doubleBaseResponse.getContent());
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

    public void recharge(int company, int id, RefundRequest refundRequest) {
        mModel.recharge(company, id, refundRequest)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponseAddisOK<RefundResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseAddisOK<RefundResponse> baseResponse) {
                        if (baseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(baseResponse.getMessage());
                        } else {
                            if (baseResponse.isSuccess())
                                if (baseResponse.isResult()) {
                                    mRootView.onRecharge(baseResponse.getContent());
                                }
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
