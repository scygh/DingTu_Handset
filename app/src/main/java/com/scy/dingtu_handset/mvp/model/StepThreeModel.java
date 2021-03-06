package com.scy.dingtu_handset.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.scy.dingtu_handset.app.api.UserService;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.DepartmentTo;
import com.scy.dingtu_handset.app.entity.RegisterParam;
import com.scy.dingtu_handset.mvp.contract.StepThreeContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StepThreeModel extends BaseModel implements StepThreeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StepThreeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<BaseResponse<List<DepartmentTo>>> getDepartment() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getDepartment();
    }

    @Override public Observable<BaseResponse<Integer>> getNextNumber() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getNextNumber();
    }

    @Override public Observable<BaseResponse> addRegister(RegisterParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addRegister(param);
    }

}