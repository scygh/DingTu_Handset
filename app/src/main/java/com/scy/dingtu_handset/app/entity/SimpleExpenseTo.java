package com.scy.dingtu_handset.app.entity;

import java.io.Serializable;
import java.util.List;

public class SimpleExpenseTo implements Serializable {

    /**
     * State : 0
     * ExpenseDetail : {"Id":"string","UserId":"string","Number":0,"DeviceType":0,"DeviceId":0,"Pattern":1,"DetailType":0,"PayCount":0,"Finance":0,"OriginalAmount":0,"Amount":0,"Balance":0,"IsDiscount":true,"DiscountRate":0,"TradeDateTime":"2021-01-08T02:15:07.972Z","CreateTime":"2021-01-08T02:15:07.972Z","Description":"string","OfflinePayCount":0}
     * GoodsDetails : [{"Id":"string","Eid":"string","GoodsNo":0,"OrderNo":0,"GoodsName":"string","Price":0,"Amount":0,"Count":0,"CreateTime":"2021-01-08T02:15:07.972Z"}]
     */

    private int State;
    private ExpenseDetailBean ExpenseDetail;
    private List<GoodsDetailsBean> GoodsDetails;

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public ExpenseDetailBean getExpenseDetail() {
        return ExpenseDetail;
    }

    public void setExpenseDetail(ExpenseDetailBean ExpenseDetail) {
        this.ExpenseDetail = ExpenseDetail;
    }

    public List<GoodsDetailsBean> getGoodsDetails() {
        return GoodsDetails;
    }

    public void setGoodsDetails(List<GoodsDetailsBean> GoodsDetails) {
        this.GoodsDetails = GoodsDetails;
    }

    public static class ExpenseDetailBean {
        /**
         * Id : string
         * UserId : string
         * Number : 0
         * DeviceType : 0
         * DeviceId : 0
         * Pattern : 1
         * DetailType : 0
         * PayCount : 0
         * Finance : 0
         * OriginalAmount : 0
         * Amount : 0
         * Balance : 0
         * IsDiscount : true
         * DiscountRate : 0
         * TradeDateTime : 2021-01-08T02:15:07.972Z
         * CreateTime : 2021-01-08T02:15:07.972Z
         * Description : string
         * OfflinePayCount : 0
         */

        private String Id;
        private String UserId;
        private int Number;
        private int DeviceType;
        private int DeviceId;
        private int Pattern;
        private int DetailType;
        private int PayCount;
        private double Finance;
        private double OriginalAmount;
        private double Amount;
        private double Balance;
        private boolean IsDiscount;
        private int DiscountRate;
        private String TradeDateTime;
        private String CreateTime;
        private String Description;
        private int OfflinePayCount;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public int getNumber() {
            return Number;
        }

        public void setNumber(int Number) {
            this.Number = Number;
        }

        public int getDeviceType() {
            return DeviceType;
        }

        public void setDeviceType(int DeviceType) {
            this.DeviceType = DeviceType;
        }

        public int getDeviceId() {
            return DeviceId;
        }

        public void setDeviceId(int DeviceId) {
            this.DeviceId = DeviceId;
        }

        public int getPattern() {
            return Pattern;
        }

        public void setPattern(int Pattern) {
            this.Pattern = Pattern;
        }

        public int getDetailType() {
            return DetailType;
        }

        public void setDetailType(int DetailType) {
            this.DetailType = DetailType;
        }

        public int getPayCount() {
            return PayCount;
        }

        public void setPayCount(int PayCount) {
            this.PayCount = PayCount;
        }

        public double getFinance() {
            return Finance;
        }

        public void setFinance(double Finance) {
            this.Finance = Finance;
        }

        public double getOriginalAmount() {
            return OriginalAmount;
        }

        public void setOriginalAmount(double OriginalAmount) {
            this.OriginalAmount = OriginalAmount;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public double getBalance() {
            return Balance;
        }

        public void setBalance(double Balance) {
            this.Balance = Balance;
        }

        public boolean isIsDiscount() {
            return IsDiscount;
        }

        public void setIsDiscount(boolean IsDiscount) {
            this.IsDiscount = IsDiscount;
        }

        public int getDiscountRate() {
            return DiscountRate;
        }

        public void setDiscountRate(int DiscountRate) {
            this.DiscountRate = DiscountRate;
        }

        public String getTradeDateTime() {
            return TradeDateTime;
        }

        public void setTradeDateTime(String TradeDateTime) {
            this.TradeDateTime = TradeDateTime;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public int getOfflinePayCount() {
            return OfflinePayCount;
        }

        public void setOfflinePayCount(int OfflinePayCount) {
            this.OfflinePayCount = OfflinePayCount;
        }
    }

    public static class GoodsDetailsBean {
        /**
         * Id : string
         * Eid : string
         * GoodsNo : 0
         * OrderNo : 0
         * GoodsName : string
         * Price : 0
         * Amount : 0
         * Count : 0
         * CreateTime : 2021-01-08T02:15:07.972Z
         */

        private String Id;
        private String Eid;
        private int GoodsNo;
        private int OrderNo;
        private String GoodsName;
        private double Price;
        private double Amount;
        private int Count;
        private String CreateTime;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getEid() {
            return Eid;
        }

        public void setEid(String Eid) {
            this.Eid = Eid;
        }

        public int getGoodsNo() {
            return GoodsNo;
        }

        public void setGoodsNo(int GoodsNo) {
            this.GoodsNo = GoodsNo;
        }

        public int getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(int OrderNo) {
            this.OrderNo = OrderNo;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
