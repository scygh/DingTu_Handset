package com.scy.dingtu_handset.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.BaseResponseAddisOK;
import com.scy.dingtu_handset.app.entity.DeviceReadCardRequest;
import com.scy.dingtu_handset.app.entity.EMGoodsTo2;
import com.scy.dingtu_handset.app.entity.EMGoodsTypeTo;
import com.scy.dingtu_handset.app.entity.KeySwitchTo;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
import com.scy.dingtu_handset.app.entity.SimpleExpenseParam;
import com.scy.dingtu_handset.app.entity.SimpleExpenseTo;

import java.util.List;

import io.reactivex.Observable;


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
public interface WarenverbrauchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onFailed();

        void onReadCard(DeviceReadCardResponse readCardTo);

        void creatBill2(boolean keyEnabled);

        void creatSuccess(SimpleExpenseTo simpleExpenseTo);

        void onEMGoodsDetailGet(List<EMGoodsTo2.RowsBean> content);

        void onPagers(List<EMGoodsTypeTo> emGoodsTypeToList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponseAddisOK<DeviceReadCardResponse>> deviceReadCard(int companyCode, int deviceID,  DeviceReadCardRequest readCardRequest);

        Observable<BaseResponse<KeySwitchTo>> getEMDevice(int id);

        Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(int companyCode, int deviceID,SimpleExpenseParam param);

        Observable<BaseResponse<List<EMGoodsTypeTo>>> findEMGoodsType(String state);

        Observable<BaseResponse<EMGoodsTo2>> findEMGoods(int index, int count, String goodsType);

    }
}
