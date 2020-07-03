package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.PaymentModule;
import com.scy.dingtu_handset.mvp.contract.PaymentContract;
import com.scy.dingtu_handset.mvp.ui.activity.PaymentActivity;

import dagger.BindsInstance;
import dagger.Component;


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
@Component(modules = PaymentModule.class, dependencies = AppComponent.class)
public interface PaymentComponent {
    void inject(PaymentActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PaymentComponent.Builder view(PaymentContract.View view);

        PaymentComponent.Builder appComponent(AppComponent appComponent);

        PaymentComponent build();
    }
}