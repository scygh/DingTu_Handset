package com.scy.dingtu_handset.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.scy.dingtu_handset.app.api.UserService;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.QRExpenseParam;
import com.scy.dingtu_handset.app.entity.QRExpenseTo;
import com.scy.dingtu_handset.app.entity.QRReadTo;
import com.scy.dingtu_handset.mvp.contract.PaymentContract;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class PaymentModel extends BaseModel implements PaymentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PaymentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override public Observable<BaseResponse<QRReadTo>> getQRRead(String qrCodeContent, int deviceID) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getQRRead(qrCodeContent, deviceID);
    }

    @Override public Observable<BaseResponse<QRExpenseTo>> addQRExpense(QRExpenseParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addQRExpense(param);
    }
}