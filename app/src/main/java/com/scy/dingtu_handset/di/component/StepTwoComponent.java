package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.StepTwoModule;
import com.scy.dingtu_handset.mvp.contract.StepTwoContract;
import com.scy.dingtu_handset.mvp.ui.activity.StepTwoActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StepTwoModule.class, dependencies = AppComponent.class)
public interface StepTwoComponent {
    void inject(StepTwoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StepTwoComponent.Builder view(StepTwoContract.View view);

        StepTwoComponent.Builder appComponent(AppComponent appComponent);

        StepTwoComponent build();
    }
}