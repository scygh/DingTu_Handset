package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.ManualModule;
import com.scy.dingtu_handset.mvp.contract.ManualContract;
import com.scy.dingtu_handset.mvp.ui.activity.ManualActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ManualModule.class, dependencies = AppComponent.class)
public interface ManualComponent {
    void inject(ManualActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ManualComponent.Builder view(ManualContract.View view);

        ManualComponent.Builder appComponent(AppComponent appComponent);

        ManualComponent build();
    }
}