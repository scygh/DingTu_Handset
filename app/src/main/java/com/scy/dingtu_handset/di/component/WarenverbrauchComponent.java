package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.WarenverbrauchModule;
import com.scy.dingtu_handset.mvp.contract.WarenverbrauchContract;
import com.scy.dingtu_handset.mvp.ui.activity.WarenverbrauchActivity;

import dagger.BindsInstance;
import dagger.Component;


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
@Component(modules = WarenverbrauchModule.class, dependencies = AppComponent.class)
public interface WarenverbrauchComponent {
    void inject(WarenverbrauchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WarenverbrauchComponent.Builder view(WarenverbrauchContract.View view);

        WarenverbrauchComponent.Builder appComponent(AppComponent appComponent);

        WarenverbrauchComponent build();
    }
}