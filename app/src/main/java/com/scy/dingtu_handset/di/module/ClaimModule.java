package com.scy.dingtu_handset.di.module;

import com.scy.dingtu_handset.mvp.contract.ClaimContract;
import com.scy.dingtu_handset.mvp.model.ClaimModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ClaimModule {

    @Binds
    abstract ClaimContract.Model bindClaimModel(ClaimModel model);
}