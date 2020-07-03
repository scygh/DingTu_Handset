package com.scy.dingtu_handset.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.di.module.ChooseModule;
import com.scy.dingtu_handset.mvp.contract.ChooseContract;
import com.scy.dingtu_handset.mvp.ui.activity.ChooseActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/21/2019 15:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChooseModule.class, dependencies = AppComponent.class)
public interface ChooseComponent {
    void inject(ChooseActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChooseComponent.Builder view(ChooseContract.View view);

        ChooseComponent.Builder appComponent(AppComponent appComponent);

        ChooseComponent build();
    }
}