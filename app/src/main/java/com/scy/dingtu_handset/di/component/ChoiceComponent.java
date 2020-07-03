package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.ChoiceModule;
import com.scy.dingtu_handset.mvp.contract.ChoiceContract;
import com.scy.dingtu_handset.mvp.ui.activity.ChoiceActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 16:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChoiceModule.class, dependencies = AppComponent.class)
public interface ChoiceComponent {
    void inject(ChoiceActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChoiceComponent.Builder view(ChoiceContract.View view);

        ChoiceComponent.Builder appComponent(AppComponent appComponent);

        ChoiceComponent build();
    }
}