package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.OpenModule;
import com.scy.dingtu_handset.mvp.contract.OpenContract;
import com.scy.dingtu_handset.mvp.ui.activity.OpenActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = OpenModule.class, dependencies = AppComponent.class)
public interface OpenComponent {
    void inject(OpenActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OpenComponent.Builder view(OpenContract.View view);

        OpenComponent.Builder appComponent(AppComponent appComponent);

        OpenComponent build();
    }
}