package com.scy.dingtu_handset.app.entity;

import java.util.List;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2021/1/12 16:50
 */
public class CodeExpenseResponse {

    /**
     * CodeType : 2
     * State : 0
     * ThirdPartyExpenseDetail : {"Id":"e4402022-96dd-404e-9303-fd043d2a9f96","DeviceType":2,"DeviceId":1,"Pattern":4,"DetailType":0,"OriginalAmount":0.01,"Amount":0.01,"IsDiscount":false,"DiscountRate":100,"TradeDateTime":"2021-01-12 17:07:10","CreateTime":"2021-01-12 17:07:08","Description":"【收钱吧当面付】消费0.01元","ThirdPartyUserId":"2088022580368112","ThirdPartySourceId":"7895208005092393","OurSourceId":"1001_20210112170707396_1","PayWay":1,"Channel":3,"State":1}
     * GoodsDetails : [{"Id":"cae7e9a9-d829-48d0-86b4-5afe1cf56dd8","Eid":"e4402022-96dd-404e-9303-fd043d2a9f96","GoodsNo":4,"OrderNo":1,"GoodsName":"鱼香肉丝","Price":0.01,"Amount":0.01,"Count":1,"CreateTime":"2021-01-12 17:07:08"}]
     */

    private int CodeType;
    private int State;
    private ThirdPartyExpenseDetailBean ThirdPartyExpenseDetail;
    private ExpenseDetailBean ExpenseDetail;
    private List<GoodsDetailsBean> GoodsDetails;

    public int getCodeType() {
        return CodeType;
    }

    public void setCodeType(int CodeType) {
        this.CodeType = CodeType;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public ExpenseDetailBean getExpenseDetail() {
        return ExpenseDetail;
    }

    public void setExpenseDetail(ExpenseDetailBean expenseDetail) {
        ExpenseDetail = expenseDetail;
    }

    public ThirdPartyExpenseDetailBean getThirdPartyExpenseDetail() {
        return ThirdPartyExpenseDetail;
    }

    public void setThirdPartyExpenseDetail(ThirdPartyExpenseDetailBean ThirdPartyExpenseDetail) {
        this.ThirdPartyExpenseDetail = ThirdPartyExpenseDetail;
    }

    public List<GoodsDetailsBean> getGoodsDetails() {
        return GoodsDetails;
    }

    public void setGoodsDetails(List<GoodsDetailsBean> GoodsDetails) {
        this.GoodsDetails = GoodsDetails;
    }

    public static class ThirdPartyExpenseDetailBean {
        /**
         * Id : e4402022-96dd-404e-9303-fd043d2a9f96
         * DeviceType : 2
         * DeviceId : 1
         * Pattern : 4
         * DetailType : 0
         * OriginalAmount : 0.01
         * Amount : 0.01
         * IsDiscount : false
         * DiscountRate : 100
         * TradeDateTime : 2021-01-12 17:07:10
         * CreateTime : 2021-01-12 17:07:08
         * Description : 【收钱吧当面付】消费0.01元
         * ThirdPartyUserId : 2088022580368112
         * ThirdPartySourceId : 7895208005092393
         * OurSourceId : 1001_20210112170707396_1
         * PayWay : 1
         * Channel : 3
         * State : 1
         */

        private String Id;
        private int DeviceType;
        private int DeviceId;
        private int Pattern;
        private int DetailType;
        private double OriginalAmount;
        private double Amount;
        private boolean IsDiscount;
        private int DiscountRate;
        private String TradeDateTime;
        private String CreateTime;
        private String Description;
        private String ThirdPartyUserId;
        private String ThirdPartySourceId;
        private String OurSourceId;
        private int PayWay;
        private int Channel;
        private int State;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
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

        public String getThirdPartyUserId() {
            return ThirdPartyUserId;
        }

        public void setThirdPartyUserId(String ThirdPartyUserId) {
            this.ThirdPartyUserId = ThirdPartyUserId;
        }

        public String getThirdPartySourceId() {
            return ThirdPartySourceId;
        }

        public void setThirdPartySourceId(String ThirdPartySourceId) {
            this.ThirdPartySourceId = ThirdPartySourceId;
        }

        public String getOurSourceId() {
            return OurSourceId;
        }

        public void setOurSourceId(String OurSourceId) {
            this.OurSourceId = OurSourceId;
        }

        public int getPayWay() {
            return PayWay;
        }

        public void setPayWay(int PayWay) {
            this.PayWay = PayWay;
        }

        public int getChannel() {
            return Channel;
        }

        public void setChannel(int Channel) {
            this.Channel = Channel;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }
    }

    public static class GoodsDetailsBean {
        /**
         * Id : cae7e9a9-d829-48d0-86b4-5afe1cf56dd8
         * Eid : e4402022-96dd-404e-9303-fd043d2a9f96
         * GoodsNo : 4
         * OrderNo : 1
         * GoodsName : 鱼香肉丝
         * Price : 0.01
         * Amount : 0.01
         * Count : 1
         * CreateTime : 2021-01-12 17:07:08
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

    public static class ExpenseDetailBean {
        private String Id;
        private String UserId;
        private int Number;
        private int DeviceType;
        private int DeviceId;
        public int Pattern;
        public int DetailType;
        public int PayCount;
        public int Finance;
        public double OriginalAmount;
        public double Amount;
        public double Balance;
        public boolean IsDiscount;
        public int DiscountRate;
        public String TradeDateTime;
        public String CreateTime;
        public String Description;
        public int OfflinePayCount;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public int getNumber() {
            return Number;
        }

        public void setNumber(int number) {
            Number = number;
        }

        public int getDeviceType() {
            return DeviceType;
        }

        public void setDeviceType(int deviceType) {
            DeviceType = deviceType;
        }

        public int getDeviceId() {
            return DeviceId;
        }

        public void setDeviceId(int deviceId) {
            DeviceId = deviceId;
        }

        public int getPattern() {
            return Pattern;
        }

        public void setPattern(int pattern) {
            Pattern = pattern;
        }

        public int getDetailType() {
            return DetailType;
        }

        public void setDetailType(int detailType) {
            DetailType = detailType;
        }

        public int getPayCount() {
            return PayCount;
        }

        public void setPayCount(int payCount) {
            PayCount = payCount;
        }

        public int getFinance() {
            return Finance;
        }

        public void setFinance(int finance) {
            Finance = finance;
        }

        public double getOriginalAmount() {
            return OriginalAmount;
        }

        public void setOriginalAmount(double originalAmount) {
            OriginalAmount = originalAmount;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double amount) {
            Amount = amount;
        }

        public double getBalance() {
            return Balance;
        }

        public void setBalance(double balance) {
            Balance = balance;
        }

        public boolean isDiscount() {
            return IsDiscount;
        }

        public void setDiscount(boolean discount) {
            IsDiscount = discount;
        }

        public int getDiscountRate() {
            return DiscountRate;
        }

        public void setDiscountRate(int discountRate) {
            DiscountRate = discountRate;
        }

        public String getTradeDateTime() {
            return TradeDateTime;
        }

        public void setTradeDateTime(String tradeDateTime) {
            TradeDateTime = tradeDateTime;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public int getOfflinePayCount() {
            return OfflinePayCount;
        }

        public void setOfflinePayCount(int offlinePayCount) {
            OfflinePayCount = offlinePayCount;
        }
    }
}
