package com.scy.dingtu_handset.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.scy.dingtu_handset.app.api.UserService;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.BaseResponseAddisOK;
import com.scy.dingtu_handset.app.entity.CardInfoTo;
import com.scy.dingtu_handset.app.entity.DeviceReadCardRequest;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
import com.scy.dingtu_handset.app.entity.MoneyParam;
import com.scy.dingtu_handset.app.entity.RefundRequest;
import com.scy.dingtu_handset.app.entity.RefundResponse;
import com.scy.dingtu_handset.app.entity.UserGetTo;
import com.scy.dingtu_handset.mvp.contract.RefundContract;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class RefundModel extends BaseModel implements RefundContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RefundModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<CardInfoTo>> getByNumber(int number) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getByNumber(number);
    }

    @Override
    public Observable<BaseResponse> addRefund(MoneyParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addRefund(param);
    }

    @Override
    public Observable<BaseResponseAddisOK<RefundResponse>> refund(int companyCode, int deviceID, RefundRequest refundRequest) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).refund(companyCode, deviceID, refundRequest);
    }

    @Override
    public Observable<BaseResponseAddisOK<DeviceReadCardResponse>> deviceReadCard(int companyCode, int deviceID, DeviceReadCardRequest readCardRequest) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).deviceReadCard(companyCode, deviceID, readCardRequest);
    }
}