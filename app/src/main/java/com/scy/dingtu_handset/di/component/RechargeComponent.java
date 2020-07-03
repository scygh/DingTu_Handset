package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.RechargeModule;
import com.scy.dingtu_handset.mvp.contract.RechargeContract;
import com.scy.dingtu_handset.mvp.ui.activity.RechargeActivity;

import dagger.BindsInstance;
import dagger.Component;


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
@Component(modules = RechargeModule.class, dependencies = AppComponent.class)
public interface RechargeComponent {
    void inject(RechargeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RechargeComponent.Builder view(RechargeContract.View view);

        RechargeComponent.Builder appComponent(AppComponent appComponent);

        RechargeComponent build();
    }
}