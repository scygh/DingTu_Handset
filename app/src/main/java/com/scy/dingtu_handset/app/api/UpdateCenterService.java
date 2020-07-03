package com.scy.dingtu_handset.app.api;

import com.scy.dingtu_handset.app.entity.UpdateInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.scy.dingtu_handset.app.api.Api.UPDATECENTER_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface UpdateCenterService {

    @Headers({DOMAIN_NAME_HEADER + UPDATECENTER_NAME})
    @GET("/Api/UpdateCenter/GetUpdateInfo")
    Call<UpdateInfo> syncUpdate(@Query("softwareKey") String softwareKey);
}