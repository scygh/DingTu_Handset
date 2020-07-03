package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.TodayModule;
import com.scy.dingtu_handset.mvp.contract.TodayContract;
import com.scy.dingtu_handset.mvp.ui.activity.TodayActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TodayModule.class, dependencies = AppComponent.class)
public interface TodayComponent {
    void inject(TodayActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TodayComponent.Builder view(TodayContract.View view);

        TodayComponent.Builder appComponent(AppComponent appComponent);

        TodayComponent build();
    }
}