package com.scy.dingtu_handset.app.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.scy.dingtu_handset.app.entity.UserInfoTo;
import com.scy.dingtu_handset.app.utils.ConfigUtil;

import static com.scy.dingtu_handset.app.api.AppConstant.KeyValue.KEY_IS_LOGIN_INFO;
import static com.scy.dingtu_handset.app.api.AppConstant.KeyValue.KEY_USER_INFO;


public class UserInfoHelper {

    protected static UserInfoHelper instance;
    private UserInfoTo userInfoTo;
    private Context mContext;

    private UserInfoHelper(Context context) {
        mContext = context.getApplicationContext();
        load(context);
    }


    public static UserInfoHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (UserInfoHelper.class) {
                if (instance == null) {
                    instance = new UserInfoHelper(context);
                }
            }
        }
        instance.mContext = context;
        return instance;
    }

    public UserInfoTo getUserInfoTo() {
        return userInfoTo;
    }

    public String getAccessToken() {
        return userInfoTo == null ? "" : userInfoTo.getAccessToken();
    }

    public int getCode() {
        return userInfoTo == null ? 0 : userInfoTo.getCompanyCode();
    }

    /**
    * descirption: 在用户登录成功时刷新
    */
    public void updateUser(UserInfoTo infoTo) {
        userInfoTo = infoTo;
        save();
    }

    private void save() {
        ConfigUtil.saveString(PreferenceManager.getDefaultSharedPreferences(mContext),
                KEY_USER_INFO, JSON.toJSONString(userInfoTo));
    }

    public void load(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String userJson = ConfigUtil.getString(sp, KEY_USER_INFO, "");
        Log.d("KeyValue-----", userJson);
        if (!TextUtils.isEmpty(userJson)) {
            userInfoTo = JSON.parseObject(userJson, UserInfoTo.class);
        }
    }

    public boolean isLogin() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        return ConfigUtil.getBoolean(sp, KEY_IS_LOGIN_INFO);
    }

    public void updateLogin(boolean flag) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        ConfigUtil.saveBoolean(sp, KEY_IS_LOGIN_INFO, flag);
    }
}
