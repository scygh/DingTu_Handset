package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.StepOneModule;
import com.scy.dingtu_handset.mvp.contract.StepOneContract;
import com.scy.dingtu_handset.mvp.ui.activity.StepOneActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StepOneModule.class, dependencies = AppComponent.class)
public interface StepOneComponent {
    void inject(StepOneActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StepOneComponent.Builder view(StepOneContract.View view);

        StepOneComponent.Builder appComponent(AppComponent appComponent);

        StepOneComponent build();
    }
}