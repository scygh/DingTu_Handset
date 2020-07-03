package com.scy.dingtu_handset.app.entity;

import java.util.List;

public class KeySwitchTo {


    /**
     * Id : 1
     * Name : null
     * PlaceId : af54988b-64af-4a0f-95bb-6cc17655be89
     * Pattern : 1
     * AutoAmount : 0
     * KeyEnabled : false
     * MealEnabled : false
     * DepositEnabled : true
     * RefundEnabled : true
     * CorrectionEnabled : true
     * DiscountEnabled : true
     * AllowType : [1,2,3,4]
     * AllowMeal : null
     * LinkIpAddress : 218.2.80.158
     * LinkPort : 9991
     * GoodsRange : 1,65535
     * FirmwareVersion : SCM_EM_1100-191022
     * DeviceVersion : 9
     * State : 1
     */

    private int ID;
    private String Name;
    private String PlaceId;
    private int Pattern;
    private int AutoAmount;
    private boolean KeyEnabled;
    private boolean MealEnabled;
    private boolean DepositEnabled;
    private boolean RefundEnabled;
    private boolean CorrectionEnabled;
    private boolean DiscountEnabled;
    private List<Integer> AllowMeal;
    private String LinkIpAddress;
    private int LinkPort;
    private String GoodsRange;
    private String FirmwareVersion;
    private int DeviceVersion;
    private int State;
    private List<Integer> AllowType;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPlaceId() {
        return PlaceId;
    }

    public void setPlaceId(String PlaceId) {
        this.PlaceId = PlaceId;
    }

    public int getPattern() {
        return Pattern;
    }

    public void setPattern(int Pattern) {
        this.Pattern = Pattern;
    }

    public int getAutoAmount() {
        return AutoAmount;
    }

    public void setAutoAmount(int AutoAmount) {
        this.AutoAmount = AutoAmount;
    }

    public boolean isKeyEnabled() {
        return KeyEnabled;
    }

    public void setKeyEnabled(boolean KeyEnabled) {
        this.KeyEnabled = KeyEnabled;
    }

    public boolean isMealEnabled() {
        return MealEnabled;
    }

    public void setMealEnabled(boolean MealEnabled) {
        this.MealEnabled = MealEnabled;
    }

    public boolean isDepositEnabled() {
        return DepositEnabled;
    }

    public void setDepositEnabled(boolean DepositEnabled) {
        this.DepositEnabled = DepositEnabled;
    }

    public boolean isRefundEnabled() {
        return RefundEnabled;
    }

    public void setRefundEnabled(boolean RefundEnabled) {
        this.RefundEnabled = RefundEnabled;
    }

    public boolean isCorrectionEnabled() {
        return CorrectionEnabled;
    }

    public void setCorrectionEnabled(boolean CorrectionEnabled) {
        this.CorrectionEnabled = CorrectionEnabled;
    }

    public boolean isDiscountEnabled() {
        return DiscountEnabled;
    }

    public void setDiscountEnabled(boolean DiscountEnabled) {
        this.DiscountEnabled = DiscountEnabled;
    }

    public List<Integer> getAllowMeal() {
        return AllowMeal;
    }

    public void setAllowMeal(List<Integer> AllowMeal) {
        this.AllowMeal = AllowMeal;
    }

    public String getLinkIpAddress() {
        return LinkIpAddress;
    }

    public void setLinkIpAddress(String LinkIpAddress) {
        this.LinkIpAddress = LinkIpAddress;
    }

    public int getLinkPort() {
        return LinkPort;
    }

    public void setLinkPort(int LinkPort) {
        this.LinkPort = LinkPort;
    }

    public String getGoodsRange() {
        return GoodsRange;
    }

    public void setGoodsRange(String GoodsRange) {
        this.GoodsRange = GoodsRange;
    }

    public String getFirmwareVersion() {
        return FirmwareVersion;
    }

    public void setFirmwareVersion(String FirmwareVersion) {
        this.FirmwareVersion = FirmwareVersion;
    }

    public int getDeviceVersion() {
        return DeviceVersion;
    }

    public void setDeviceVersion(int DeviceVersion) {
        this.DeviceVersion = DeviceVersion;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public List<Integer> getAllowType() {
        return AllowType;
    }

    public void setAllowType(List<Integer> AllowType) {
        this.AllowType = AllowType;
    }
}
