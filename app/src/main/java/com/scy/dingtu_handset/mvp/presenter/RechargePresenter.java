package com.scy.dingtu_handset.mvp.presenter;

import android.app.Application;
import android.widget.Toast;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.CardInfoTo;
import com.scy.dingtu_handset.app.entity.MoneyParam;
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

    public void userGetTo(int number) {
        mModel.userGetTo(number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<UserGetTo>>() {
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
                    public void onNext(BaseResponse<UserGetTo> readCardToBaseResponse) {
                        if (readCardToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(readCardToBaseResponse.getMessage());
                        } else {
                            if (readCardToBaseResponse.isSuccess())
                                if (readCardToBaseResponse.getContent() != null)
                                    mRootView.onUserGetTo(readCardToBaseResponse.getContent());
                        }
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

    public void onDeposit(MoneyParam param, int cardtype) {
        mModel.addDeposit(param)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(baseResponse.getMessage());
                        } else {
                            if (baseResponse.isSuccess())
                                if (baseResponse.isResult()) {
                                    userGetTo(param.getNumber());
                                    if (cardtype == 1) {
                                        AudioUtils.getInstance().speakText(String.format("充值%.2f元", param.getAmount()));
                                    } else if (cardtype == 4) {
                                        AudioUtils.getInstance().speakText("充值" + (int) param.getAmount() + "次");
                                    }
                                    Toasty.success(mApplication, "充值成功", Toast.LENGTH_SHORT, true).show();
                                }
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
}
