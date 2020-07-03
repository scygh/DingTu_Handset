package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.ClaimModule;
import com.scy.dingtu_handset.mvp.contract.ClaimContract;
import com.scy.dingtu_handset.mvp.ui.activity.ClaimActivity;

import dagger.BindsInstance;
import dagger.Component;


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
@ActivityScope
@Component(modules = ClaimModule.class, dependencies = AppComponent.class)
public interface ClaimComponent {
    void inject(ClaimActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ClaimComponent.Builder view(ClaimContract.View view);

        ClaimComponent.Builder appComponent(AppComponent appComponent);

        ClaimComponent build();
    }
}