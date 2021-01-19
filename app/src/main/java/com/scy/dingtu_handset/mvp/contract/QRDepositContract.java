package com.scy.dingtu_handset.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.scy.dingtu_handset.app.api.BaseResponse;
import com.scy.dingtu_handset.app.entity.BaseResponseAddisOK;
import com.scy.dingtu_handset.app.entity.CardInfoTo;
import com.scy.dingtu_handset.app.entity.CodeReadRequest;
import com.scy.dingtu_handset.app.entity.CodeReadResponse;
import com.scy.dingtu_handset.app.entity.CodeRechargeRequest;
import com.scy.dingtu_handset.app.entity.CodeRechargeResponse;
import com.scy.dingtu_handset.app.entity.DeviceReadCardRequest;
import com.scy.dingtu_handset.app.entity.DeviceReadCardResponse;
import com.scy.dingtu_handset.app.entity.QRDepositParam;
import com.scy.dingtu_handset.app.entity.UserGetTo;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 15:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface QRDepositContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void onReadCard(DeviceReadCardResponse readCardTo);

        void onCodeRecharge(CodeRechargeResponse rechargeResponse);

        void onCodeRead(CodeReadResponse codeReadResponse);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<CardInfoTo>> getByNumber(int number);

        Observable<BaseResponseAddisOK<DeviceReadCardResponse>> deviceReadCard(int companyCode, int deviceID, DeviceReadCardRequest readCardRequest);

        Observable<BaseResponseAddisOK<CodeRechargeResponse>> codeRecharge(int companyCode, int deviceID, CodeRechargeRequest codeRechargeRequest);

        Observable<BaseResponseAddisOK<CodeReadResponse>> codeRead(int companyCode, int deviceID, CodeReadRequest codeReadRequest);
    }
}
