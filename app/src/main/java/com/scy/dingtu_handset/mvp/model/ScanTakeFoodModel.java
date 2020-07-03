package com.scy.dingtu_handset.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.scy.dingtu_handset.app.api.UserService;
import com.scy.dingtu_handset.app.entity.OrderDetailGet;
import com.scy.dingtu_handset.app.entity.OrderGoodsDetailList;
import com.scy.dingtu_handset.app.entity.OrderTake;
import com.scy.dingtu_handset.mvp.contract.ScanTakeFoodContract;

import io.reactivex.Observable;


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
public class ScanTakeFoodModel extends BaseModel implements ScanTakeFoodContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ScanTakeFoodModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<OrderDetailGet> getOrderDetailGet(String orderId) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getOrderDetailGet(orderId);
    }

    @Override
    public Observable<OrderGoodsDetailList> getOrderGoodsDetailList(String orderId) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getOrderGoodsDetailList(orderId);
    }

    @Override
    public Observable<OrderTake> orderTake(String orderId) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).takeOrder(orderId);
    }
}