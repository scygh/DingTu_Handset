package com.scy.dingtu_handset.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.scy.dingtu_handset.app.entity.OrderDetailGet;
import com.scy.dingtu_handset.app.entity.OrderGoodsDetailList;
import com.scy.dingtu_handset.app.entity.OrderTake;
import com.scy.dingtu_handset.app.utils.RxUtils;
import com.scy.dingtu_handset.mvp.contract.ScanTakeFoodContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/12/2020 10:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ScanTakeFoodPresenter extends BasePresenter<ScanTakeFoodContract.Model, ScanTakeFoodContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ScanTakeFoodPresenter(ScanTakeFoodContract.Model model, ScanTakeFoodContract.View rootView) {
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

    public void orderGet(String orderId) {
        mModel.getOrderDetailGet(orderId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<OrderDetailGet>(mErrorHandler) {
                    @Override
                    public void onNext(OrderDetailGet orderDetailGet) {
                        if (orderDetailGet.getStatusCode() != 200) {
                            mRootView.showMessage(orderDetailGet.getMessage());
                        } else {
                            if (orderDetailGet.getContent() != null)
                                mRootView.onOrderGet(orderDetailGet);
                        }
                    }
                });
    }

    public void orderGoodsListGet(String orderId) {
        mModel.getOrderGoodsDetailList(orderId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<OrderGoodsDetailList>(mErrorHandler) {
                    @Override
                    public void onNext(OrderGoodsDetailList orderGoodsDetailList) {
                        if (orderGoodsDetailList.getStatusCode() != 200) {
                            mRootView.showMessage(orderGoodsDetailList.getMessage());
                        } else {
                            if (orderGoodsDetailList.getContent() != null)
                                mRootView.onOrderGoodsDetailList(orderGoodsDetailList);
                        }
                    }
                });
    }

    public void orderTake(String orderId) {
        mModel.orderTake(orderId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<OrderTake>(mErrorHandler) {
                    @Override
                    public void onNext(OrderTake orderTake) {
                        if (orderTake.getStatusCode() != 200) {
                            mRootView.showMessage(orderTake.getMessage());
                        } else {
                            if (orderTake.getContent() != null)
                                mRootView.onOrderTake(orderTake);
                        }
                    }
                });
    }
}
