package com.scy.dingtu_handset.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.DepartmentTo;
import com.scy.dingtu_handset.app.entity.RegisterParam;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 09:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface StepThreeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onDepartment(List<DepartmentTo> list);
        void onRegister();
        void onNextNumber(int number);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<DepartmentTo>>> getDepartment();
        Observable<BaseResponse<Integer>> getNextNumber();
        Observable<BaseResponse> addRegister(RegisterParam param);
    }
}
