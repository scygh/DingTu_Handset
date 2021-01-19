package com.scy.dingtu_handset.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.scy.dingtu_handset.app.api.UserService;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.BaseResponseAddisOK;
import com.scy.dingtu_handset.app.entity.DeviceReadCardRequest;
import com.scy.dingtu_handset.app.entity.EMGoodsTo2;
import com.scy.dingtu_handset.app.entity.EMGoodsTypeTo;
import com.scy.dingtu_handset.app.entity.KeySwitchTo;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
import com.scy.dingtu_handset.app.entity.SimpleExpenseParam;
import com.scy.dingtu_handset.app.entity.SimpleExpenseTo;
import com.scy.dingtu_handset.mvp.contract.WarenverbrauchContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class WarenverbrauchModel extends BaseModel implements WarenverbrauchContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WarenverbrauchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<EMGoodsTypeTo>>> findEMGoodsType(String state) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).findEMGoodsType(state);
    }

    @Override
    public Observable<BaseResponse<EMGoodsTo2>> findEMGoods(int index, int count, String goodsType) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).findEMGoods(index, count, goodsType);
    }

    @Override
    public Observable<BaseResponseAddisOK<DeviceReadCardResponse>> deviceReadCard(int companyCode, int deviceID, DeviceReadCardRequest readCardRequest) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).deviceReadCard(companyCode, deviceID, readCardRequest);
    }

    @Override
    public Observable<BaseResponse<KeySwitchTo>> getEMDevice(int id) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getEMDevice(id);
    }

    @Override
    public Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(int companyCode, int deviceID, SimpleExpenseParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).createSimpleExpense(companyCode, deviceID, param);
    }

}