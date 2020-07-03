package com.scy.dingtu_handset.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.scy.dingtu_handset.mvp.contract.ScanTakeFoodContract;
import com.scy.dingtu_handset.mvp.model.ScanTakeFoodModel;


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
@Module
public abstract class ScanTakeFoodModule {

    @Binds
    abstract ScanTakeFoodContract.Model bindScanTakeFoodModel(ScanTakeFoodModel model);
}