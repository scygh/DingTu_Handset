package com.scy.dingtu_handset.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.scy.dingtu_handset.di.module.ScanTakeFoodModule;
import com.scy.dingtu_handset.mvp.contract.ScanTakeFoodContract;

import com.jess.arms.di.scope.ActivityScope;
import com.scy.dingtu_handset.mvp.ui.activity.ScanTakeFoodActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/12/2020 10:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ScanTakeFoodModule.class, dependencies = AppComponent.class)
public interface ScanTakeFoodComponent {
    void inject(ScanTakeFoodActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ScanTakeFoodComponent.Builder view(ScanTakeFoodContract.View view);

        ScanTakeFoodComponent.Builder appComponent(AppComponent appComponent);

        ScanTakeFoodComponent build();
    }
}