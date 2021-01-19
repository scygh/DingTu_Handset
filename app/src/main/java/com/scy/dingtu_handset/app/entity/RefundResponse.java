package com.scy.dingtu_handset.app.entity;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2021/1/11 15:25
 */
public class RefundResponse {


    /**
     * State : 0
     * DepositDetail : {"Id":"string","UserId":"string","Number":0,"DeviceId":0,"DetailType":1,"Finance":0,"PayCount":0,"BeforeBalance":0,"Amount":0,"Money":0,"Donate":0,"Cost":0,"AfterBalance":0,"Operator":"string","TradeDateTime":"2021-01-11T07:22:19.795Z","CreateTime":"2021-01-11T07:22:19.795Z","Description":"string","Channel":0}
     */

    private int State;
    private DepositDetailBean DepositDetail;

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public DepositDetailBean getDepositDetail() {
        return DepositDetail;
    }

    public void setDepositDetail(DepositDetailBean DepositDetail) {
        this.DepositDetail = DepositDetail;
    }

    public static class DepositDetailBean {
        /**
         * Id : string
         * UserId : string
         * Number : 0
         * DeviceId : 0
         * DetailType : 1
         * Finance : 0
         * PayCount : 0
         * BeforeBalance : 0
         * Amount : 0
         * Money : 0
         * Donate : 0
         * Cost : 0
         * AfterBalance : 0
         * Operator : string
         * TradeDateTime : 2021-01-11T07:22:19.795Z
         * CreateTime : 2021-01-11T07:22:19.795Z
         * Description : string
         * Channel : 0
         */

        private String Id;
        private String UserId;
        private int Number;
        private int DeviceId;
        private int DetailType;
        private int Finance;
        private int PayCount;
        private double BeforeBalance;
        private double Amount;
        private double Money;
        private double Donate;
        private double Cost;
        private double AfterBalance;
        private String Operator;
        private String TradeDateTime;
        private String CreateTime;
        private String Description;
        private int Channel;

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

        public int getDeviceId() {
            return DeviceId;
        }

        public void setDeviceId(int DeviceId) {
            this.DeviceId = DeviceId;
        }

        public int getDetailType() {
            return DetailType;
        }

        public void setDetailType(int DetailType) {
            this.DetailType = DetailType;
        }

        public int getFinance() {
            return Finance;
        }

        public void setFinance(int Finance) {
            this.Finance = Finance;
        }

        public int getPayCount() {
            return PayCount;
        }

        public void setPayCount(int PayCount) {
            this.PayCount = PayCount;
        }

        public double getBeforeBalance() {
            return BeforeBalance;
        }

        public void setBeforeBalance(double BeforeBalance) {
            this.BeforeBalance = BeforeBalance;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public double getMoney() {
            return Money;
        }

        public void setMoney(double Money) {
            this.Money = Money;
        }

        public double getDonate() {
            return Donate;
        }

        public void setDonate(double Donate) {
            this.Donate = Donate;
        }

        public double getCost() {
            return Cost;
        }

        public void setCost(double Cost) {
            this.Cost = Cost;
        }

        public double getAfterBalance() {
            return AfterBalance;
        }

        public void setAfterBalance(double AfterBalance) {
            this.AfterBalance = AfterBalance;
        }

        public String getOperator() {
            return Operator;
        }

        public void setOperator(String Operator) {
            this.Operator = Operator;
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

        public int getChannel() {
            return Channel;
        }

        public void setChannel(int Channel) {
            this.Channel = Channel;
        }
    }
}
