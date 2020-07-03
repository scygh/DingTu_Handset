package com.scy.dingtu_handset.mvp.ui.widget;


import com.scy.dingtu_handset.BuildConfig;

public class UpdateConfig {



    private static final String TAG = "Update";
    public static final String APK_PACKAGENAME ="com.lianxi.dingtu.newsnfc";

    public static int getVerCode() {
        int verCode = BuildConfig.VERSION_CODE;
        return verCode;
    }

    public static String getVerName() {
        String VerName = BuildConfig.VERSION_NAME;
        return VerName;

    }

}
