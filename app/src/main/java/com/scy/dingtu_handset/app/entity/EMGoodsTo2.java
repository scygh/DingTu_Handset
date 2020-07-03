package com.scy.dingtu_handset.app.entity;

import java.io.Serializable;
import java.util.List;

public class EMGoodsTo2 implements Serializable {


    /**
     * Count : 240
     * Rows : [{"Goods":{"GoodsNo":65533,"GoodsType":"00000000-0000-0000-0000-000000000001","GoodsName":"1元商品","Price":1,"Total":9999999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":"1元商品","GoodsLetter":"1 Yuan Shang Pin "},"ImgCount":0},{"Goods":{"GoodsNo":65534,"GoodsType":"00000000-0000-0000-0000-000000000001","GoodsName":"0.5元商品","Price":0.5,"Total":9999999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":"0.5元商品","GoodsLetter":"0 . 5 Yuan Shang Pin "},"ImgCount":0},{"Goods":{"GoodsNo":65535,"GoodsType":"00000000-0000-0000-0000-000000000001","GoodsName":"托盘","Price":0,"Total":999999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":"托盘11","GoodsLetter":"Tuo Pan"},"ImgCount":0}]
     */

    private int Count;
    private List<RowsBean> Rows;

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public List<RowsBean> getRows() {
        return Rows;
    }

    public void setRows(List<RowsBean> Rows) {
        this.Rows = Rows;
    }

    public static class RowsBean {
        /**
         * Goods : {"GoodsNo":65533,"GoodsType":"00000000-0000-0000-0000-000000000001","GoodsName":"1元商品","Price":1,"Total":9999999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":"1元商品","GoodsLetter":"1 Yuan Shang Pin "}
         * ImgCount : 0
         */

        private GoodsBean Goods;
        private int ImgCount;

        public GoodsBean getGoods() {
            return Goods;
        }

        public void setGoods(GoodsBean Goods) {
            this.Goods = Goods;
        }

        public int getImgCount() {
            return ImgCount;
        }

        public void setImgCount(int ImgCount) {
            this.ImgCount = ImgCount;
        }

        public static class GoodsBean {
            /**
             * GoodsNo : 65533
             * GoodsType : 00000000-0000-0000-0000-000000000001
             * GoodsName : 1元商品
             * Price : 1
             * Total : 9999999
             * GoodsNature : 0
             * PackageDetails : null
             * State : 1
             * Description : 1元商品
             * GoodsLetter : 1 Yuan Shang Pin
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
