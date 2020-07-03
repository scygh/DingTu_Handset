package com.scy.dingtu_handset.app.entity;

import java.util.List;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2020/6/28 15:50
 */
public class OrderGoodsDetailList {


    /**
     * Content : [{"OrderGoodsDetail":{"OrderDetailId":"873ed3a7-1fff-4b30-8244-5d42264ddb15","GoodsNo":1,"Quantity":1},"Goods":{"GoodsNo":1,"GoodsType":"3d3d8767-2f6a-4f2f-90ab-60a82c512cb8","GoodsName":"红烧肉","Price":15,"Total":500,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":"50","GoodsLetter":"Hong Shao Rou "}},{"OrderGoodsDetail":{"OrderDetailId":"873ed3a7-1fff-4b30-8244-5d42264ddb15","GoodsNo":9,"Quantity":1},"Goods":{"GoodsNo":9,"GoodsType":"3d3d8767-2f6a-4f2f-90ab-60a82c512cb8","GoodsName":"米饭","Price":1.5,"Total":998,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Mi Fan "}}]
     * Message : Success!
     * Result : true
     * StatusCode : 200
     * IsOk : true
     */

    private String Message;
    private boolean Result;
    private int StatusCode;
    private boolean IsOk;
    private List<ContentBean> Content;

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

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        /**
         * OrderGoodsDetail : {"OrderDetailId":"873ed3a7-1fff-4b30-8244-5d42264ddb15","GoodsNo":1,"Quantity":1}
         * Goods : {"GoodsNo":1,"GoodsType":"3d3d8767-2f6a-4f2f-90ab-60a82c512cb8","GoodsName":"红烧肉","Price":15,"Total":500,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":"50","GoodsLetter":"Hong Shao Rou "}
         */

        private OrderGoodsDetailBean OrderGoodsDetail;
        private GoodsBean Goods;

        public OrderGoodsDetailBean getOrderGoodsDetail() {
            return OrderGoodsDetail;
        }

        public void setOrderGoodsDetail(OrderGoodsDetailBean OrderGoodsDetail) {
            this.OrderGoodsDetail = OrderGoodsDetail;
        }

        public GoodsBean getGoods() {
            return Goods;
        }

        public void setGoods(GoodsBean Goods) {
            this.Goods = Goods;
        }

        public static class OrderGoodsDetailBean {
            /**
             * OrderDetailId : 873ed3a7-1fff-4b30-8244-5d42264ddb15
             * GoodsNo : 1
             * Quantity : 1
             */

            private String OrderDetailId;
            private int GoodsNo;
            private int Quantity;

            public String getOrderDetailId() {
                return OrderDetailId;
            }

            public void setOrderDetailId(String OrderDetailId) {
                this.OrderDetailId = OrderDetailId;
            }

            public int getGoodsNo() {
                return GoodsNo;
            }

            public void setGoodsNo(int GoodsNo) {
                this.GoodsNo = GoodsNo;
            }

            public int getQuantity() {
                return Quantity;
            }

            public void setQuantity(int Quantity) {
                this.Quantity = Quantity;
            }
        }

        public static class GoodsBean {
            /**
             * GoodsNo : 1
             * GoodsType : 3d3d8767-2f6a-4f2f-90ab-60a82c512cb8
             * GoodsName : 红烧肉
             * Price : 15
             * Total : 500
             * GoodsNature : 0
             * PackageDetails : null
             * State : 1
             * Description : 50
             * GoodsLetter : Hong Shao Rou
             */

            private int GoodsNo;
            private String GoodsType;
            private String GoodsName;
            private double Price;
            private int Total;
            private int GoodsNature;
            private Object PackageDetails;
            private int State;
            private String Description;
            private String GoodsLetter;

            public int getGoodsNo() {
                return GoodsNo;
            }

            public void setGoodsNo(int GoodsNo) {
                this.GoodsNo = GoodsNo;
            }

            public String getGoodsType() {
                return GoodsType;
            }

            public void setGoodsType(String GoodsType) {
                this.GoodsType = GoodsType;
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

            public int getTotal() {
                return Total;
            }

            public void setTotal(int Total) {
                this.Total = Total;
            }

            public int getGoodsNature() {
                return GoodsNature;
            }

            public void setGoodsNature(int GoodsNature) {
                this.GoodsNature = GoodsNature;
            }

            public Object getPackageDetails() {
                return PackageDetails;
            }

            public void setPackageDetails(Object PackageDetails) {
                this.PackageDetails = PackageDetails;
            }

            public int getState() {
                return State;
            }

            public void setState(int State) {
                this.State = State;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public String getGoodsLetter() {
                return GoodsLetter;
            }

            public void setGoodsLetter(String GoodsLetter) {
                this.GoodsLetter = GoodsLetter;
            }
        }
    }
}
