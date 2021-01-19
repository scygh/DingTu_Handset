package com.scy.dingtu_handset.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.scy.dingtu_handset.app.entity.BaseResponseAddisOK;
import com.scy.dingtu_handset.app.entity.CodeReadRequest;
import com.scy.dingtu_handset.app.entity.CodeReadResponse;
import com.scy.dingtu_handset.app.entity.CodeRechargeRequest;
import com.scy.dingtu_handset.app.entity.CodeRechargeResponse;
import com.scy.dingtu_handset.app.entity.DeviceReadCardRequest;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
import com.scy.dingtu_handset.app.entity.UserGetTo;
import com.scy.dingtu_handset.app.utils.RxUtils;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.CardInfoTo;
import com.scy.dingtu_handset.app.entity.QRDepositParam;
import com.scy.dingtu_handset.mvp.contract.QRDepositContract;

import javax.inject.Inject;

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
public class QRDepositPresenter extends BasePresenter<QRDepositContract.Model, QRDepositContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public QRDepositPresenter(QRDepositContract.Model model, QRDepositContract.View rootView) {
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

    public void codeRead(int company, int id, CodeReadRequest codeReadRequest) {
        mModel.codeRead(company, id, codeReadRequest)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponseAddisOK<CodeReadResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseAddisOK<CodeReadResponse> cardInfoToBaseResponse) {
                        if (cardInfoToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(cardInfoToBaseResponse.getMessage());
                        } else {
                            if (cardInfoToBaseResponse.isSuccess())
                                if (cardInfoToBaseResponse.getContent() != null)
                                    mRootView.onCodeRead(cardInfoToBaseResponse.getContent());
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


    public void deviceReadCard(int company, int id, DeviceReadCardRequest readCardRequest) {
        mModel.deviceReadCard(company, id, readCardRequest)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponseAddisOK<DeviceReadCardResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

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

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void codeRecharge(int company, int id, CodeRechargeRequest codeRechargeRequest) {
        mModel.codeRecharge(company, id, codeRechargeRequest)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponseAddisOK<CodeRechargeResponse>>() {
                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseAddisOK<CodeRechargeResponse> baseResponse) {
                        if (baseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(baseResponse.getMessage());
                            Log.e(TAG, "onNext: " + baseResponse.getMessage());
                        } else {
                            if (baseResponse.isSuccess())
                                mRootView.onCodeRecharge(baseResponse.getContent());
                        }

                    }
                });
    }
}
