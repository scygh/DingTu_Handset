package com.scy.dingtu_handset.app.api;

import com.scy.dingtu_handset.app.entity.AggregateTo;
import com.scy.dingtu_handset.app.entity.CardInfoTo;
import com.scy.dingtu_handset.app.entity.CardTypeTo;
import com.scy.dingtu_handset.app.entity.DepartmentTo;
import com.scy.dingtu_handset.app.entity.DepositTo;
import com.scy.dingtu_handset.app.entity.EMGoodsTo2;
import com.scy.dingtu_handset.app.entity.EMGoodsTypeTo;
import com.scy.dingtu_handset.app.entity.ExpenseTo;
import com.scy.dingtu_handset.app.entity.KeySwitchTo;
import com.scy.dingtu_handset.app.entity.MachineAmountTo;
import com.scy.dingtu_handset.app.entity.MoneyParam;
import com.scy.dingtu_handset.app.entity.OrderDetailGet;
import com.scy.dingtu_handset.app.entity.OrderGoodsDetailList;
import com.scy.dingtu_handset.app.entity.OrderTake;
import com.scy.dingtu_handset.app.entity.QRDepositParam;
import com.scy.dingtu_handset.app.entity.QRExpenseParam;
import com.scy.dingtu_handset.app.entity.QRExpenseTo;
import com.scy.dingtu_handset.app.entity.QRReadTo;
import com.scy.dingtu_handset.app.entity.ReadCardTo;
import com.scy.dingtu_handset.app.entity.RegisterParam;
import com.scy.dingtu_handset.app.entity.RoleTo;
import com.scy.dingtu_handset.app.entity.SimpleExpenseParam;
import com.scy.dingtu_handset.app.entity.SimpleExpenseTo;
import com.scy.dingtu_handset.app.entity.SubsidyTo;
import com.scy.dingtu_handset.app.entity.UserGetTo;
import com.scy.dingtu_handset.app.entity.UserInfoTo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("Api/Token/GetToken")
    Observable<BaseResponse<UserInfoTo>> login(@Query("account") String account, @Query("password") String password, @Query("oldAccessToken") String oldAccessToken);

    @GET("Api/Token/GetToken")
    Call<BaseResponse<UserInfoTo>> synclogin(@Query("account") String account, @Query("password") String password, @Query("oldAccessToken") String oldAccessToken);

    @GET("Api/Config/GetCardPassword")
    Observable<BaseResponse<String>> getCardPassword();

    @GET("Api/User/GetByNumber")
    Observable<BaseResponse<CardInfoTo>> getByNumber(@Query("number") int number);

    @POST("Api/Expense/SimpleExpense")
    Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(@Body SimpleExpenseParam param);

    @GET("Api/Config/GetConfig")
    Observable<BaseResponse<String>> getPayKeySwitch(@Query("key") String key);

    @GET("Api/ReportCenter/Deposit/Page")
    Observable<BaseResponse<DepositTo>> getDepositReport(@Query("PageIndex") int pageIndex, @Query("PageSize") int pageSize, @Query("deviceIDs") String deviceIDs, @Query("orderColumn") String orderColumn, @Query("orderPattern") String orderPattern);

    @GET("Api/ReportCenter/Expense/Page")
    Observable<BaseResponse<ExpenseTo>> getExpenseReport(@Query("PageIndex") int pageIndex, @Query("PageSize") int pageSize, @Query("deviceIDs") String deviceIDs, @Query("orderColumn") String orderColumn, @Query("orderPattern") String orderPattern);

    @GET("Api/Subsidy/SubsidyLeve/GetList")
    Observable<BaseResponse<List<SubsidyTo>>> getSubsidyLeve();

    @GET("Api/CardType/GetList")
    Observable<BaseResponse<List<CardTypeTo>>> getCardType();

    @GET("Api/Department/GetList")
    Observable<BaseResponse<List<DepartmentTo>>> getDepartment();

    @GET("Api/MoneyExchange/Donate/CalculateDonateAmount")
    Observable<BaseResponse<Double>> getDonate(@Query("cardType") int cardType, @Query("amount") double amount);

    @GET("Api/User/Card/GetNextNumber")
    Observable<BaseResponse<Integer>> getNextNumber();

    @POST("Api/User/Register")
    Observable<BaseResponse> addRegister(@Body RegisterParam param);

    @POST("Api/MoneyExchange/Deposit")
    Observable<BaseResponse> addDeposit(@Body MoneyParam param);

    @POST("Api/MoneyExchange/Refund")
    Observable<BaseResponse> addRefund(@Body MoneyParam param);

    @POST("Api/User/Deregister")
    Observable<BaseResponse> addDeregister(@Body MoneyParam param);

    @GET("Api/User/Card/Read")
    Observable<BaseResponse<ReadCardTo>> addReadCard(@Query("qrCodeCompanyCode") int qrCodeCompanyCode, @Query("deviceID") int deviceID, @Query("number") int number);

    @GET("Api/User/Card/ObtainByNumber")
    Observable<BaseResponse> addObtainByNumber(@Query("number") int number);

    @GET("Api/User/get")
    Observable<BaseResponse<UserGetTo>> userGetTo(@Query("number") int number,@Query("IsGetFaceInfo") boolean IsGetFaceInfo);

    @GET("Api/ReportCenter/AggregateDepositAndExpenseReport")
    Observable<BaseResponse<List<AggregateTo>>> getAggregateTo(@Query("startTime") String startTime, @Query("endTime") String endTime);

    @GET("Api/User/QRCode/QRRead")
    Observable<BaseResponse<QRReadTo>> getQRRead(@Query("qrCodeContent") String qrCodeContent, @Query("deviceID") int deviceID);

    @POST("Api/Expense/QRExpense")
    Observable<BaseResponse<QRExpenseTo>> addQRExpense(@Body QRExpenseParam param);

    @POST("Api/MoneyExchange/ThirdParty/QRDeposit")
    Observable<BaseResponse> getQRDeposit(@Body QRDepositParam param);

    @GET("Api/Device/Get")
    Observable<BaseResponse<KeySwitchTo>> getEMDevice(@Query("id") int id);

    @GET("Api/EMGoodsType/GetList")
    Observable<BaseResponse<List<EMGoodsTypeTo>>> findEMGoodsType(@Query("state") String state);

    @GET("Api/EMGoods/GetPage")
    Observable<BaseResponse<EMGoodsTo2>> findEMGoods(@Query("pageIndex") int index, @Query("pageSize") int count, @Query("goodsType") String goodsType);

    @GET("Api/ReportCenter/Expense/MachineAmount")
    Observable<BaseResponse<MachineAmountTo>> getMachineAmount(@Query("deviceId") Integer deviceId);

    @GET("Api/ReportCenter/Expense/MachineTimeCount")
    Observable<BaseResponse<MachineAmountTo>> getMachineTimeCount(@Query("deviceId") Integer deviceId);

    @GET("Api/Manager/GetRole")
    Observable<BaseResponse<RoleTo>> getRole(@Query("userId") String userId);

    @GET("Api/Order/GoodsDetail/List")
    Observable<OrderGoodsDetailList> getOrderGoodsDetailList(@Query("orderId") String orderId);

    @GET("Api/Order/Detail/Get")
    Observable<OrderDetailGet> getOrderDetailGet(@Query("id") String id);

    @POST("Api/Expense/TakeOrder")
    Observable<OrderTake> takeOrder(@Query("orderId") String id);
}
