package com.scy.dingtu_handset.app.entity;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2020/6/28 15:28
 */
public class OrderDetailGet {

    /**
     * Content : {"Id":"56248195-b961-4028-b601-4d7b133d0780","UserId":"12d37a92-474f-49ac-a1aa-239e69f041bd","Title":null,"OrderType":2,"TakeDate":"2020-06-30 00:00:00","TakeStartTime":"11:00:00","TakeEndTime":"13:00:00","OriginalAmount":13,"IsDiscount":true,"DiscountRate":100,"Amount":13,"AheadAmount":5,"AheadEId":null,"TakeEId":null,"State":0,"UpdateTime":"2020-06-28 16:04:48","Remarks":null,"CreateTime":"2020-06-28 16:04:48"}
     * Message : Success!
     * Result : true
     * StatusCode : 200
     * IsOk : true
     */

    private ContentBean Content;
    private String Message;
    private boolean Result;
    private int StatusCode;
    private boolean IsOk;

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean Result) {
        this.Result = Result;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public boolean isIsOk() {
        return IsOk;
    }

    public void setIsOk(boolean IsOk) {
        this.IsOk = IsOk;
    }

    public static class ContentBean {
        /**
         * Id : 56248195-b961-4028-b601-4d7b133d0780
         * UserId : 12d37a92-474f-49ac-a1aa-239e69f041bd
         * Title : null
         * OrderType : 2
         * TakeDate : 2020-06-30 00:00:00
         * TakeStartTime : 11:00:00
         * TakeEndTime : 13:00:00
         * OriginalAmount : 13
         * IsDiscount : true
         * DiscountRate : 100
         * Amount : 13
         * AheadAmount : 5
         * AheadEId : null
         * TakeEId : null
         * State : 0
         * UpdateTime : 2020-06-28 16:04:48
         * Remarks : null
         * CreateTime : 2020-06-28 16:04:48
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
        private Object AheadEId;
        private Object TakeEId;
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

        public Object getAheadEId() {
            return AheadEId;
        }

        public void setAheadEId(Object AheadEId) {
            this.AheadEId = AheadEId;
        }

        public Object getTakeEId() {
            return TakeEId;
        }

        public void setTakeEId(Object TakeEId) {
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
