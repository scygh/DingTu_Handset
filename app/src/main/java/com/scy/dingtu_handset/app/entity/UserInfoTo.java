package com.scy.dingtu_handset.app.entity;

import java.io.Serializable;

public class UserInfoTo implements Serializable {

    /**
     * AccessToken : string
     * CompanyCode : 0
     * UserId : string
     * Account : string
     * ExpirationTime : 2021-01-14T01:46:55.449Z
     */

    private String AccessToken;
    private int CompanyCode;
    private String UserId;
    private String Account;
    private String ExpirationTime;

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String AccessToken) {
        this.AccessToken = AccessToken;
    }

    public int getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(int CompanyCode) {
        this.CompanyCode = CompanyCode;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public String getExpirationTime() {
        return ExpirationTime;
    }

    public void setExpirationTime(String ExpirationTime) {
        this.ExpirationTime = ExpirationTime;
    }
}
