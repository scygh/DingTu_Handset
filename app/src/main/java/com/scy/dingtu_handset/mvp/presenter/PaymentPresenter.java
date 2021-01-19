package com.scy.dingtu_handset.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.scy.dingtu_handset.app.entity.BaseResponseAddisOK;
import com.scy.dingtu_handset.app.entity.CodeExpenseRequest;
import com.scy.dingtu_handset.app.entity.CodeExpenseResponse;
import com.scy.dingtu_handset.app.entity.CodeReadRequest;
import com.scy.dingtu_handset.app.entity.CodeReadResponse;
import com.scy.dingtu_handset.app.utils.RxUtils;
import com.scy.dingtu_handset.app.utils.SpUtils;
import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.QRExpenseParam;
import com.scy.dingtu_handset.app.entity.QRExpenseTo;
import com.scy.dingtu_handset.app.entity.QRReadTo;
import com.scy.dingtu_handset.mvp.contract.PaymentContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PaymentPresenter extends BasePresenter<PaymentContract.Model, PaymentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PaymentPresenter(PaymentContract.Model model, PaymentContract.View rootView) {
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

    public void codeExpense(int company, int id, CodeExpenseRequest codeExpenseRequest) {
        mModel.codeExpense(company, id, codeExpenseRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseResponseAddisOK<CodeExpenseResponse>>() {
                    @Override
                    public void onError(Throwable t) {
                        mRootView.onPayFailure();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseAddisOK<CodeExpenseResponse> codeExpenseResponse) {
                        if (codeExpenseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(codeExpenseResponse.getMessage());
                            mRootView.onPayFailure();

                        } else {
                            if (codeExpenseResponse.isSuccess())
                                mRootView.onPaySuccess(codeExpenseResponse.getContent());
                        }

                    }
                });
    }
}
