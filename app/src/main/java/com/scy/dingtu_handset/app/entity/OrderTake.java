package com.scy.dingtu_handset.app.entity;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2020/6/29 16:12
 */
public class OrderTake {


    /**
     * Content : {"Id":"80125f7d-0324-48ff-aee5-4037a6e5d8a3","UserId":"12d37a92-474f-49ac-a1aa-239e69f041bd","Title":null,"OrderType":2,"TakeStartTime":"2020-07-07 11:00:00","TakeEndTime":"2020-07-07 13:00:00","OriginalAmount":5.01,"IsDiscount":true,"DiscountRate":100,"Amount":5.01,"AheadAmount":5,"AheadEId":"4a97ea59-d440-4454-b462-148b329b70b8","TakeEId":"719d26b0-3c05-45c4-877f-9ef0812d45d4","State":1,"UpdateTime":"2020-06-29 16:11:11","Remarks":null,"CreateTime":"2020-06-29 16:03:46"}
     * Result : true
     * Message : Success!
     * StatusCode : 200
     */

    private ContentBean Content;
    private boolean Result;
    private String Message;
    private int StatusCode;

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public static class ContentBean {
        /**
         * Id : 80125f7d-0324-48ff-aee5-4037a6e5d8a3
         * UserId : 12d37a92-474f-49ac-a1aa-239e69f041bd
         * Title : null
         * OrderType : 2
         * TakeStartTime : 2020-07-07 11:00:00
         * TakeEndTime : 2020-07-07 13:00:00
         * OriginalAmount : 5.01
         * IsDiscount : true
         * DiscountRate : 100
         * Amount : 5.01
         * AheadAmount : 5
         * AheadEId : 4a97ea59-d440-4454-b462-148b329b70b8
         * TakeEId : 719d26b0-3c05-45c4-877f-9ef0812d45d4
         * State : 1
         * UpdateTime : 2020-06-29 16:11:11
         * Remarks : null
         * CreateTime : 2020-06-29 16:03:46
         */

        private String Id;
        private String UserId;
        private Object Title;
        private int OrderType;
        private String TakeStartTime;
        private String TakeEndTime;
        private double OriginalAmount;
        private boolean IsDiscount;
        private int DiscountRate;
        private double Amount;
        private double AheadAmount;
        private String AheadEId;
        private String TakeEId;
        private int State;
        private String UpdateTime;
        private Object Remarks;
        private String CreateTime;

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

        public Object getTitle() {
            return Title;
        }

        public void setTitle(Object Title) {
            this.Title = Title;
        }

        public int getOrderType() {
            return OrderType;
        }

        public void setOrderType(int OrderType) {
            this.OrderType = OrderType;
        }

        public String getTakeStartTime() {
            return TakeStartTime;
        }

        public void setTakeStartTime(String TakeStartTime) {
            this.TakeStartTime = TakeStartTime;
        }

        public String getTakeEndTime() {
            return TakeEndTime;
        }

        public void setTakeEndTime(String TakeEndTime) {
            this.TakeEndTime = TakeEndTime;
        }

        public double getOriginalAmount() {
            return OriginalAmount;
        }

        public void setOriginalAmount(double OriginalAmount) {
            this.OriginalAmount = OriginalAmount;
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

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double Amount) {
            this.Amount = Amount;
        }

        public double getAheadAmount() {
            return AheadAmount;
        }

        public void setAheadAmount(double AheadAmount) {
            this.AheadAmount = AheadAmount;
        }

        public String getAheadEId() {
            return AheadEId;
        }

        public void setAheadEId(String AheadEId) {
            this.AheadEId = AheadEId;
        }

        public String getTakeEId() {
            return TakeEId;
        }

        public void setTakeEId(String TakeEId) {
            this.TakeEId = TakeEId;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public Object getRemarks() {
            return Remarks;
        }

        public void setRemarks(Object Remarks) {
            this.Remarks = Remarks;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
